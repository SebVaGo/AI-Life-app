package com.example.ai_life.presentation.screens.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)

    var emailError by mutableStateOf("")
    var passwordError by mutableStateOf("")
    var showErrors by mutableStateOf(false)

    fun validate(): Boolean {
        emailError = ""
        passwordError = ""
        var isValid = true

        if (email.isBlank()) {
            emailError = "El correo no puede estar vacío"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Correo inválido"
            isValid = false
        }

        if (password.isBlank()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        }

        return isValid
    }
}
