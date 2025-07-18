package com.example.toramarket.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.utils.helpers.*

@Composable
fun ProfileForm(viewModel: ProfileViewModel, toSplash: () -> Unit) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val edit by viewModel.edit.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()
    SimpleText(
        value = name,
        onChange = { viewModel.onRegisterChange(it, email, password, confirmPassword) },
        label = "Nombre completo",
        enabled = edit,
        error = { validateName(it) }

    )
    Spacer(modifier = Modifier.padding(16.dp))
    EmailField(
        email,
        enabled = false,
        onValueChange = { viewModel.onRegisterChange(name, it, password, confirmPassword) },
        error = { emailError(it) })
    Spacer(modifier = Modifier.padding(16.dp))
    PasswordField(
        password,
        enabled = edit,
        onValueChange = { viewModel.onRegisterChange(name, email, it, confirmPassword) },
        label = "Nueva contraseña",
        error = { passwordError(it) })

    Spacer(modifier = Modifier.padding(16.dp))
    if (edit) PasswordField(
        confirmPassword,
        { viewModel.onRegisterChange(name, email, password, it) },
        label = "Confirmar contraseña",
        error = { confirmPasswordError(password, it) })

    Spacer(modifier = Modifier.padding(16.dp))
    if (edit) {
        UpdateButton(isFormValid) { viewModel.updateProfile() }

    } else {
        EditButton() { viewModel.editProfile() }
    }
    Spacer(modifier = Modifier.padding(16.dp))
    LogoutButton { viewModel.logOut(toSplash) }
}

@Composable
fun EditButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Editar")
    }
}

@Composable
fun UpdateButton(enabled: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
        Text("Actualizar")
    }
}

@Composable
fun UpdateImage(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Seleccionar imagen")
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Cerrar sesión")
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.Logout,
            contentDescription = "LogOut",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}