package com.chiennc.base.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File
import java.nio.ByteBuffer

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.gone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun Context.isNetworkAvailable(): Boolean {
    val cm: ConnectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val cap: NetworkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else
        for (n in cm.allNetworks) {
            val nInfo: NetworkInfo? = cm.getNetworkInfo(n)
            if (nInfo != null && nInfo.isConnected) return true
        }
    return false
}

fun AppCompatActivity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

/**
 * Load ảnh với coil
 * @param => Truyền vào 1 trong các key Source bên dưới
 */
@BindingAdapter(
    value = ["url", "uri", "file", "drawableRes", "drawable", "bitmap", "byteArray", "byteBuffer"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = null,
    uri: Uri? = null,
    file: File? = null,
    drawableRes: Int? = null,
    drawable: Drawable? = null,
    bitmap: Bitmap? = null,
    byteArray: ByteArray? = null,
    byteBuffer: ByteBuffer? = null,
) {
    when {
        url != null -> Glide.with(context).load(url).into(this)
        uri != null -> Glide.with(context).load(uri).into(this)
        file != null -> Glide.with(context).load(file).into(this)
        drawableRes != null -> Glide.with(context).load(drawableRes).into(this)
        drawable != null -> Glide.with(context).load(drawable).into(this)
        bitmap != null -> Glide.with(context).load(bitmap).into(this)
        byteArray != null -> Glide.with(context).load(byteArray).into(this)
        byteBuffer != null -> Glide.with(context).load(byteBuffer).into(this)
    }
}