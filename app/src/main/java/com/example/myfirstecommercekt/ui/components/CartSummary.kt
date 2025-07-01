package com.example.myfirstecommercekt.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*

@Composable
fun CartSummaryCard(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Cantidad de items")
                Text("4")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Subtotal")
                Text("$10.000")
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

@Preview
@Composable
fun CartPreview() {
    CartSummaryCard(Modifier)
}