package com.example.toramarket.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*

@Composable
fun AppTitle() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "ComposeMarket", textAlign = TextAlign.Center, fontSize = 30.sp
        )
        Spacer(Modifier.padding(16.dp))
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "ShoppingCart",
            Modifier.size(100.dp)
        )
    }
}