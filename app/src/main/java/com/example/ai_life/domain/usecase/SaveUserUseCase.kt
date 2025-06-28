package com.example.ai_life.domain.usecase

import com.example.ai_life.data.user.UserRepository
import com.example.ai_life.domain.model.User

class SaveUserUseCase(
    private val repo: UserRepository
) {
    suspend operator fun invoke(user: User) = repo.saveUser(user)
}