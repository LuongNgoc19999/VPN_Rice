package com.chiennc.base.app.presentation.vpn

sealed class VpnState {
    object Loading : VpnState()
    object Connect : VpnState()
    object Disconnect : VpnState()
}