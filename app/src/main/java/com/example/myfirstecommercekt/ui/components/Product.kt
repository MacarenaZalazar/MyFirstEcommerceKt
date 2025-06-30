package com.example.myfirstecommercekt.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.myfirstecommercekt.data.local.entity.*

@Composable
fun Product(
    modifier: Modifier = Modifier,
    item: ProductEntity,
    addToCart: (item: ProductEntity) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(item.description, fontSize = 8.sp)
                Spacer(modifier = Modifier.padding(16.dp))
                Text("$${item.price}", fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier.height(100.dp)) {
                LoadImage(
                    url = "https://recetasdecocina.elmundo.es/wp-content/uploads/2024/04/noquis-con-tomate.jpg",
                    contentDescription = "Imagen",
                    Modifier.fillMaxHeight()
                )

                IconButton(
                    onClick = { addToCart(item) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "agregar")
                }
            }
        }
    }
}