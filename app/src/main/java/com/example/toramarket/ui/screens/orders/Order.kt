package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*

@Composable
fun Order(order: Order) {
    val count = totalCount(order.items)
    Card {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Entregado: ${formatDate(order.created)}")
            Spacer(Modifier.padding(6.dp))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    "Pedido: #${order.id.takeLast(6)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text("$${order.total}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(Modifier.padding(6.dp))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$count productos")
                Button(onClick = {}) { Text("Ver pedido") }
            }

        }
    }
}