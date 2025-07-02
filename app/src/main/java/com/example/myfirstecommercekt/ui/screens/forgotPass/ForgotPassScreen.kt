package com.example.myfirstecommercekt.ui.screens.forgotPass

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.utils.helpers.*

@Composable
fun ForgotPassScreen(viewModel: ForgotPassViewModel = hiltViewModel(), login: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ForgotPass(viewModel, login)
    }
}

@Composable
fun ForgotPass(viewModel: ForgotPassViewModel, login: () -> Unit) {
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
        Text("Actualizá tu contraseña:")
        Spacer(modifier = Modifier.padding(20.dp))
        EmailField(
            email,
            { viewModel.onRegisterChange(it, password, confirmPassword) },
            error = { emailError(it) }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(
            password,
            { viewModel.onRegisterChange(email, it, confirmPassword) },
            error = { passwordError(it) }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(email, password, it) },
            error = { confirmPasswordError(password, it) }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        UpdatePassButton(enabled = enabled) { viewModel.updatePassword(login) }
    }
}

@Composable
fun UpdatePassButton(enabled: Boolean, updatePass: () -> Unit) {
    Button(onClick = updatePass, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text("Actualizar")
    }
}