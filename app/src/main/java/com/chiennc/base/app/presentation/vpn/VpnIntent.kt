package com.chiennc.base.app.presentation.vpn

sealed class VpnIntent {
    data class Connect(val up: Double = 0.0, val down: Double = 0.0, val time: Long = 0) :
        VpnIntent()

    object Loading : VpnIntent()
    data class Disconnect(val time: Long = 0) : VpnIntent()
}