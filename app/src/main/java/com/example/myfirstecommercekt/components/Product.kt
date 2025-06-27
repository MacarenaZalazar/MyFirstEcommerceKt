package com.example.myfirstecommercekt.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Product() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                Text("Nombre Producto", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Descripción del producto", fontSize = 8.sp)
                Spacer(modifier = Modifier.padding(16.dp))
                Text("$10.000", fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier.height(100.dp)) {
                LoadImage(
                    url = "https://recetasdecocina.elmundo.es/wp-content/uploads/2024/04/noquis-con-tomate.jpg",
                    contentDescription = "Imagen",
                    Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
                IconButton(onClick = {}, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "fav")
                }
                IconButton(onClick = {}, modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "fav")

                }
            }
        }
    }
}

@Preview
@Composable
fun ProductPreview() {
    Product()
}

