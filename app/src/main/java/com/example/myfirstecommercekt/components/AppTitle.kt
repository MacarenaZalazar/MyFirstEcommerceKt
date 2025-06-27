package com.example.myfirstecommercekt.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTitle() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            "ComposeMarket!",
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        Spacer(Modifier.padding(16.dp))
        Icon(
            imageVector = Icons.Outlined.ShoppingCart, contentDescription = "ShoppingCart",
            Modifier.size(100.dp)
        )
    }
}