package com.example.myfirstecommercekt.presentation

import android.annotation.SuppressLint
import android.util.Log.i
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstecommercekt.components.AppTitle
import com.example.myfirstecommercekt.components.EmailField
import com.example.myfirstecommercekt.components.PasswordField
import com.example.myfirstecommercekt.viewmodel.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {

    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTitle()
                Spacer(Modifier.padding(36.dp))
                Login(Modifier, viewModel)
            }
        }
    }

}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val enabled by viewModel.loginEnabled.collectAsState()



    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        EmailField(email) { viewModel.onLoginChange(email = it, password = password) }
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(password) { viewModel.onLoginChange(email = email, password = it) }
        Spacer(modifier = Modifier.padding(8.dp))
        ForgotPassword(modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(16.dp))
        LoginButton(enabled) {
            viewModel.logIn()
        }
    }

}


@Composable
fun ForgotPassword(modifier: Modifier) {
    Text("¿Olvidaste tu contraseña?", modifier = modifier.clickable {})
}


@Composable
fun LoginButton(enabled: Boolean, logIn: () -> Unit) {
    Button(
        onClick = { logIn() }, enabled = enabled
    ) {
        Text("Ingresar")
    }
}

