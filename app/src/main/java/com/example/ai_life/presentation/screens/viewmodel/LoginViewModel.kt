package com.example.ai_life.presentation.screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_life.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
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

    fun signIn(onResult: (Boolean, String?) -> Unit) {
        if (!validate()) {
            showErrors = true
            onResult(false, null)
            return
        }
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                onResult(result.isSuccess, result.exceptionOrNull()?.message)
            }
        }
    }
}
