package com.chiennc.base.app

import android.app.Application
import com.chiennc.base.app.di.appModule
import com.chiennc.base.app.di.networkModule
import com.chiennc.base.app.di.repositoryModule
import com.chiennc.base.app.di.useCaseModule
import com.chiennc.base.app.di.viewModelModule
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}