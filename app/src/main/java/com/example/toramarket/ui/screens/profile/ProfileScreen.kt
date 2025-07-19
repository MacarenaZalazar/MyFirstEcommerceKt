package com.example.toramarket.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), toSplash: () -> Unit) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) { viewModel.getUser() }

    val snackbarHostState = remember { SnackbarHostState() }

    when (state) {
        is UIState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }

        is UIState.Error -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.message, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(16.dp))
                LogoutButton { viewModel.logOut(toSplash) }
            }
        }

        is UIState.Success -> {
            Scaffold(
                topBar = { CenterAlignedTopAppBar({ Text("Mi Perfil") }) },
                snackbarHost = { SnackbarHost(snackbarHostState) }) { it ->
                Column(
                    Modifier
                        .padding(it)
                        .padding(16.dp)
                ) {
                    ProfileImage(viewModel, snackbarHostState)
                    Spacer(Modifier.padding(8.dp))
                    ProfileForm(viewModel) { toSplash }
                }
            }
        }
    }

}