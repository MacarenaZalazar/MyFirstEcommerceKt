package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.example.toramarket.utils.helpers.*

@Composable
fun PaymentMethodSelector(
    selectedMethod: PaymentMethod,
    onMethodSelected: (PaymentMethod) -> Unit
) {
    Column {

        Text(
            "¿Cómo querés pagar?",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        Row {

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMethod == PaymentMethod.CASH,
                    onClick = { onMethodSelected(PaymentMethod.CASH) }
                )
                Text("Efectivo")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMethod == PaymentMethod.CARD,
                    onClick = { onMethodSelected(PaymentMethod.CARD) }
                )
                Text("Tarjeta")
            }
        }
    }
}