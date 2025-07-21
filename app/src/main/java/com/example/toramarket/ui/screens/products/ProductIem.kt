package com.example.toramarket.ui.screens.products

import android.graphics.*
import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.utils.data.*

@Composable
fun ProductItem(
    modifier: Modifier = Modifier, item: Product, viewModel: ProductsViewModel
) {
    val isLoading = viewModel.isItemLoading(item.id)

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
                Text("$${"%.2f".format(item.price)}", fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp), contentAlignment = Alignment.Center
            ) {
                if (item.image.startsWith("data:image")) {
                    LoadImage(
                        image = item.image,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(16.dp)),
                    )
                } else {
                    LoadImage(
                        url = item.image,
                        contentDescription = "Imagen",
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                AddToCartButton(
                    isLoading,
                    modifier.align(Alignment.BottomEnd)
                ) { viewModel.addToCart(item.id) }

            }
        }
    }
}

@Composable
fun AddToCartButton(
    isLoading: Boolean, modifier: Modifier, onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .shadow(4.dp, CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .clickable(enabled = !isLoading, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp), color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "agregar",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun LoadImage(
    image: String, modifier: Modifier

) {
    val base64Data = image.substringAfter("base64,")
    val imageBytes = Base64.decode(base64Data, Base64.DEFAULT)
    val bitmap = remember(image) {
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}

