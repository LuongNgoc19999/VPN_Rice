package com.chiennc.base.app.di

import com.chiennc.base.app.presentation.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
}