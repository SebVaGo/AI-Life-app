package com.example.ai_life.domain.usecase

import com.example.ai_life.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String) =
        repository.register(email, password)
}
