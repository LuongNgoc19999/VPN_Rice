package com.chiennc.base.app.di

import com.chiennc.base.app.domain.usecase.GetUsersUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUsersUseCase(get()) }
}