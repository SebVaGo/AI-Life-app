package com.example.ai_life

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.snap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ai_life.presentation.navigation.NavGraph
import com.example.ai_life.ui.theme.Ai_LifeTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp

import com.google.firebase.database.database

import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        val dbRef = Firebase.database.reference.child("connectionTest")

        dbRef.setValue("Test Connection")
            .addOnSuccessListener { Log.d("FireTest", "Escritura") }
            .addOnFailureListener {e -> Log.e("FireTest", "Error de escritura")}

        dbRef.get()
            .addOnSuccessListener { snap ->
                val v = snap.getValue(String::class.java)
                Log.d("FireTest", "Lectura Ok")
            }
            .addOnFailureListener { e ->
                Log.e("FireTest","Error lectura", e)
            }
        setContent {
            Ai_LifeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    NavGraph()
                }
                }
            }

        }
    }



