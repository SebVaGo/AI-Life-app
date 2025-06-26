package com.example.ai_life.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    fun login(email: String, password: String): Flow<Result<FirebaseUser>>
    fun register(email: String, password: String): Flow<Result<FirebaseUser>>
    fun logout()
}
