package com.chiennc.base.app.ui.fragment.disconnected

import androidx.navigation.fragment.navArgs
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentDisconnectedBinding

class DisconnectedFragment : BaseFragment<FragmentDisconnectedBinding, DisconnectedNavigation>() {
    val args by navArgs<DisconnectedFragmentArgs>()
    override fun getLayoutId(): Int = R.layout.fragment_disconnected

    override val navigation: DisconnectedNavigation = DisconnectedNavigation(this)


    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.btnReplace.setOnClickListener {
            navigation.navToChangeServer()
        }
        binding.timeView.hideTransmit(args.totalTime)
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}