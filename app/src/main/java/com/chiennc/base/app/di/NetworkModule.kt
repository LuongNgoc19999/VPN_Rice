package com.chiennc.base.app.di

import com.chiennc.base.app.data.remote.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

val networkModule = module {
    single { OkHttpClient.Builder().build() }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(UserService::class.java) }
}