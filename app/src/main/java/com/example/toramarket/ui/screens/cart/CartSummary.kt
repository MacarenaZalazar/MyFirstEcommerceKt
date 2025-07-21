package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.navigation.*

@Composable
fun CartSummary(count: Int, subtotal: Double, navController: NavController) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(20.dp)


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(stringResource(R.string.cantidad_de_items))
            Text(count.toString())
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal")
            Text("$${"%.2f".format(subtotal)}")
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16)))
        Button(
            onClick = { navController.navigate(CheckoutScreenRoute) },
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_10))
                .fillMaxWidth()
        ) {
            Text(stringResource(R.string.ir_a_pagar))
        }
    }
}
