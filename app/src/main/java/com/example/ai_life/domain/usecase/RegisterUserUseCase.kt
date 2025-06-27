package com.example.ai_life.domain.usecase

import com.example.ai_life.data.auth.AuthRepository
import com.example.ai_life.domain.model.User

class RegisterUserUseCase(
    private val authRepo: AuthRepository
) {
    suspend operator fun invoke(user: User, password: String) =
        authRepo.register(user, password)
}