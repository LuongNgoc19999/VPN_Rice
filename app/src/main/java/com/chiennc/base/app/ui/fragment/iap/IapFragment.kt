package com.chiennc.base.app.ui.fragment.iap

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentIapBinding
import com.chiennc.base.iap

class IapFragment : BaseFragment<FragmentIapBinding, IapNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_iap
    var index = 0
    override val navigation: IapNavigation = IapNavigation(this)

    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.ervIap.withModels {
            (0 until 3).forEach {
                iap {
                    id(it)
                    textTime(
                        when (it) {
                            0 -> "1 month"
                            1 -> "6 month"
                            else -> "12 month"
                        }
                    )
                    totalPrice("Total price \$${it}")
                    price("${it}000")
                    unit("\$/Month")
                    isSelected(it == index)
                    onclick { _ ->
                        index = it
                        binding.ervIap.requestModelBuild()
                    }
                }
            }
        }
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}