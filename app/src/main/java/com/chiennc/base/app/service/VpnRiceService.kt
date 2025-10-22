package com.chiennc.base.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import com.chiennc.base.app.ui.MainActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

class VpnRiceService : VpnService() {
    companion object {
        const val ACTION_CONNECT = "studio.attect.demo.vpnservice.CONNECT"
        const val ACTION_DISCONNECT = "studio.attect.demo.vpnservice.DISCONNECT"

        @Volatile
        var isRunning = false
    }
    private fun sendStatusUpdate() {
        val intent = Intent("VPN_STATUS")
        intent.putExtra("status", isRunning)
        sendBroadcast(intent)
    }
    private val TAG = "VpnRiceService"
    private var vpnFd: ParcelFileDescriptor? = null
    private val running = AtomicBoolean(false)
    private var dumpPcap: java.io.File? = null
    private var pcapStream: FileOutputStream? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent?.action == ACTION_DISCONNECT) {
            stopVpnTunnel()
            START_NOT_STICKY
        } else {
            val host = intent?.getStringExtra("server_host") ?: "1.2.3.4"
            val port = intent?.getIntExtra("server_port", 51820) ?: 51820
            createNotificationChannel()
            runCatching {
                startForeground(1, buildNotification("Connecting..."))
            }
            startVpnTunnel(host, port)
            START_STICKY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun buildNotification(text: String): Notification {
        val pending = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val nb = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "vpn_channel")
        } else Notification.Builder(this)

        return nb.setContentTitle("SimpleVPN")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_secure)
            .setContentIntent(pending)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel =
                NotificationChannel("vpn_channel", "VPN", NotificationManager.IMPORTANCE_LOW)
            nm.createNotificationChannel(channel)
        }
    }

    private fun startVpnTunnel(serverHost: String, serverPort: Int) {
        if (running.get()) return

        // Builder config
        val builder = Builder()
        builder.setSession("SimpleVPN")
            .addAddress("10.0.0.2", 24)         // local IP within VPN
            .addRoute("0.0.0.0", 0)             // route all traffic
            .addDnsServer("8.8.8.8")
            .setMtu(1400)

        vpnFd = builder.establish()
        if (vpnFd == null) {
            Log.e(TAG, "Failed to establish VPN")
            stopSelf()
            return
        }

        // optional: prepare pcap dump
        try {
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            dumpPcap = java.io.File(filesDir, "vpn_${sdf.format(Date())}.pcap")
            pcapStream = FileOutputStream(dumpPcap)
            // write pcap global header (pcap v2, little endian)
            pcapStream?.write(
                byteArrayOf(
                    0xd4.toByte(),
                    0xc3.toByte(),
                    0xb2.toByte(),
                    0xa1.toByte(), // magic
                    0x02,
                    0x00,
                    0x04,
                    0x00, // version major/minor
                    0x00,
                    0x00,
                    0x00,
                    0x00, // thiszone
                    0x00,
                    0x00,
                    0x00,
                    0x00, // sigfigs
                    0xff.toByte().toInt().and(0xff).toByte(),
                    0xff.toByte().toInt().and(0xff).toByte(),
                    0x00,
                    0x00, // snaplen (65535)
                    0x01,
                    0x00,
                    0x00,
                    0x00 // network (LINKTYPE_ETHERNET) — we will write IP packets directly although ideal header would vary
                )
            )
        } catch (e: Exception) {
            Log.w(TAG, "Cannot create pcap: ${e.message}")
            pcapStream = null
        }

        running.set(true)
        isRunning = true
        sendStatusUpdate()
        thread { vpnLoop(vpnFd!!.fileDescriptor, serverHost, serverPort) }

        // update notification
        runCatching {
            startForeground(1, buildNotification("Connected"))
        }
    }

    private fun stopVpnTunnel() {
        Log.d("ngoc", "stopVpnTunnel")
        running.set(false)
        isRunning = false
        sendStatusUpdate()
        try {
            vpnFd?.close()
        } catch (_: Exception) {
        }
        vpnFd = null
        try {
            pcapStream?.close()
        } catch (_: Exception) {
        }
        pcapStream = null
    }

    private fun vpnLoop(fd: java.io.FileDescriptor, serverHost: String, serverPort: Int) {
        val inStream = FileInputStream(fd)
        val outStream = FileOutputStream(fd)

        val udpSocket = DatagramSocket()
        udpSocket.soTimeout = 1000

        val packetBuf = ByteArray(32768)
        val udpBuf = ByteArray(32768)

        val reader = thread {
            try {
                while (running.get()) {
                    val read = inStream.read(packetBuf)
                    if (read > 0) {
                        // For PoC: send raw IP packet to server via UDP
                        val pkt = DatagramPacket(
                            packetBuf,
                            read,
                            InetSocketAddress(serverHost, serverPort)
                        )
                        udpSocket.send(pkt)
                        maybeDumpPcap(packetBuf, 0, read)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Reader exception: ${e.message}", e)
            }
        }

        val writer = thread {
            try {
                val rcv = DatagramPacket(udpBuf, udpBuf.size)
                while (running.get()) {
                    try {
                        udpSocket.receive(rcv)
                        // write payload directly into TUN
                        outStream.write(rcv.data, 0, rcv.length)
                        maybeDumpPcap(rcv.data, 0, rcv.length)
                    } catch (ste: java.net.SocketTimeoutException) {
                        // loop continue
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Writer exception: ${e.message}", e)
            }
        }

        reader.join()
        writer.join()
        try {
            udpSocket.close()
        } catch (_: Exception) {
        }
        try {
            inStream.close(); outStream.close()
        } catch (_: Exception) {
        }
        // stop service when loops end
        stopSelf()
    }

    private fun maybeDumpPcap(buf: ByteArray, offset: Int, length: Int) {
        try {
            val stream = pcapStream ?: return
            val ts = System.currentTimeMillis() / 1000L
            val usec = (System.currentTimeMillis() % 1000L) * 1000L
            val header = ByteBuffer.allocate(16)
            header.putInt(ts.toInt())
            header.putInt(usec.toInt())
            header.putInt(length)
            header.putInt(length)
            stream.write(header.array())
            stream.write(buf, offset, length)
            stream.flush()
        } catch (e: Exception) {
            // ignore
        }
    }
}