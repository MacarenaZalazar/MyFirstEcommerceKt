package com.example.myfirstecommercekt.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*

@Composable
fun SimpleText(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    error: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        enabled = enabled,
        isError = error
    )
}

@Composable
fun EmailField(
    email: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    error: Boolean
) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        enabled = enabled,
        isError = error
    )
}

@Composable
fun PasswordField(
    password: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    label: String = "Contraseña", error: Boolean
) {
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        enabled = enabled,
        isError = error
    )
}