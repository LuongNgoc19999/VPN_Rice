package com.chiennc.base.app.ui.fragment.success

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.dialog.DialogExtentTime
import com.chiennc.base.databinding.FragmentSucceedBinding


class SucceedFragment : BaseFragment<FragmentSucceedBinding, SucceedNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_succeed

    private val dialogExtentTime by lazy {
        DialogExtentTime().apply {
            callback = {

            }
        }
    }
    override val navigation: SucceedNavigation = SucceedNavigation(this)


    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.btnExtent5min.setOnClickListener {
            if (!dialogExtentTime.isAdded && isAdded) dialogExtentTime.show(childFragmentManager)
        }
        binding.btnExtent30min.setOnClickListener {
            if (!dialogExtentTime.isAdded && isAdded) dialogExtentTime.show(childFragmentManager)
        }
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}