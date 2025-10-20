package com.chiennc.base.app.domain.usecase

import com.chiennc.base.app.data.repository.UserRepository
import com.chiennc.base.app.domain.model.User

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getUsers()
    }
}