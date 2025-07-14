package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*

@Composable
fun Order(order: Order) {
    val count = totalAmount(order.items)
    val total = totalCount(order.items)

    Row {

        Column() {
            Row {
                Text("Entregado")
                Text(order.created.toString())
            }
            Text("Cantidad de productos: $count")
        }
        Column {

            Text("$total")
            Button(onClick = {}) { Text("Ver mi pedido") }
        }

    }
}