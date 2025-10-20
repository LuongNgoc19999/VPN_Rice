package com.chiennc.base.app.ui.fragment.iap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.fragment.result.ResultNavigation
import com.chiennc.base.databinding.FragmentIapBinding
import com.chiennc.base.databinding.FragmentResultBinding

class IapFragment: BaseFragment<FragmentIapBinding, IapNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_iap

    override val navigation: IapNavigation = IapNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}