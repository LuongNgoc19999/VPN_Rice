package com.chiennc.base.app.data.repository

import com.chiennc.base.app.data.remote.UserService
import com.chiennc.base.app.domain.model.User

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return userService.getUsers()
    }
}