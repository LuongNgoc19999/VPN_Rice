package com.chiennc.base.app.ui.fragment.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.fragment.setting.SettingNavigation
import com.chiennc.base.databinding.FragmentResultBinding
import com.chiennc.base.databinding.FragmentSettingBinding

class ResultFragment : BaseFragment<FragmentResultBinding, ResultNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_result

    override val navigation: ResultNavigation = ResultNavigation(this)


    override fun initView() {

    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}