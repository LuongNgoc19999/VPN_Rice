package com.chiennc.base.app.ui.fragment.setting

import com.chiennc.base.R
import com.chiennc.base.app.domain.model.SettingObject
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentSettingBinding
import com.chiennc.base.setting

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_setting

    override val navigation: SettingNavigation = SettingNavigation(this)

    override fun initView() {
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
        binding.btnIap.setOnClickListener {
            navigation.navToIap()
        }
        binding.ervSetting.withModels {
            SettingObject.entries.forEach {
                setting {
                    id(it.image)
                    image(it.image)
                    text(it.text)
                    onClick { _ -> }
                }
            }
        }
    }

    override fun initObserver() {

    }

    override fun initData() {

    }
}