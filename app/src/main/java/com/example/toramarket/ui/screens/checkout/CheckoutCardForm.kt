package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*

@Composable
fun CheckoutCardForm(viewModel: CheckoutViewModel = hiltViewModel()) {

    val name by viewModel.name.collectAsState()
    val number by viewModel.number.collectAsState()
    val expiration by viewModel.expiration.collectAsState()
    val ccv by viewModel.ccv.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Por favor, ingresá los datos de tu tarjeta",
            style = MaterialTheme.typography.titleMedium
        )

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
            label = { Text("Número de la tarjeta") },
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
            label = { Text("Nombre (como figura en la tarjeta)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

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
                label = { Text("MM/YY") },
                modifier = Modifier.weight(0.5f)
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
                modifier = Modifier.weight(0.5f)
            )
        }

    }
}

