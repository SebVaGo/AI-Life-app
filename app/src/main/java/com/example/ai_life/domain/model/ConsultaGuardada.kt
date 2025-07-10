package com.example.ai_life.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ConsultaGuardada(
    var code: String = "",
    val bpm: Int = 0,
    val spo2: Int = 0,
    val temperatura: Double = 0.0,
    val diagnostico: String = "",
    val fecha: Long = 0L
)
