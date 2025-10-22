package com.chiennc.base.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.app.NotificationCompat
import com.chiennc.base.R
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream

class MyVpnService : VpnService() {
    companion object {
        const val ACTION_DISCONNECT = "studio.attect.demo.vpnservice.DISCONNECT"

        @Volatile
        var isRunning = false
    }
    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent?.action == VpnRiceService.Companion.ACTION_DISCONNECT) {
            stopVpn()
            START_NOT_STICKY
        } else {
            runCatching {
                startForeground(1, createNotification())
            }
            startVpn()
            START_STICKY
        }

        return START_STICKY
    }

    inner class VpnWorker(private val fd: FileDescriptor) : Thread() {
        override fun run() {
            val input = FileInputStream(fd)   // gói tin từ hệ thống (download)
            val output = FileOutputStream(fd) // gói tin ra mạng (upload)
            val buffer = ByteArray(32767)

            var totalReadBytes = 0L
            var totalWriteBytes = 0L
            var lastTime = System.currentTimeMillis()

            while (isRunning) {
                // Đọc từ TUN interface
                val len = input.read(buffer)
                if (len > 0) totalReadBytes += len
                // (Nếu có forward ra mạng, sẽ ghi vào output)
                // output.write(buffer, 0, len)
                // totalWriteBytes += len

                // Cập nhật tốc độ mỗi giây
                val now = System.currentTimeMillis()
                if (now - lastTime >= 1000) {
                    val downMBps = totalReadBytes / 1_000_000.0
                    val upMBps = totalWriteBytes / 1_000_000.0
                    sendSpeedBroadcast(downMBps, upMBps)

                    totalReadBytes = 0
                    totalWriteBytes = 0
                    lastTime = now
                }
            }
        }
    }

    private fun startVpn() {
        val builder = Builder()
            .setSession("My VPN")
            .addAddress("10.0.0.2", 24)
            .addDnsServer("8.8.8.8")
            .addRoute("0.0.0.0", 0)

        vpnInterface = builder.establish()
        vpnInterface?.let { tun ->
            isRunning = true
            sendStatusUpdate()
            Thread(VpnWorker(tun.fileDescriptor)).start()
        }
    }

    fun stopVpn(){
        isRunning = false
        sendStatusUpdate()
        vpnInterface?.close()
    }

    private fun sendStatusUpdate() {
        val intent = Intent("VPN_STATUS")
        intent.putExtra("status", isRunning)
        sendBroadcast(intent)
    }

    private fun sendSpeedBroadcast(downMBps: Double, upMBps: Double) {
        Log.d("ngoc", "sendSpeedBroadcast")
        val intent = Intent("VPN_SPEED")
        intent.putExtra("download", downMBps)
        intent.putExtra("upload", upMBps)
        sendBroadcast(intent)
    }

    private fun createNotification(): Notification {
        val channelId = "vpn_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "VPN", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_request)
            .setContentTitle("VPN đang chạy")
            .setContentText("Đang kết nối...")
            .build()
    }
}