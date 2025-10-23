package com.chiennc.base.app.presentation.change_server

import android.util.Log
import androidx.lifecycle.ViewModel
import com.chiennc.base.app.presentation.splash.SplashState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChangeServerViewModel : ViewModel() {
    private val _state = MutableStateFlow<ChangeServerState>(ChangeServerState())
    val state: StateFlow<ChangeServerState> = _state

    fun handleIntent(intent: ChangeServerIntent){
        when(intent){
            is ChangeServerIntent.ChangeServer ->{
                _state.value = _state.value.copy(index = intent.index)
            }
        }
    }
}