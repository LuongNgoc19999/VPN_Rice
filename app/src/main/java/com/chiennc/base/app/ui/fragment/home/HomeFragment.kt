package com.chiennc.base.app.ui.fragment.home

import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.VpnService
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.chiennc.base.R
import com.chiennc.base.app.presentation.vpn.VpnIntent
import com.chiennc.base.app.presentation.vpn.VpnState
import com.chiennc.base.app.presentation.vpn.VpnViewModel
import com.chiennc.base.app.service.MyVpnService
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.dialog.DialogRequest
import com.chiennc.base.app.utils.visible
import com.chiennc.base.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeNavigation>() {
    private val vpnStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.hasExtra("status") == true) {
                val status = intent.getBooleanExtra("status", false)
                Log.d("ngoc", "status: $status")
                updateStatus()
                if (status) navigation.navToSucceed() else navigation.navToDisconnected()
            }
            if (intent?.action == "VPN_SPEED"){
                val down = intent.getDoubleExtra("download", 0.0)
                val up = intent.getDoubleExtra("upload", 0.0)
                Log.d("ngoc", "down: $down, up: $up")
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home
    val viewModel: VpnViewModel by viewModels()
    private val dialogRequest by lazy {
        DialogRequest().apply {
            callback = {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.handleIntent(VpnIntent.Loading)
                    delay(2000)
                    prepareAndStartVpn()
                }
            }
        }
    }

    private val vpnContent = registerForActivityResult(VpnContent()) {
        if (it) startVpnService()
    }

    override val navigation: HomeNavigation = HomeNavigation(this)

    override fun initView() {
        binding.serverView.setData()
        binding.serverView.callback = {
            navigation.navToChangeServer()
        }
        binding.btnStart.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (MyVpnService.isRunning) {
                    viewModel.handleIntent(VpnIntent.Loading)
                    delay(2000)
                    stopVpnService()
                } else {
                    if (!dialogRequest.isAdded && isAdded) dialogRequest.show(childFragmentManager)
                }
            }
        }
        binding.btnIap.setOnClickListener {
            navigation.navToIap()
        }
        binding.btnSetting.setOnClickListener {
            navigation.navToSetting()
        }
    }

    private fun stopVpnService() {
        requireActivity().startService(
            Intent(
                requireActivity(),
                MyVpnService::class.java
            ).also { it.action = MyVpnService.ACTION_DISCONNECT })
    }

    private fun prepareAndStartVpn() {
        val intent = VpnService.prepare(context)
        if (intent != null)
            vpnContent.launch(intent)
        else startVpnService()
    }

    private fun startVpnService() {
        val svc = Intent(requireActivity(), MyVpnService::class.java)
        svc.putExtra("server_host", "10.0.2.2")
        svc.putExtra("server_port", 51820)
        requireActivity().startService(svc)
    }

    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect { handleChangeState(it) }
        }
    }

    fun handleChangeState(state: VpnState) {
        binding.ivloading.visible(state == VpnState.Loading)
        binding.ivFlash.visible(state == VpnState.Disconnect)
        binding.ivPause.visible(state == VpnState.Connect)
        if (state == VpnState.Loading) {
            val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            binding.ivloading.startAnimation(rotateAnimation)
        } else {
            binding.ivloading.clearAnimation()
        }
    }

    private fun updateStatus() {
        val running = MyVpnService.isRunning
        if (running) {
            viewModel.handleIntent(VpnIntent.Connect)
        } else {
            viewModel.handleIntent(VpnIntent.Disconnect)
        }
    }

    override fun initData() {}

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("VPN_STATUS")
        ContextCompat.registerReceiver(
            requireContext(),
            vpnStatusReceiver,
            filter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(vpnStatusReceiver)
        binding.ivloading.clearAnimation()
    }

    class VpnContent : ActivityResultContract<Intent, Boolean>() {
        override fun createIntent(context: Context, input: Intent): Intent {
            return input
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}