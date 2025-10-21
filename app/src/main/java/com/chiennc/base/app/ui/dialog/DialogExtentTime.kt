package com.chiennc.base.app.ui.dialog

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseDialog
import com.chiennc.base.databinding.DialogExtentTimeBinding

class DialogExtentTime : BaseDialog<DialogExtentTimeBinding>() {
    var callback: () -> Unit = {}
    override fun onViewReady() {
        binding.btnOK.setOnClickListener {
            dismiss()
            callback.invoke()
        }
    }

    override fun layoutRes(): Int = R.layout.dialog_extent_time
}