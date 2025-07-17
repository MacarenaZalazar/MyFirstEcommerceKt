package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.*

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    goHome: () -> Unit,
    goBack: () -> Unit
) {

    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getOrder()
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        when (state) {
            is UIState.Loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }

            is UIState.Success -> {
                Column {

                    Resume(state.data, goBack)
                    CheckoutForm(viewModel) { }
                }

            }

            is UIState.Error -> {
                // Show error message
            }
        }

    }
}

@Composable
fun CheckoutForm(
    viewModel: CheckoutViewModel = hiltViewModel(),
    goHome: () -> Unit,
) {
    val showDialog by viewModel.showDialog.collectAsState()
    val dialogMessage by viewModel.dialogMessage.collectAsState()

    val name by viewModel.name.collectAsState()
    val number by viewModel.number.collectAsState()
    val expiration by viewModel.expiration.collectAsState()
    val ccv by viewModel.ccv.collectAsState()
    val isValid by viewModel.isValid.collectAsState()


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            title = { Text("Payment Status") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = {
                    viewModel.closeDialog()
                    goHome()
                }) {
                    Text("OK")
                }
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Checkout", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = number,
            onValueChange = {
                viewModel.onChange(
                    it,
                    name,
                    expiration,
                    ccv
                )
            },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                viewModel.onChange(
                    number,
                    it,
                    expiration,
                    ccv
                )
            },
            label = { Text("Card Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = expiration,
            onValueChange = {
                viewModel.onChange(
                    number,
                    name,
                    it,
                    ccv
                )
            },
            label = { Text("Expiration Date") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = ccv,
            onValueChange = {
                viewModel.onChange(
                    number,
                    name,
                    expiration,
                    it
                )
            },
            label = { Text("CCV") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.pay() },
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pagar")
        }
    }
}