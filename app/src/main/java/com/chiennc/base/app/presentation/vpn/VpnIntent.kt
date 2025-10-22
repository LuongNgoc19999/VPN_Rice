package com.chiennc.base.app.presentation.vpn

sealed class VpnIntent {
    object Connect : VpnIntent()
    object Loading : VpnIntent()
    object Disconnect : VpnIntent()
}