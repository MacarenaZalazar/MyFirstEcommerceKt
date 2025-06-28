package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.viewmodel.*

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    toLogin: () -> Unit,
    toRegister: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Bienvenid@", textAlign = TextAlign.Center, fontSize = 30.sp
            )
            Spacer(Modifier.padding(8.dp))
            AppTitle()
            Spacer(Modifier.padding(25.dp))
            LogInButton(toLogin)
            Spacer(modifier = Modifier.padding(16.dp))
            Text("O si todavía no tenés una cuenta:")
            Spacer(modifier = Modifier.padding(16.dp))
            RegisterButton(toRegister)
        }
    }
}

@Composable
fun LogInButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Iniciá sesión")
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Registrate")
    }
}

