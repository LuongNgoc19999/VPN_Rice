package com.chiennc.base.app.ui.fragment.home

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class HomeNavigation(val fragment: HomeFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }

    fun navToChangeServer() {
        val direction = HomeFragmentDirections.actionHomeFragmentToChangeServerFragment()
        navigateTo(R.id.homeFragment, direction)
    }

    fun navToSetting() {
        val direction = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
        navigateTo(R.id.homeFragment, direction)
    }

    fun navToIap() {
        val direction = HomeFragmentDirections.actionHomeFragmentToIapFragment()
        navigateTo(R.id.homeFragment, direction)
    }

    fun navToResult() {
        val direction = HomeFragmentDirections.actionHomeFragmentToResultFragment()
        navigateTo(R.id.homeFragment, direction)
    }
}