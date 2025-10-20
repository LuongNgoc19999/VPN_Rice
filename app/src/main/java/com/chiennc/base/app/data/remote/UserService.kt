package com.chiennc.base.app.data.remote

import com.chiennc.base.app.domain.model.User
import retrofit2.http.GET

interface UserService {
    @GET("/users")
    suspend fun getUsers(): List<User>
}