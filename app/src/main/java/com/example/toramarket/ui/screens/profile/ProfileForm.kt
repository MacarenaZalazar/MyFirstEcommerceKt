package com.example.toramarket.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.ui.navigation.*
import com.example.toramarket.utils.helpers.*

@Composable
fun ProfileForm(viewModel: ProfileViewModel, navController: NavController) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val editName by viewModel.editName.collectAsState()
    val editPassword by viewModel.editPassword.collectAsState()

    val isFormValid by viewModel.isFormValid.collectAsState()
    SimpleText(
        value = name,
        onChange = { viewModel.onRegisterChange(it) },
        label = "Nombre completo",
        enabled = editName,
        error = { validateName(it) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (!editName) {
                IconButton(onClick = { viewModel.toggleEditName() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
            }

        }

    )
    Spacer(modifier = Modifier.padding(8.dp))
    EmailField(
        email,
        enabled = false,
        onValueChange = {},
        error = { emailError(it) },
    )
    Spacer(modifier = Modifier.padding(8.dp))
    PasswordField(
        password,
        enabled = editPassword,
        onValueChange = { viewModel.onRegisterChange(it, confirmPassword) },
        label = "Nueva contraseña",
        error = { passwordError(it) },
        trailingIcon = {
            if (!editPassword) {
                IconButton(onClick = { viewModel.toggleEditPassword() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
            }
        },
    )

    Spacer(modifier = Modifier.padding(8.dp))
    if (editPassword) {
        PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(password, it) },
            label = "Confirmar contraseña",
            error = { confirmPasswordError(password, it) })
        Spacer(modifier = Modifier.padding(8.dp))
        UpdateButton(isFormValid) { viewModel.updatePassword() }

    }
    if (editName) {
        UpdateButton(isFormValid) { viewModel.updateName() }


    }

    Spacer(modifier = Modifier.padding(8.dp))
    if (!editPassword && !editName) {
        LogoutButton {
            viewModel.logOut({
                navController.navigate(
                    SplashScreenRoute
                )
            })
        }
    } else {
        Button(
            onClick = { viewModel.cancelEdit() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}

@Composable
fun UpdateButton(enabled: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
        Text("Actualizar")
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

