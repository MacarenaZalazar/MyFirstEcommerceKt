package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.R
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*

@Composable
fun Order(order: Order, viewModel: OrdersViewModel) {
    val count = totalCount(order.items)
    Card {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_16))
        ) {
            Text(stringResource(R.string.entregado, formatDate(order.created)))
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_6)))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    stringResource(R.string.pedido, order.id.takeLast(6)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    "$${"%.2f".format(order.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_6)))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string._productos, count))
                Button(onClick = { viewModel.openOrderDetail(order) }) { Text(stringResource(R.string.ver_pedido)) }
            }

        }
    }
}