package com.example.myfirstecommercekt.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), toLogin: () -> Unit) {
    val isLoading by viewModel.isLoading.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Register(viewModel, toLogin)
        }
    }
}

@Composable
fun Register(viewModel: RegisterViewModel, toLogin: () -> Unit) {

    val name by viewModel.name.collectAsState()
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
        SimpleText(
            value = name,
            onChange = { viewModel.onRegisterChange(it, email, password, confirmPassword) },
            label = "Nombre completo",
            error = false
        )
        Spacer(modifier = Modifier.padding(16.dp))

        EmailField(
            email,
            { viewModel.onRegisterChange(name, it, password, confirmPassword) },
            error = false
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            "La constraseña debe tener más de 8 caracteres, e incluir una mayúscula, un número y un símbolo.",
            fontSize = 10.sp
        )
        PasswordField(
            password,
            { viewModel.onRegisterChange(name, email, it, confirmPassword) },
            error = false
        )

        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(name, email, password, it) },
            error = false
        )

        Spacer(modifier = Modifier.padding(16.dp))
        SingUpButton(enabled = enabled) { viewModel.register(toLogin) }
    }
}

@Composable
fun SingUpButton(enabled: Boolean, register: () -> Unit) {
    Button(onClick = register, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text("Registrarme")
    }
}

