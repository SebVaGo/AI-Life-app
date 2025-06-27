package com.example.ai_life.data.auth

import com.example.ai_life.domain.model.User

interface AuthRepository {
    suspend fun register (user: User, password: String): Result<String>
    suspend fun login (email: String, password: String): Result<String>
}
