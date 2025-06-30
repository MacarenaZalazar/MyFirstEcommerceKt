package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.viewmodel.*

@Composable
fun CartScreen(viewModel: CartViewModel, toProducts: () -> Unit) {

    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

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
    val items by viewModel.cartItems.collectAsState()
    if (items.isEmpty()) {
        Column() {
            Text("Tu carrito se encuentra vacío!")
            Text("Sumá productos y comenzá tu compra ")
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "carrito vacío"
            )
            Button(onClick = toProducts) {
                Text("Buscar productos")
            }
        }


    } else {
        LazyColumn() { }
    }
}