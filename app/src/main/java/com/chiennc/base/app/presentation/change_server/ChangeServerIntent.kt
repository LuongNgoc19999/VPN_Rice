package com.chiennc.base.app.presentation.change_server

sealed class ChangeServerIntent {
    data class ChangeServer(val index: Int) : ChangeServerIntent()
}