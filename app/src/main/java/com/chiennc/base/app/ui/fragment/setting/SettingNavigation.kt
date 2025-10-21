package com.chiennc.base.app.ui.fragment.setting

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class SettingNavigation(val fragment: SettingFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }

    fun navToIap() {
        val direction = SettingFragmentDirections.actionSettingFragmentToIapFragment()
        navigateTo(R.id.settingFragment, direction)
    }
}