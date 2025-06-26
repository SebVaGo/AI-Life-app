package com.example.ai_life.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun login(email: String, password: String): Flow<Result<FirebaseUser>> = flow {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Result.success(result.user!!))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun register(email: String, password: String): Flow<Result<FirebaseUser>> = flow {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(Result.success(result.user!!))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun logout() {
        auth.signOut()
    }
}
