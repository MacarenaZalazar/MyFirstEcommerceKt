package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myfirstecommercekt.viewmodel.ShoppingCartViewModel

@Composable
fun ShoppingCartScreen(viewModel: ShoppingCartViewModel, toProducts: ()-> Unit) {
    val items by viewModel.cartIems.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (items.size < 1) {
            //carrito vacío
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
            //carrito con productos
            LazyColumn() {  }
        }
    }
}