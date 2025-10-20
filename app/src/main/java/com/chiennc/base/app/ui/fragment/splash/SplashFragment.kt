package com.chiennc.base.app.ui.fragment.splash

import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_splash

    override val navigation: SplashNavigation = SplashNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}