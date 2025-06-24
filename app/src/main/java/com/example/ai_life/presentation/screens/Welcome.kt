package com.example.ai_life.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ai_life.R

@Composable
fun welcomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF040A7E))){
        val image = painterResource(id= R.drawable.welcome)
        Image(
            painter = image ,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement= Arrangement.Bottom
            , horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Pulse aquí", fontSize = 30.sp, color = Color.White,
                modifier = Modifier.padding(bottom = 40.dp)
                    .clickable{
                        navController.navigate("login_register")
                    }
                 )
        }
    }
}

