package com.example.toramarket.ui.screens.forgotPass

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
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
fun ForgotPassScreen(
    viewModel: ForgotPassViewModel = hiltViewModel(),
    navController: NavController
) {
    val loading by viewModel.isLoading.collectAsState()

    val email by viewModel.email.collectAsState()
    val isEmailValid by viewModel.isEmailValid.collectAsState()

    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val enabled by viewModel.isFormValid.collectAsState()
    val user by viewModel.user.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { it ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(dimensionResource(R.dimen.padding_16))
        ) {
            if (loading) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.volver),
                        modifier = Modifier.size(dimensionResource(R.dimen.padding_4))
                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    AppTitle()
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
                    Text(stringResource(R.string.actualiz_tu_contrase_a))
                    Spacer(modifier = Modifier.padding(20.dp))
                    if (!user) {
                        EmailField(
                            email,
                            { viewModel.onEmailChange(it) },
                            error = { emailError(it) }
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { viewModel.validateEmail() },
                            enabled = isEmailValid
                        ) { Text(stringResource(R.string.siguiente)) }
                    } else {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = {},
                            enabled = false,
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                        PasswordField(
                            password,
                            { viewModel.onPasswordChange(it, confirmPassword) },
                            error = { passwordError(it) }
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                        PasswordField(
                            confirmPassword,
                            { viewModel.onPasswordChange(password, it) },
                            error = { confirmPasswordError(password, it) },
                            label = stringResource(R.string.confirmar_contrase_a)
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                        UpdatePassButton(enabled = enabled) {
                            viewModel.updatePassword {
                                navController.navigate(
                                    LogInScreenRoute
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))

                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            navController.popBackStack()
                        }) { Text(stringResource(R.string.cancelar)) }
                    }
                }
            }
        }
    }
}

@Composable
fun UpdatePassButton(enabled: Boolean, updatePass: () -> Unit) {
    Button(onClick = updatePass, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.actualizar))
    }
}