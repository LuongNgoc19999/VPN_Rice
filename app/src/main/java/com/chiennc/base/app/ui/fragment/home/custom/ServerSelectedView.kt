package com.chiennc.base.app.ui.fragment.home.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.chiennc.base.databinding.LayoutServerHomeBinding

class ServerSelectedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var callback: () -> Unit = {}
    val binding = LayoutServerHomeBinding.inflate(LayoutInflater.from(context), this, true)
    fun setData() {
        binding.container.setOnClickListener {
            callback.invoke()
        }
    }
}