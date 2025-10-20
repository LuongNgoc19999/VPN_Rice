package com.chiennc.base.app.ui.fragment.iap

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation
import com.chiennc.base.app.ui.fragment.result.ResultFragment
import com.chiennc.base.app.ui.fragment.setting.SettingFragment
import com.chiennc.base.app.ui.fragment.splash.SplashFragment

class IapNavigation(val fragment: IapFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}