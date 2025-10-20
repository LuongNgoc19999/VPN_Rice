package com.chiennc.base.app.di

import com.chiennc.base.app.data.repository.UserRepository
import com.chiennc.base.app.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}