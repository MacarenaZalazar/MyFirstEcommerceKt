package com.example.toramarket.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AlertPopup(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = onDismiss,
    confirmText: String = "Aceptar"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        }
    )
}