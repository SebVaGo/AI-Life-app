package com.example.ai_life.presentation.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_life.domain.model.Consulta
import com.example.ai_life.domain.model.ConsultaGuardada
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.google.firebase.database.ktx.database
import android.util.Log
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConsultaViewModel : ViewModel() {

    companion object {
        private const val TAG = "ConsultaVM"
    }

    // ⚠️ Si tus números están directamente bajo el root de la RTDB,
    //    usa Firebase.database.reference.
    //    Si están dentro de /consultas, haz Firebase.database.reference.child("consultas")
    private val db = Firebase.database.reference

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code

    private val _consultas = MutableStateFlow<List<ConsultaGuardada>>(emptyList())
    val consultas: StateFlow<List<ConsultaGuardada>> = _consultas

    private val _status = MutableStateFlow<String?>(null)
    val status: StateFlow<String?> = _status

    init {
        loadHistorial()
    }

    fun onCodeChange(new: String) {
        _code.value = new
    }

    fun searchConsulta() {
        val lookup = code.value.trim()
        Log.d(TAG, "Buscando codigo: '$lookup'")
        if (lookup.isEmpty()) {
            _status.value = "Ingresa un código"
            _consultas.value = emptyList()
            return
        }

        _status.value = "Buscando..."
        _consultas.value = emptyList()

        db.child(lookup).get()
            .addOnSuccessListener { snap ->
                if (snap.exists()) {
                    Log.d(TAG, "Codigo $lookup encontrado en RTDB")
                    val bpm = snap.child("bpm").getValue(Int::class.java)
                    val spo2 = snap.child("spo2").getValue(Int::class.java)
                    val temp = snap.child("temperatura").getValue(Double::class.java)
                    if (bpm != null && spo2 != null && temp != null) {
                        val consulta = ConsultaGuardada(
                            code = lookup,
                            bpm = bpm,
                            spo2 = spo2,
                            temperatura = temp,
                            diagnostico = "",
                            fecha = 0L
                        )
                        _consultas.value = listOf(consulta)
                        _status.value = "Consulta encontrada"
                        Log.d(TAG, "Datos de consulta cargados: $consulta")
                    } else {
                        _status.value = "Datos incompletos para el código $lookup"
                        Log.w(TAG, "Datos incompletos para $lookup")
                    }
                } else {
                    _status.value = "No se encontró el código $lookup"
                    Log.d(TAG, "Codigo $lookup no existe")
                }
            }
            .addOnFailureListener { e ->
                _status.value = "Error al buscar: ${e.message}"
                Log.e(TAG, "Error buscando codigo", e)
            }
    }

    private fun loadHistorial() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Log.d(TAG, "Cargando historial para UID=$uid")
        viewModelScope.launch {
            try {
                val snapshot = db.child("users").child(uid).child("consultas").get().await()
                val list = snapshot.children.mapNotNull { it.getValue(ConsultaGuardada::class.java) }
                _consultas.value = list
                Log.d(TAG, "Historial cargado: ${list.size} registros")
            } catch (e: Exception) {
                Log.e(TAG, "Error cargando historial", e)
            }
        }
    }
}