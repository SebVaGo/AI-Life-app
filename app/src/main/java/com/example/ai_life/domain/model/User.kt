package com.example.ai_life.domain.model

data class User (
    val uid: String? = null,
    val nombres: String,
    val apellidos: String,
    val dni: String,
    val localidad: String,
    val fechaNacimiento: String,
    val sexo: String,
    val email: String
)