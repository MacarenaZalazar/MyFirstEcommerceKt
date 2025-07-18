package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.toramarket.ui.navigation.*


@Composable
fun EmptyCart(navController: NavController) {
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
                onClick = {
                    navController.navigate(ProductsScreenRoute) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ) {
                Text("Buscar productos")
            }
        }
    }
}