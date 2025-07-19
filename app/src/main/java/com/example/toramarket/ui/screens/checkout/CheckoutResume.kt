package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*

@Composable
fun CheckoutResume(order: Order) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text("Resumen del pedido", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.padding(8.dp))

            Column {
                order.items.forEach { it ->
                    ResumeItem(
                        quantity = it.quantity,
                        name = it.productName,
                        price = it.price
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Costo de servicio", fontWeight = FontWeight.Bold)
                Text("$50.00", fontWeight = FontWeight.Bold)
            }
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Costo de envío", fontWeight = FontWeight.Bold)
                Text("$100.00", fontWeight = FontWeight.Bold)
            }

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total:", fontWeight = FontWeight.Bold)
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