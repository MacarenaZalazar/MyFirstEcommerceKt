package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myfirstecommercekt.ui.components.AppTitle
import com.example.myfirstecommercekt.viewmodel.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel(), toLogin :()-> Unit,toRegister :()-> Unit) {
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
            Text("o si todavía no tenés una cuenta")
            Spacer(modifier = Modifier.padding(16.dp))
            RegisterButton(toRegister)
        }
    }
}

@Composable
fun LogInButton(onClick: ()-> Unit) {
    Button(onClick = onClick) {
        Text("Iniciá sesión")
    }
}

@Composable
fun RegisterButton(onClick: ()-> Unit) {
    Button(onClick = onClick) {
        Text("Registrate")
    }
}

