package com.chiennc.base.app.ui.fragment.change_server

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.fragment.iap.IapNavigation
import com.chiennc.base.databinding.FragmentChangeServerBinding
import com.chiennc.base.databinding.FragmentIapBinding

class ChangeServerFragment: BaseFragment<FragmentChangeServerBinding, ChangeServerNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_change_server

    override val navigation: ChangeServerNavigation = ChangeServerNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}