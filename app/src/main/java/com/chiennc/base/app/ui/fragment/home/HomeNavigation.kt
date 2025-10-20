package com.chiennc.base.app.ui.fragment.home

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation
import com.chiennc.base.app.ui.fragment.iap.IapFragment
import com.chiennc.base.app.ui.fragment.result.ResultFragment
import com.chiennc.base.app.ui.fragment.setting.SettingFragment
import com.chiennc.base.app.ui.fragment.splash.SplashFragment

class HomeNavigation(val fragment: HomeFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}