package com.example.ai_life.presentation.screens.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_life.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    var nombres by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var dni by mutableStateOf("")
    var localidad by mutableStateOf("")
    var fechaNacimiento by mutableStateOf("")
    var sexo by mutableStateOf("")
    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")

    var errorNombres by mutableStateOf("")
    var errorApellidos by mutableStateOf("")
    var errorDni by mutableStateOf("")
    var errorLocalidad by mutableStateOf("")
    var errorFechaNacimiento by mutableStateOf("")
    var errorSexo by mutableStateOf("")
    var errorCorreo by mutableStateOf("")
    var errorContrasena by mutableStateOf("")

    var showErrors by mutableStateOf(false)

    fun validar(): Boolean {
        showErrors = true
        var valido = true

        if (nombres.isBlank()) {
            errorNombres = "Campo obligatorio"
            valido = false
        } else errorNombres = ""

        if (apellidos.isBlank()) {
            errorApellidos = "Campo obligatorio"
            valido = false
        } else errorApellidos = ""

        if (dni.isBlank()) {
            errorDni = "Campo obligatorio"
            valido = false
        } else if (!dni.all { it.isDigit() }) {
            errorDni = "Solo números"
            valido = false
        } else if (dni.length != 8) {
            errorDni = "Debe tener 8 dígitos"
            valido = false
        } else errorDni = ""

        if (localidad.isBlank()) {
            errorLocalidad = "Campo obligatorio"
            valido = false
        } else errorLocalidad = ""

        if (fechaNacimiento.isBlank()) {
            errorFechaNacimiento = "Campo obligatorio"
            valido = false
        } else errorFechaNacimiento = ""

        if (sexo.isBlank()) {
            errorSexo = "Campo obligatorio"
            valido = false
        } else errorSexo = ""

        if (correo.isBlank()) {
            errorCorreo = "Campo obligatorio"
            valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorCorreo = "Correo inválido"
            valido = false
        } else errorCorreo = ""

        if (contrasena.isBlank()) {
            errorContrasena = "Campo obligatorio"
            valido = false
        } else if (contrasena.length < 6) {
            errorContrasena = "Debe tener mínimo 6 dígitos"
            valido = false
        } else errorContrasena = ""

        return valido
    }

    fun signUp(onResult: (Boolean, String?) -> Unit) {
        if (!validar()) {
            onResult(false, null)
            return
        }
        viewModelScope.launch {
            registerUseCase(correo, contrasena).collect { result ->
                onResult(result.isSuccess, result.exceptionOrNull()?.message)
            }
        }
    }
}
