package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import com.example.toramarket.R
import com.example.toramarket.utils.helpers.*

@Composable
fun PaymentMethodSelector(
    selectedMethod: PaymentMethod,
    onMethodSelected: (PaymentMethod) -> Unit
) {
    Column {

        Text(
            stringResource(R.string.c_mo_quer_s_pagar),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_16))
        )
        Row {

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMethod == PaymentMethod.CASH,
                    onClick = { onMethodSelected(PaymentMethod.CASH) }
                )
                Text(stringResource(R.string.efectivo))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMethod == PaymentMethod.CARD,
                    onClick = { onMethodSelected(PaymentMethod.CARD) }
                )
                Text(stringResource(R.string.tarjeta))
            }
        }
    }
}