package com.chiennc.base.app.ui.fragment.change_server

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import com.chiennc.base.R
import com.chiennc.base.app.presentation.change_server.ChangeServerIntent
import com.chiennc.base.app.presentation.change_server.ChangeServerViewModel
import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.databinding.FragmentChangeServerBinding
import com.chiennc.base.server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeServerFragment : BaseFragment<FragmentChangeServerBinding, ChangeServerNavigation>() {
    override fun getLayoutId(): Int = R.layout.fragment_change_server
    val viewModel: ChangeServerViewModel by viewModels()
    override val navigation: ChangeServerNavigation = ChangeServerNavigation(this)

    override fun initView() {
        binding.ervServer.withModels {
            (0 until 10).forEach {
                server {
                    id(it)
                    isSelected(it == viewModel.state.value.index)
                    onClick { _ ->
                        viewModel.handleIntent(ChangeServerIntent.ChangeServer(it))
                    }
                }
            }
        }
        binding.ivBack.setOnClickListener {
            navigation.popBackStack()
        }
    }

    override fun initObserver() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect { state ->
                binding.ervServer.requestModelBuild()
            }
        }
    }

    private fun handlePop() {
        Handler(Looper.getMainLooper()).postDelayed({ navigation.popBackStack() }, 1000)
    }

    override fun initData() {

    }
}