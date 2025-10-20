package com.chiennc.base.app.ui.fragment.setting

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation
import com.chiennc.base.app.ui.fragment.splash.SplashFragment

class SettingNavigation(val fragment: SettingFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}