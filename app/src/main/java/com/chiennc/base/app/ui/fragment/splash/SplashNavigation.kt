package com.chiennc.base.app.ui.fragment.splash

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class SplashNavigation(val fragment: SplashFragment) : BaseNavigation() {
    fun navToHome(){
//        val direction = SplashFragment
    }
    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}