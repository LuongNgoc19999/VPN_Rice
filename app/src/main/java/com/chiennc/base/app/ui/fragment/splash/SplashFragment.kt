package com.chiennc.base.app.ui.fragment.splash

import android.util.Log
import android.view.animation.AnimationUtils
import com.chiennc.base.R
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.utils.show
import com.chiennc.base.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_splash
    override val navigation: SplashNavigation = SplashNavigation(this)


    override fun initView() {
        Log.d("ngoc", "layout: ${R.layout.item_server}")
        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        binding.ivLoading.startAnimation(rotateAnimation)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            binding.layoutApp.show()
        }
        binding.ivClose.setOnClickListener {
            navigation.navToHome()
        }
        binding.btnSignUp.setOnClickListener {
            navigation.navToHome()
        }
    }

    override fun initObserver() {

    }

    override fun initData() {

    }

    override fun onPause() {
        super.onPause()
        binding.ivLoading.clearAnimation()
    }
}