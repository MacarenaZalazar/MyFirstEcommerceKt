package com.example.toramarket.ui.components

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*

@Composable
fun SimpleText(
    value: String,
    onChange: (String) -> Unit,
    label: String = "",
    enabled: Boolean = true,
    placeholder: String = "",
    error: (String) -> String?,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var wasFocused by rememberSaveable { mutableStateOf(false) }
    val showError = wasFocused && !isFocused
    val errorMessage = if (showError) error(value) else null

    LaunchedEffect(isFocused) {
        if (isFocused) {
            wasFocused = true
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        enabled = enabled,
        isError = errorMessage != null,
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        interactionSource = interactionSource,
        supportingText = {
            if (errorMessage != null) Text(
                text = errorMessage, color = MaterialTheme.colorScheme.error
            )
        },
        trailingIcon = trailingIcon
    )
}

@Composable
fun EmailField(
    email: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    error: (String) -> String?
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var wasFocused by rememberSaveable { mutableStateOf(false) }
    val showError = wasFocused && !isFocused
    val errorMessage = if (showError) error(email) else null

    LaunchedEffect(isFocused) {
        if (isFocused) {
            wasFocused = true
        }
    }

    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        interactionSource = interactionSource,
        enabled = enabled,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null && enabled) Text(
                text = errorMessage, color = MaterialTheme.colorScheme.error
            )
        })
}

@Composable
fun PasswordField(
    password: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    label: String = "Contraseña",
    error: (String) -> String?,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    var wasFocused by rememberSaveable { mutableStateOf(false) }
    val showError = wasFocused && !isFocused
    val errorMessage = if (showError) error(password) else null

    LaunchedEffect(isFocused) {
        if (isFocused) {
            wasFocused = true
        }
    }

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        enabled = enabled,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null && enabled) Text(
                text = errorMessage, color = MaterialTheme.colorScheme.error
            )
        },
        trailingIcon = trailingIcon
    )
}