package com.example.myfirstecommercekt.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import coil.compose.*

@Composable
fun LoadImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,

    ) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop

    )
}