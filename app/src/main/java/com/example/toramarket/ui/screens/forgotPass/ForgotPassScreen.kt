package com.example.toramarket.ui.screens.forgotPass

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
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
                .padding(16.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    AppTitle()
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text("Actualizá tu contraseña:")
                    Spacer(modifier = Modifier.padding(20.dp))
                    if (!user) {
                        EmailField(
                            email,
                            { viewModel.onEmailChange(it) },
                            error = { emailError(it) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { viewModel.validateEmail() },
                            enabled = isEmailValid
                        ) { Text("Siguiente") }
                    } else {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = {},
                            enabled = false,
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        PasswordField(
                            password,
                            { viewModel.onPasswordChange(it, confirmPassword) },
                            error = { passwordError(it) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        PasswordField(
                            confirmPassword,
                            { viewModel.onPasswordChange(password, it) },
                            error = { confirmPasswordError(password, it) },
                            label = "Confirmar contraseña"
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        UpdatePassButton(enabled = enabled) {
                            viewModel.updatePassword({
                                navController.navigate(
                                    LogInScreenRoute
                                )
                            })
                        }
                        Spacer(modifier = Modifier.padding(8.dp))

                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            navController.popBackStack()
                        }) { Text("Cancelar") }
                    }
                }
            }
        }
    }
}

@Composable
fun UpdatePassButton(enabled: Boolean, updatePass: () -> Unit) {
    Button(onClick = updatePass, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
        Text("Actualizar")
    }
}