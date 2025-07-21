package com.example.toramarket.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.components.*
import com.example.toramarket.ui.navigation.*
import com.example.toramarket.utils.helpers.*

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), navController: NavController) {
    val isLoading by viewModel.isLoading.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_16))
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Register(viewModel,
                { navController.navigate(LogInScreenRoute) { launchSingleTop = true } }
            )
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
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
        Text(stringResource(R.string.cre_tu_cuenta))
        Spacer(modifier = Modifier.padding(20.dp))
        SimpleText(
            value = name,
            onChange = { viewModel.onRegisterChange(it, email, password, confirmPassword) },
            label = stringResource(R.string.nombre_completo),
            error = { validateName(it) })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))

        EmailField(
            email,
            { viewModel.onRegisterChange(name, it, password, confirmPassword) },
            error = { emailError(it) })
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
        Text(
            stringResource(R.string.la_constrase_a_debe_tener_m_s_de_8_caracteres_e_incluir_una_may_scula_un_n_mero_y_un_s_mbolo),
            fontSize = 10.sp
        )
        PasswordField(
            password,
            { viewModel.onRegisterChange(name, email, it, confirmPassword) },
            error = { passwordError(it) })

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
        PasswordField(
            confirmPassword,
            { viewModel.onRegisterChange(name, email, password, it) },
            error = { confirmPasswordError(password, it) })

        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
        SingUpButton(enabled = enabled) { viewModel.register(toLogin) }
    }
}

@Composable
fun SingUpButton(enabled: Boolean, register: () -> Unit) {
    Button(onClick = register, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.registrarme))
    }
}

