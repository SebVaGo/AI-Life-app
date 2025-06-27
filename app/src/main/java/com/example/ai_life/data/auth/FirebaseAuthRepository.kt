package com.example.ai_life.data.auth

import com.example.ai_life.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun register(user: User, password: String):  Result<String> = try {
        val result = auth.createUserWithEmailAndPassword(user.email, password).await()
        val uid = result.user!!.uid
        Result.success(uid)
    }catch (e: Exception){
        Result.failure(e)
    }

    override suspend fun login(email: String, password: String): Result<String> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Result.success(result.user!!.uid)
    }catch (e: Exception){
        Result.failure(e)
    }
}