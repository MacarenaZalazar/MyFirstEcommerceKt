package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.utils.data.*

@Composable
fun Resume(order: Order, goBack: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = { goBack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Volver"
            )
        }
        Text("Tu pedido", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.padding(8.dp))
        LazyColumn {
            items(order.items) { it ->
                ResumeItem(
                    quantity = it.quantity,
                    name = it.productName,
                    price = it.price
                )
            }
        }
        Spacer(Modifier.padding(8.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total:", fontWeight = FontWeight.Bold)
            Text("$${String.format("%.2f", order.total)}", fontWeight = FontWeight.Bold)
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