package com.example.toramarket.ui.screens.login

import androidx.compose.foundation.*
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
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
) {

    val isLoading by viewModel.isLoading.collectAsState()

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val enabled by viewModel.loginEnabled.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_16)), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            IconButton(
                onClick = { navController.popBackStack() },
                Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.volver),
                    modifier = Modifier.size(dimensionResource(R.dimen.padding_4))
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTitle()
                Spacer(Modifier.padding(36.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    EmailField(
                        email = email,
                        onValueChange = {
                            viewModel.onLoginChange(
                                email = it,
                                password = password
                            )
                        },
                        error = { emailError(it) })
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
                    PasswordField(
                        password = password,
                        onValueChange = { viewModel.onLoginChange(email = email, password = it) },
                        error = { passwordError(it) }
                    )
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                    ForgotPassword(
                        modifier = Modifier.align(Alignment.End)
                    ) { navController.navigate(ForgotPassScreenRoute) }
                    Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
                    LoginButton(enabled) {
                        viewModel.logIn {
                            navController.navigate(ProductsScreenRoute) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ForgotPassword(modifier: Modifier, forgotPass: () -> Unit) {
    Text(
        stringResource(R.string.olvidaste_tu_contrase_a),
        modifier = modifier.clickable { forgotPass() })
}


@Composable
fun LoginButton(enabled: Boolean, logIn: () -> Unit) {
    Button(
        onClick = { logIn() }, enabled = enabled, modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.ingresar))
    }
}

