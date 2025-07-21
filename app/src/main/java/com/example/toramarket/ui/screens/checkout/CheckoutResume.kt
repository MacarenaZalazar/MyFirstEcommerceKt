package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import com.example.toramarket.R
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*

@Composable
fun CheckoutResume(order: Order) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_16))
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_16))

        ) {
            Text(
                stringResource(R.string.resumen_del_pedido),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_8)))

            Column {
                order.items.forEach { it ->
                    ResumeItem(
                        quantity = it.quantity,
                        name = it.productName,
                        price = it.price
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_8)))

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.costo_de_servicio), fontWeight = FontWeight.Bold)
                Text(stringResource(R.string._50_00), fontWeight = FontWeight.Bold)
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.costo_de_env_o), fontWeight = FontWeight.Bold)
                Text(stringResource(R.string._100_00), fontWeight = FontWeight.Bold)
            }

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.total), fontWeight = FontWeight.Bold)
                Text("$${"%.2f".format(totalPrice(order.total))}", fontWeight = FontWeight.Bold)
            }
        }
    }

}

@Composable
fun ResumeItem(quantity: Int, name: String, price: Double) {
    val total = price * quantity
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("$quantity - $name")
        Text("$${"%.2f".format(total)}")
    }
}