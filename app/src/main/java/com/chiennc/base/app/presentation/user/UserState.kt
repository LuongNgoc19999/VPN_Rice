package com.chiennc.base.app.presentation.user

import com.chiennc.base.app.domain.model.User

sealed class UserState {
    object Loading : UserState()
    data class Success(val users: List<User>) : UserState()
    data class Error(val message: String) : UserState()
}