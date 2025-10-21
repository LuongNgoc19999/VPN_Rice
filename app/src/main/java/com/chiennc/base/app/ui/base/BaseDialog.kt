package com.chiennc.base.app.ui.base

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity.BOTTOM
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog<M : ViewDataBinding> : DialogFragment() {

    protected var percentWidth = 0.85f

    protected abstract fun onViewReady()

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected lateinit var binding: M


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout(1f)
        setBackgroundDialog()
        onViewReady()
    }

    fun show(manager: FragmentManager) {
        runCatching {
            show(manager, null)
        }.onFailure {
            Log.d("TAGGGGGG", "show:${it.printStackTrace()} ")
        }
    }

    fun setLayout(mValue: Float) {
        val screenWith = Resources.getSystem().displayMetrics.widthPixels
        val params = dialog?.window?.attributes
        params?.width = (screenWith * mValue).toInt()
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = BOTTOM
        dialog?.window?.attributes = params
    }

    private fun setBackgroundDialog() {
        if (dialog?.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.setCancelable(false)
        }
    }
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

