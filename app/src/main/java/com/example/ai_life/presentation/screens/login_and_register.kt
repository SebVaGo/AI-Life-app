package com.example.ai_life.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai_life.R
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import com.example.ai_life.presentation.screens.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun loginScreen (navController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()
    var selectedTab by remember { mutableStateOf("Ingresar") }
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            logo(modifier = Modifier.weight(3f))
            TabSelector(selectedTab = selectedTab) {
                selectedTab = it
            }
            Spacer(modifier = Modifier.height(32.dp))
            when (selectedTab) {
                "Ingresar" -> LoginForm(viewModel = loginViewModel)
                "Registrarte" -> RegisterForm()
            }
        }
    }
}

@Composable
fun logo(modifier: Modifier = Modifier) {
        Box(modifier = Modifier.fillMaxWidth().padding(start = 80.dp, end = 80.dp,top=60.dp , bottom = 30.dp)) {
            Column(modifier = Modifier.fillMaxWidth()
                , verticalArrangement = Arrangement.Top, horizontalAlignment =Alignment.CenterHorizontally ) {
                val image = painterResource(id = R.drawable.logo)
                Image(
                    painter = image,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    )
                Spacer(modifier=Modifier.padding(15.dp))
                Text(
                    "AI LIVE",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1B3CAA)
                )
            }
    }
}

@Composable
fun TabSelector(selectedTab: String, onTabSelected: (String) -> Unit) {
    val backgroundColor = Color(0xFF040A7E)
    val shape = RoundedCornerShape(50)

    Row(
        modifier = Modifier
            .fillMaxWidth().padding(10.dp)
            .clip(shape)
            .background(backgroundColor)
            .height(40.dp),
    ) {
        listOf("Ingresar", "Registrarte").forEach { tab ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(tab) }
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = tab,
                    color = Color.White,
                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                )
                if (selectedTab == tab) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth(0.6f)
                            .background(Color(0xFFFFB400))
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(viewModel: LoginViewModel){
    Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = { Text("Email") },
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray.copy(alpha = 0.2f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp, start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Transparent.copy(alpha = 0.1f))

        )
        if (viewModel.showErrors && viewModel.emailError.isNotEmpty()) {
            Text(viewModel.emailError, color = Color.Red, fontSize = 12.sp)
        }

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = { Text("Contraseña") },
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray.copy(alpha = 0.2f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp, start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Transparent.copy(alpha = 0.1f))

        )
        if (viewModel.showErrors && viewModel.passwordError.isNotEmpty()) {
            Text(viewModel.passwordError, color = Color.Red, fontSize = 12.sp)
        }
        Row {
            Checkbox(
                checked = viewModel.rememberMe,
                onCheckedChange = { viewModel.rememberMe = it }
            )

            Text(
                "Recordar", fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.fillMaxWidth()
                    .padding(top = 15.dp), color = Color(0xFF040A7E)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            viewModel.showErrors = true
            if (viewModel.validate()) {
            }
        }, modifier = Modifier.fillMaxWidth().height(50.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF040A7E),
        contentColor = Color.White ))
        {
            Text("Acceder", fontSize = 17.sp, color=Color.White)
        }
    }
}

@Composable
fun RegisterForm() {


}


