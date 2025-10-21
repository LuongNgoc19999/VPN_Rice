package com.chiennc.base.app.presentation.splash

import com.chiennc.base.app.domain.model.User

sealed class SplashState {
    object Loading : SplashState()
    data class Success(val message: String) : SplashState()
    data class Error(val message: String) : SplashState()
}