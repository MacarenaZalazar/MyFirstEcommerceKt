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
import com.example.toramarket.data.local.entity.*
import com.example.toramarket.ui.navigation.*

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
            }, bottomBar = {
                BottomAppBar(tonalElevation = 4.dp) {
                    Column(modifier = Modifier.padding(20.dp)) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//
//                            Text("Cantidad de items")
//                            Text(count.toString())
//                        }
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//
//                            Text("Subtotal")
//                            Text("$${subtotal}")
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate(CheckoutScreenRoute) },
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Ir a pagar")
                        }
                    }
                }

            }
        ) { it ->
            Column(Modifier.padding(it)) {
                Cart(cartItems, viewModel)
                CartResume(count, subtotal, navController)
            }
        }
    }

}

@Composable
fun Cart(
    cartItems: List<CartItemWithProduct>,
    viewModel: CartViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
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
    }
}


@Composable
fun CartResume(count: Int, subtotal: Double, navController: NavController) {
    Column(modifier = Modifier.padding(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Cantidad de items")
            Text(count.toString())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal")
            Text("$${subtotal}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(CheckoutScreenRoute) },
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Text("Ir a pagar")
        }
    }
}
