package com.chiennc.base.app.service

import android.annotation.SuppressLint
import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat
import com.chiennc.base.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.InetSocketAddress
import java.net.Socket

@SuppressLint("VpnServicePolicy")
class MyVpnService : VpnService() {
    companion object {
        const val TIME = 2 * 60 * 1000//minute (milisecond)
        const val TIME_5_MIN = 5 * 60 * 1000//minute (milisecond)
        const val TIME_30_MIN = 30 * 60 * 1000//minute (milisecond)
        const val ACTION_DISCONNECT = "studio.attect.demo.vpnservice.DISCONNECT"
        const val ACTION_EXTENT_TIME_5_MIN = "ACTION_EXTENT_TIME_5_MIN"
        const val ACTION_EXTENT_TIME_30_MIN = "ACTION_EXTENT_TIME_30_MIN"
        const val ACTION_VPN_STATUS = "ACTION_VPN_STATUS"
        const val KEY_STATUS = "KEY_STATUS"
        const val KEY_UPLOAD = "KEY_UPLOAD"
        const val KEY_DOWNLOAD = "KEY_DOWNLOAD"
        const val KEY_TIME = "KEY_TIME"
        const val KEY_TOTAL_TIME = "KEY_TOTAL_TIME"

        @Volatile
        var isRunning = false
    }

    private var vpnInterface: ParcelFileDescriptor? = null

    private var startTime = 0L
    private var endTime = 0L
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent?.action == ACTION_DISCONNECT) {
            stopVpn()
            START_NOT_STICKY
        } else if (intent?.action == ACTION_EXTENT_TIME_5_MIN) {
            if (isRunning) {
                endTime = endTime + TIME_5_MIN
            }
            START_STICKY
        } else if (intent?.action == ACTION_EXTENT_TIME_30_MIN) {
            if (isRunning) {
                endTime = endTime + TIME_30_MIN
            }
            START_STICKY
        } else {
            runCatching {
                startForegroundService()
            }
            val host = intent?.getStringExtra("server_host") ?: "8.8.8.8"
            val port = intent?.getIntExtra("server_port", 443) ?: 443
            startVpn(host, port)
            START_STICKY
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, "vpn_channel")
            .setContentTitle("VPN đang chạy")
            .setContentText("Đang kết nối đến máy chủ...")
            .setSmallIcon(R.drawable.ic_request)
            .build()

        startForeground(1, notification)
    }

    private fun startVpn(serverHost: String, serverPort: Int) {
        isRunning = true
        sendStatusUpdate()
        endTime = System.currentTimeMillis() + TIME
        startTime = System.currentTimeMillis()
        val builder = Builder()
            .setSession("MyVPN")
            .addAddress("10.0.0.2", 32)
            .addDnsServer("8.8.8.8")
            .addRoute("0.0.0.0", 0)

        vpnInterface = builder.establish()

        // Bắt đầu đo lưu lượng
        scope.launch { monitorTraffic(/*serverHost, serverPort*/) }
    }

    private suspend fun monitorTraffic(serverHost: String, serverPort: Int) {
        val fd = vpnInterface?.fileDescriptor ?: return
        val input = FileInputStream(fd)
        val output = FileOutputStream(fd)
        val buffer = ByteArray(32768)

        var totalUpload = 0L
        var totalDownload = 0L

        // Giả lập kết nối tới server thật
        val socket = Socket()
        try {
            // 👇 Thêm xử lý an toàn
            try {
                socket.connect(InetSocketAddress(serverHost, serverPort), 3000)
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

            val remoteIn = socket.getInputStream()
            val remoteOut = socket.getOutputStream()

            while (isRunning) {
                val readBytes = input.read(buffer)
                if (readBytes > 0) {
                    remoteOut.write(buffer, 0, readBytes)
                    totalUpload += readBytes
                }

                if (remoteIn.available() > 0) {
                    val bytesFromServer = remoteIn.read(buffer)
                    if (bytesFromServer > 0) {
                        output.write(buffer, 0, bytesFromServer)
                        totalDownload += bytesFromServer
                    }
                }

                val upMBps = totalUpload / 1024.0 / 1024.0
                val downMBps = totalDownload / 1024.0 / 1024.0
                val elapsed = (endTime - System.currentTimeMillis())
                if (elapsed <= 0) {
                    stopVpn()
                }
                sendSpeedBroadcast(downMBps, upMBps, elapsed)
                delay(1000)
            }
        } finally {
            socket.close()
        }
    }

    private suspend fun monitorTraffic() {
        val fd = vpnInterface?.fileDescriptor ?: return
        val input = FileInputStream(fd)
        val output = FileOutputStream(fd)
        val buffer = ByteArray(32768)

        var totalRead = 0L
        var totalWrite = 0L

        while (isRunning) {
            val readBytes = input.read(buffer)
            if (readBytes > 0) totalRead += readBytes

            output.write(buffer, 0, readBytes)
            totalWrite += readBytes

            val upMBps = totalWrite / 1024.0 / 1024.0
            val downMBps = totalRead / 1024.0 / 1024.0
            val elapsed = (endTime - System.currentTimeMillis())
            if (elapsed <= 0) {
                stopVpn()
            }
            sendSpeedBroadcast(downMBps, upMBps, elapsed)
            delay(1000)
        }
    }

    fun stopVpn() {
        isRunning = false
        endTime = 0
        sendStatusUpdate()
        vpnInterface?.close()
        scope.cancel()
    }

    private fun sendStatusUpdate() {
        val intent = Intent(ACTION_VPN_STATUS)
        intent.putExtra(KEY_STATUS, isRunning)
        if (!isRunning){
            val totalTime = (System.currentTimeMillis()-startTime)
            intent.putExtra(KEY_TOTAL_TIME, totalTime)
            startTime = 0
        }
        sendBroadcast(intent)
    }

    private fun sendSpeedBroadcast(downMBps: Double, upMBps: Double, elapsed: Long) {
        val intent = Intent(ACTION_VPN_STATUS)
        intent.putExtra(KEY_DOWNLOAD, downMBps)
        intent.putExtra(KEY_UPLOAD, upMBps)
        intent.putExtra(KEY_TIME, elapsed)
        sendBroadcast(intent)
    }
}