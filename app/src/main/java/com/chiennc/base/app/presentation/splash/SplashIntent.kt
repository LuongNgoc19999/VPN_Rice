package com.chiennc.base.app.presentation.splash

sealed class SplashIntent {
    object LoadAds : SplashIntent()
    object ChangeScreen : SplashIntent()
}