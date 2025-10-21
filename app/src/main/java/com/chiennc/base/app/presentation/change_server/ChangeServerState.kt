package com.chiennc.base.app.presentation.change_server

import com.chiennc.base.app.domain.model.User

//sealed class ChangeServerState {
//    object Loading : ChangeServerState()
//    data class Success(val index: Int) : ChangeServerState()
//    data class Error(val message: String) : ChangeServerState()
//}
data class ChangeServerState(
    val index: Int = 0
)