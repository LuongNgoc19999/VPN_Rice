package com.chiennc.base.app.presentation.vpn

sealed class VpnState {
    object Loading : VpnState()
    data class Connect(val up: Double = 0.0, val down: Double = 0.0, val time: Long = 0) : VpnState()
    data class Disconnect(val time: Long = 0) : VpnState()
}