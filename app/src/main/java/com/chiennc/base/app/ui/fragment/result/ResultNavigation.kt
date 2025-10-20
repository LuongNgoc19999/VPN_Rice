package com.chiennc.base.app.ui.fragment.result

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation
import com.chiennc.base.app.ui.fragment.setting.SettingFragment
import com.chiennc.base.app.ui.fragment.splash.SplashFragment

class ResultNavigation(val fragment: ResultFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}