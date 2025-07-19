package com.example.toramarket.ui.theme

import android.os.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*

// Tema claro
private val LightPrimary = Color(0xFF8762C5)
private val LightPrimaryVariant = Color(0xFF5E429A)
private val LightSecondary = Color(0xFFB596DF)
private val LightBackground = Color.White
private val LightSurface = Color(0xFFF9F2FE)
private val LightOnPrimary = Color.White
private val LightOnSecondary = Color.Black
private val LightOnBackground = Color.Black
private val LightOnSurface = Color.Black

// Tema oscuro
private val DarkPrimary = Color(0xFF8861C5)
private val DarkPrimaryVariant = Color(0xFF513A7F)
private val DarkSecondary = Color(0xFF9F7DD1)
private val DarkBackground = Color(0xFF281C4B)
private val DarkSurface = Color(0xFF6C4BA9)
private val DarkOnPrimary = Color.Black
private val DarkOnSecondary = Color.Black
private val DarkOnBackground = Color.White
private val DarkOnSurface = Color.White

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryVariant,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryVariant,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
)

@Composable
fun ToraMarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}