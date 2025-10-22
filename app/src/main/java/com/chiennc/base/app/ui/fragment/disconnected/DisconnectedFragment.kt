package com.chiennc.base.app.ui.fragment.disconnected

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentDisconnectedBinding

class DisconnectedFragment : BaseFragment<FragmentDisconnectedBinding, DisconnectedNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_disconnected

    override val navigation: DisconnectedNavigation = DisconnectedNavigation(this)


    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.timeView.hideTransmit()
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}