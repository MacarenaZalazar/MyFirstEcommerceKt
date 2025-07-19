package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navController: NavController
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val subtotal by viewModel.subtotal.collectAsState()
    val count by viewModel.count.collectAsState()

    if (cartItems.isEmpty()) {
        EmptyCart(navController)
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth(), topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.shadow(4.dp),
                    title = { Text("Tus productos") },
                    actions = {
                        Button(
                            onClick = { viewModel.clearCart() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = Color.White
                            ),
                        ) {
                            Text("Vaciar")
                        }
                    }
                )
            }

        ) { it ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems) { it ->
                        CartItem(
                            item = it,
                            addToCart = { viewModel.addToCart(it.id) },
                            removeFromCart = { viewModel.removeFromCart(it.id) })
                    }
                }
                CartSummary(count, subtotal, navController)
            }
        }
    }
}

