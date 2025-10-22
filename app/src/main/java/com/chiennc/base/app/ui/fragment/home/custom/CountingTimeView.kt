package com.chiennc.base.app.ui.fragment.home.custom

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
    fun hideTransmit() {
        binding.layoutBandWidth.gone()
    }
}