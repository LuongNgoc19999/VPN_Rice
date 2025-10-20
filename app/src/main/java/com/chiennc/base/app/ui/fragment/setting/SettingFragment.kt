package com.chiennc.base.app.ui.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.fragment.splash.SplashNavigation
import com.chiennc.base.databinding.FragmentSettingBinding
import com.chiennc.base.databinding.FragmentSplashBinding

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override val navigation: SettingNavigation = SettingNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}