package com.chiennc.base.app.ui

import com.chiennc.base.app.ui.base.BaseActivity
import com.chiennc.base.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}