package com.chiennc.base.app.ui.dialog

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseDialog
import com.chiennc.base.databinding.DialogConnectionRequestBinding

class DialogRequest : BaseDialog<DialogConnectionRequestBinding>() {
    var callback: () -> Unit = {}
    override fun onViewReady() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnOK.setOnClickListener {
            dismiss()
            callback.invoke()
        }
    }

    override fun layoutRes(): Int = R.layout.dialog_connection_request
}