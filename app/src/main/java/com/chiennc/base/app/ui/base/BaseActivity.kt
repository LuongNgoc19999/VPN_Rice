package com.chiennc.base.app.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding()
        setContentView(binding.root)
        initView()
        initObserver()
        initData()
    }

    abstract fun initBinding(): VB
    abstract fun initView()
    abstract fun initObserver()
    abstract fun initData()
}