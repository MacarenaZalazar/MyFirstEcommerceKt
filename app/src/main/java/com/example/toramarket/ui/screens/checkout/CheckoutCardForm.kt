package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.utils.helpers.*

@Composable
fun CheckoutCardForm(viewModel: CheckoutViewModel = hiltViewModel()) {

    val name by viewModel.name.collectAsState()
    val number by viewModel.number.collectAsState()
    val expiration by viewModel.expiration.collectAsState()
    val cvv by viewModel.cvv.collectAsState()

    val cardType by viewModel.cardType.collectAsState()

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

        SimpleText(
            value = number,
            onChange = {
                viewModel.onChange(
                    it,
                    name,
                    expiration,
                    cvv
                )
            },
            enabled = true,
            error = { validateCardNumber(it) },
            label = "Número de la tarjeta",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SimpleText(
            value = name,
            onChange = {
                viewModel.onChange(
                    number,
                    it,
                    expiration,
                    cvv
                )
            },
            enabled = true,
            error = { validateName(it) },
            label = "Nombre (como figura en la tarjeta",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            SimpleText(
                value = expiration,
                onChange = {
                    viewModel.onChange(
                        number,
                        name,
                        formatExpiryInput(it),
                        cvv
                    )
                },
                enabled = true,
                error = { validateExpiration(it) },
                label = "MM/YY",
                modifier = Modifier.weight(0.5f),
            )

            Spacer(modifier = Modifier.height(8.dp))

            SimpleText(
                value = cvv,
                onChange = {
                    viewModel.onChange(
                        number,
                        name,
                        expiration,
                        it
                    )
                },
                error = { validateCcv(it, cardType) },
                label = "CCV",
                modifier = Modifier.weight(0.5f)
            )
        }

    }
}

