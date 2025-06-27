package com.example.myfirstecommercekt.presentation

import android.R
import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstecommercekt.components.EmailField
import com.example.myfirstecommercekt.components.PasswordField
import com.example.myfirstecommercekt.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Register( viewModel)
    }
}

@Composable
fun Register( viewModel: RegisterViewModel) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro")
        Spacer(modifier = Modifier.padding(16.dp))
        Text("¡Creá tu cuenta!")
        Spacer(modifier = Modifier.padding(20.dp))
        EmailField(email, {viewModel.onEmailChange(it)})
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(password, { viewModel.onPasswordChange(it) })

        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(confirmPassword, { viewModel.onConfirmPasswordChange(it) })

        Spacer(modifier = Modifier.padding(16.dp))
        SingInButton()
    }
}

@Composable
fun SingInButton() {
    Button(onClick = {}, Modifier.fillMaxWidth()) {
        Text("Registrarme")
    }
}

