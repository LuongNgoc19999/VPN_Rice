package com.chiennc.base.app.data.repository

import com.chiennc.base.app.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}