package com.example.toramarket.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.example.toramarket.R

@Composable
fun AppTitle() {
    val isDark = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (isDark) {
                painterResource(id = R.drawable.toramarket_logo_dark)

            } else {
                painterResource(id = R.drawable.toramarket_logo)
            },
            contentDescription = "Logo de Tora",
            modifier = Modifier
                .size(300.dp),
            contentScale = ContentScale.Fit
        )
    }
}