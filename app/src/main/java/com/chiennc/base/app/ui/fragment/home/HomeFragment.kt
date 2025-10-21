package com.chiennc.base.app.ui.fragment.home

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.dialog.DialogExtentTime
import com.chiennc.base.app.ui.dialog.DialogRequest
import com.chiennc.base.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_home
    private val dialogRequest by lazy {
        DialogRequest().apply {
            callback = {}
        }
    }
    private val dialogExtentTime by lazy {
        DialogExtentTime().apply {
            callback = {}
        }
    }

    override val navigation: HomeNavigation = HomeNavigation(this)


    override fun initView() {
        binding.serverView.setData()
    }

    override fun initObserver() {
        binding.serverView.callback = {
            navigation.navToChangeServer()
        }
        binding.btnStart.setOnClickListener {
            if (!dialogRequest.isAdded && isAdded) dialogRequest.show(childFragmentManager)
        }
        binding.btnIap.setOnClickListener {
            if (!dialogExtentTime.isAdded && isAdded) dialogExtentTime.show(childFragmentManager)
        }
    }

    override fun initData() {

    }
}