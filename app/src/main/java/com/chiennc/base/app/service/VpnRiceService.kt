package com.chiennc.base.app.service

import android.app.Notification
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

class VpnRiceService : VpnService() {
    private val TAG = "VpnRiceService"
    private var vpnFd: ParcelFileDescriptor? = null
    private val running = AtomicBoolean(false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundServiceWithNotification()
        startVpn()
        return START_STICKY
    }

    override fun onDestroy() {
        stopVpn()
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundServiceWithNotification() {
        val notif = Notification.Builder(this, /* your channel id */ "vpn")
            .setContentTitle("MyVPN")
            .setContentText("VPN is running")
            .setSmallIcon(android.R.drawable.ic_secure)
            .build()
        startForeground(1, notif)
    }

    private fun startVpn() {
        if (running.get()) return

        val builder = Builder()
        builder.setSession("MyVPN")
            .addAddress("10.0.0.2", 24)      // local address in VPN
            .addRoute("0.0.0.0", 0)         // route all traffic
            .addDnsServer("8.8.8.8")
            .setMtu(1500)

        vpnFd = builder.establish()
        vpnFd?.let { fd ->
            running.set(true)
            thread { vpnLoop(fd.fileDescriptor) }
        } ?: run {
            Log.e(TAG, "Failed to establish VPN")
        }
    }

    private fun stopVpn() {
        running.set(false)
        vpnFd?.close()
        vpnFd = null
    }

    private fun vpnLoop(fd: java.io.FileDescriptor) {
        // Example: UDP tunnel to remote server
        val serverHost = "1.2.3.4"
        val serverPort = 51820
        val udpSocket = DatagramSocket()
        udpSocket.soTimeout = 1000

        val input = FileInputStream(fd)
        val output = FileOutputStream(fd)
        val readBuffer = ByteArray(32767)
        val udpBuffer = ByteArray(32767)

        // Reader thread: read from TUN and send to server
        val reader = thread {
            try {
                while (running.get()) {
                    val len = input.read(readBuffer)
                    if (len > 0) {
                        // Here you'd normally encapsulate/encrypt before sending
                        val pkt = DatagramPacket(
                            readBuffer,
                            len,
                            InetSocketAddress(serverHost, serverPort)
                        )
                        udpSocket.send(pkt)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Reader exception: ${e.message}", e)
            }
        }

        // Writer thread: receive from server and write to TUN
        val writer = thread {
            try {
                while (running.get()) {
                    try {
                        val rcv = DatagramPacket(udpBuffer, udpBuffer.size)
                        udpSocket.receive(rcv)
                        // Here you'd normally decrypt/decapsulate
                        output.write(rcv.data, 0, rcv.length)
                    } catch (e: java.net.SocketTimeoutException) {
                        // timeout for loop to check running flag
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Writer exception: ${e.message}", e)
            }
        }

        reader.join()
        writer.join()
        udpSocket.close()
        try {
            input.close(); output.close()
        } catch (_: Exception) {
        }
    }
}