package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.viewmodel.*

@Composable
fun CartScreen(viewModel: CartViewModel, toProducts: () -> Unit) {

    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Cart(viewModel, toProducts)
        }
    }
}

@Composable
fun Cart(viewModel: CartViewModel = hiltViewModel(), toProducts: () -> Unit) {
    val cartItems by viewModel.cartItems.collectAsState()
    val subtotal by viewModel.subtotal.collectAsState()
    val count by viewModel.count.collectAsState()

    if (cartItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.align(alignment = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Tu carrito se encuentra vacío!", fontSize = 25.sp)
                Spacer(modifier = Modifier.padding(16.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.LightGray
                        )
                        .padding(16.dp)
                ) {

                    Icon(
                        imageVector = Icons.Sharp.ShoppingCart,
                        contentDescription = "carrito vacío",
                        tint = Color.White,
                        modifier = Modifier.size(100.dp),
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Sumá productos y comenzá tu compra.")
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    onClick = toProducts,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                ) {
                    Text("Buscar productos")
                }
            }
        }


    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { it ->
                    CartItem(
                        item = it,
                        addToCart = { viewModel.addToCart(it) },
                        removeFromCart = { viewModel.removeFromCart(it) })
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tus productos")
                    Button(
                        onClick = { viewModel.clearCart() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Vaciar carrito")
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
                    .align(Alignment.BottomCenter)
            ) {

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
                        onClick = {}, modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Comprar")
                    }
                }
            }
        }
    }
}