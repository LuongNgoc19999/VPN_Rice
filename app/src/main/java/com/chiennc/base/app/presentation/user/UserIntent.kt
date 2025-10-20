package com.chiennc.base.app.presentation.user

sealed class UserIntent {
    object FetchUsers : UserIntent()
}