package com.example.toramarket.ui.screens.splash

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.ui.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.ui.navigation.*

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getLoggedUser()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (state) {
            is UIState.Loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }

            is UIState.Error -> Text(
                "Ocurrió un error al cargar la aplicación", modifier = Modifier.align(
                    Alignment.Center
                )
            )

            is UIState.Success -> {
                val logged = state.data
                if (logged) {
                    navController.navigate(ProductsScreenRoute)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AppTitle()
                        Spacer(Modifier.padding(25.dp))
                        LogInButton({ navController.navigate(LogInScreenRoute) })
                        Spacer(modifier = Modifier.padding(16.dp))
                        RegisterButton {
                            navController.navigate(LogInScreenRoute)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LogInButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Iniciá sesión")
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Text(
        "Registrate",
        Modifier.clickable { onClick() }, textDecoration = TextDecoration.Underline,
    )
}

