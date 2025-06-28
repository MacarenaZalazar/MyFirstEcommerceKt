package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstecommercekt.ui.components.AppTitle
import com.example.myfirstecommercekt.ui.components.EmailField
import com.example.myfirstecommercekt.ui.components.PasswordField
import com.example.myfirstecommercekt.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, goHome: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Register(viewModel, goHome)
    }
}

@Composable
fun Register(viewModel: RegisterViewModel, goHome: () -> Unit) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val enabled by viewModel.isFormValid.collectAsState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppTitle()
        Spacer(modifier = Modifier.padding(16.dp))
        Text("¡Creá tu cuenta!")
        Spacer(modifier = Modifier.padding(20.dp))
        EmailField(email, { viewModel.onRegisterChange(it, password, confirmPassword) })
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(password, { viewModel.onRegisterChange(email, it, confirmPassword) })

        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(confirmPassword, { viewModel.onRegisterChange(email, password, it) })

        Spacer(modifier = Modifier.padding(16.dp))
        SingInButton(enabled = enabled) { viewModel.register(goHome) }
    }
}

@Composable
fun SingInButton(enabled: Boolean, register: () -> Unit) {
    Button(onClick = register, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text("Registrarme")
    }
}

