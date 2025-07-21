package com.example.toramarket.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.style.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.*
import com.example.toramarket.ui.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController) {
    val state = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getUser()
        viewModel.snackbarMessage.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

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
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar({ Text(stringResource(R.string.mi_perfil)) }, actions = {
                        IconButton(onClick = { viewModel.getUser() }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    })
                },
            ) { it ->
                Box(
                    Modifier
                        .padding(it), contentAlignment = Alignment.Center
                ) {
                    Column(
                        Modifier
                            .padding(dimensionResource(R.dimen.padding_16)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
                        LogoutButton { viewModel.logOut { navController.navigate(SplashScreenRoute) } }
                    }
                }
            }
        }

        is UIState.Success -> {
            Scaffold(
                topBar = { CenterAlignedTopAppBar({ Text(stringResource(R.string.mi_perfil)) }) },
                snackbarHost = { SnackbarHost(snackbarHostState) }) { it ->
                Column(
                    Modifier
                        .padding(it)
                        .padding(dimensionResource(R.dimen.padding_16))
                ) {
                    ProfileImage(viewModel)
                    Spacer(Modifier.padding(dimensionResource(R.dimen.padding_8)))
                    ProfileForm(viewModel, navController)
                }
            }


        }

    }
}