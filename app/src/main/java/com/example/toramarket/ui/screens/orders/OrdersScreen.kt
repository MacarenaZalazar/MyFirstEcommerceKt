package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.*

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val state = ordersViewModel.uiState
    LaunchedEffect(Unit) {
        ordersViewModel.loadOrders()
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp), contentAlignment = Alignment.Center
    ) {

        when (state) {
            is UIState.Loading -> {

                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }

            is UIState.Success -> {
                val orders = state.data
                LazyColumn {
                    items(orders) { it ->
                        Order(it)
                    }
                }
            }

            is UIState.Error -> Text("No se encontraron pedidos")
        }
    }
}