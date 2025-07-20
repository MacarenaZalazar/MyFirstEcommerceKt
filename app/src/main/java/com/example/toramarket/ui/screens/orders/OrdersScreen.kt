package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel()
) {
    val state = ordersViewModel.uiState
    LaunchedEffect(Unit) {
        ordersViewModel.loadOrders()
    }


    when (state) {
        is UIState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {

                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        }

        is UIState.Success -> {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Mis pedidos") },
                        actions = {
                            IconButton(onClick = { ordersViewModel.loadOrders() }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->

                val orders = state.data
                if (orders.isEmpty()) {
                    Box(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {

                        Text("No se encontraron pedidos.")
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .padding(it)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(orders) { it ->
                            Order(it)
                        }
                    }

                }
            }

        }

        is UIState.Error ->
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Mis pedidos") },
                        actions = {
                            IconButton(onClick = { ordersViewModel.loadOrders() }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(it)

                ) {
                    Text(state.message, Modifier.align(Alignment.Center))
                }
            }


    }
}
