package com.chiennc.base.app.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.fragment.iap.IapNavigation
import com.chiennc.base.databinding.FragmentHomeBinding
import com.chiennc.base.databinding.FragmentIapBinding

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override val navigation: HomeNavigation = HomeNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}