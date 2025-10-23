package com.chiennc.base.app.ui.fragment.home.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.chiennc.base.app.utils.gone
import com.chiennc.base.databinding.LayoutCountingTimeBinding

class CountingTimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    val binding = LayoutCountingTimeBinding.inflate(LayoutInflater.from(context), this, true)
    fun hideTransmit(time: Long) {
        binding.tvTimeCounting.text = formatTimeFromMillis(time)
        binding.layoutBandWidth.gone()
    }

    fun setData(up: Double = 0.0, down: Double = 0.0, time: Long = 0) {
        binding.tvTimeCounting.text = formatTimeFromMillis(time)
        binding.tvUpload.text = formatData(up)
        binding.tvDownload.text = formatData(down)
    }
    @SuppressLint("DefaultLocale")
    fun formatTimeFromMillis(totalMillis: Long): String {
        val totalSeconds = totalMillis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    @SuppressLint("DefaultLocale")
    fun formatData(data: Double): String{
        return String.format("%.4f", data)+" MBps"
    }
}