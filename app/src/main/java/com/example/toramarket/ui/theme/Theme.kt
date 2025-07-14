package com.example.toramarket.ui.theme

import android.os.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
private val White = Color(0xFFFFFFFF)
private val DarkGray = Color(0xFF1C1B1F)
private val SurfaceDark = Color(0xFF2A2930)
private val OnDark = Color(0xFFA5F8D3)
private val OnLight = Color(0xFF3C3A45)

private val Mauveine = Color(0xFF802392)
private val Purpureus = Color(0xFF995FA3)
private val CoolGray = Color(0xFF9A98B5)
private val Aquamarine = Color(0xFFA5F8D3)

private val LightColorScheme = lightColorScheme(
    primary = Mauveine,
    onPrimary = White,
    secondary = Purpureus,
    onSecondary = White,
    background = White,
    onBackground = Mauveine,
    surface = White,
    onSurface = OnLight,
    error = Color(0xFFD32F2F),
    onError = White,
)

private val DarkColorScheme = darkColorScheme(
    primary = Mauveine,
    onPrimary = CoolGray,
    secondary = Purpureus,
    onSecondary = White,
    background = DarkGray,
    onBackground = Aquamarine,
    surface = SurfaceDark,
    onSurface = OnDark,
    error = Color(0xFFEF9A9A),
    onError = DarkGray,
)

@Composable
fun MyFirstEcommerceKtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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