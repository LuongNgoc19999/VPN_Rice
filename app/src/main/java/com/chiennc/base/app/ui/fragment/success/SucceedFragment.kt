package com.chiennc.base.app.ui.fragment.success

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.ContextCompat
import com.chiennc.base.R
import com.chiennc.base.app.service.MyVpnService
import com.chiennc.base.app.service.MyVpnService.Companion.KEY_DOWNLOAD
import com.chiennc.base.app.service.MyVpnService.Companion.KEY_STATUS
import com.chiennc.base.app.service.MyVpnService.Companion.KEY_TIME
import com.chiennc.base.app.service.MyVpnService.Companion.KEY_UPLOAD
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.dialog.DialogExtentTime
import com.chiennc.base.databinding.FragmentSucceedBinding


class SucceedFragment : BaseFragment<FragmentSucceedBinding, SucceedNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_succeed
    private val vpnStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra(KEY_STATUS) == true) {
                val status = intent.getBooleanExtra(KEY_STATUS, false)
                if (!status) navigation.popBackStack()
            }
            if (intent?.hasExtra(KEY_TIME) == true) {
                val down = intent.getDoubleExtra(KEY_DOWNLOAD, 0.0)
                val up = intent.getDoubleExtra(KEY_UPLOAD, 0.0)
                val time = intent.getLongExtra(KEY_TIME, 0L)
                binding.timeView.setData(up, down, time)
                Log.d("ngoc", "down: $down, up: $up, time: $time")
            }
        }
    }
    private var dialogExtentTime: DialogExtentTime? = null
    override val navigation: SucceedNavigation = SucceedNavigation(this)


    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.btnExtent5min.setOnClickListener {
            showDialog(true)
        }
        binding.btnExtent30min.setOnClickListener {
            showDialog(false)
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(MyVpnService.ACTION_VPN_STATUS)
        ContextCompat.registerReceiver(
            requireContext(), vpnStatusReceiver, filter, ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(vpnStatusReceiver)
    }

    fun showDialog(is5Min: Boolean) {
        dialogExtentTime?.dismiss()
        dialogExtentTime = DialogExtentTime(is5Min).apply {
            callback = {
                if (MyVpnService.isRunning) {
                    val svc = Intent(requireActivity(), MyVpnService::class.java).also {
                        it.action =
                            if (is5Min) MyVpnService.ACTION_EXTENT_TIME_5_MIN
                            else MyVpnService.ACTION_EXTENT_TIME_30_MIN
                    }
                    requireActivity().startService(svc)
                }
            }
        }

        if (dialogExtentTime?.isAdded == false && isAdded)
            dialogExtentTime?.show(childFragmentManager)
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}