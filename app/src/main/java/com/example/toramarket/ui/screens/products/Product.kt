package com.example.toramarket.ui.screens.products

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.utils.data.*

@Composable
fun Product(
    modifier: Modifier = Modifier, item: Product, addToCart: (item: Product) -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                Modifier
                    .weight(0.5f)
                    .padding(16.dp), verticalArrangement = Arrangement.Center
            ) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(item.description)
                Spacer(modifier = Modifier.padding(4.dp))
                Text("$${item.price}", fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                LoadImage(
                    url = item.image,
                    contentDescription = "Imagen",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(16.dp))
                )

                IconButton(
                    onClick = { addToCart(item) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "agregar")
                }
            }
        }
    }
}