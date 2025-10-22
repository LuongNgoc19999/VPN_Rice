package com.chiennc.base.app.presentation.vpn

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VpnViewModel : ViewModel() {
    private val _state = MutableStateFlow<VpnState>(VpnState.Disconnect)
    val state: StateFlow<VpnState> get() = _state

    fun handleIntent(intent: VpnIntent) {
        when (intent) {
            VpnIntent.Connect -> _state.value = VpnState.Connect
            VpnIntent.Disconnect -> _state.value = VpnState.Disconnect
            VpnIntent.Loading -> _state.value = VpnState.Loading
        }
    }

}