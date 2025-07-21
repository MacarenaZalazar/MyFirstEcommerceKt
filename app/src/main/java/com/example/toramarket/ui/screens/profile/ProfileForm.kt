package com.example.toramarket.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.navigation.*
import com.example.toramarket.R
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
        label = stringResource(R.string.nombre_completo),
        enabled = editName,
        error = { validateName(it) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (!editName) {
                IconButton(onClick = { viewModel.toggleEditName() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.editar)
                    )
                }
            }

        }

    )
    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
    EmailField(
        email,
        enabled = false,
        onValueChange = {},
        error = { emailError(it) },
    )
    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
    PasswordField(
        password,
        enabled = editPassword,
        onValueChange = { viewModel.onRegisterChange(it, confirmPassword) },
        label = stringResource(R.string.nueva_contrase_a),
        error = { passwordError(it) },
        trailingIcon = {
            if (!editPassword) {
                IconButton(onClick = { viewModel.toggleEditPassword() }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.editar)
                    )
                }
            }
        },
    )

    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
    if (editPassword) {
        PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(password, it) },
            label = stringResource(R.string.confirmar_contrase_a),
            error = { confirmPasswordError(password, it) })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
        UpdateButton(isFormValid) { viewModel.updatePassword() }

    }
    if (editName) {
        UpdateButton(isFormValid) { viewModel.updateName() }


    }

    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
    if (!editPassword && !editName) {
        LogoutButton {
            viewModel.logOut {
                navController.navigate(
                    SplashScreenRoute
                )
            }
        }
    } else {
        Button(
            onClick = { viewModel.cancelEdit() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.cancelar))
        }
    }

}

@Composable
fun UpdateButton(enabled: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
        Text(stringResource(R.string.actualizar))
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.cerrar_sesi_n))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.Logout,
            contentDescription = "LogOut",
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_8))
        )
    }
}

