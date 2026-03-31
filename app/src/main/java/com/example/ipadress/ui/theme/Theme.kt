package com.example.ipadress.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DadLightColorScheme = lightColorScheme(
    primary = Ink,
    onPrimary = Color.White,
    secondary = Accent,
    onSecondary = Color.White,
    background = Sand,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = Color(0xFFF4EEDF),
    onSurfaceVariant = InkVariant,
    outline = Line,
    outlineVariant = Line,
)

private val DadDarkColorScheme = darkColorScheme(
    primary = AccentSoft,
    onPrimary = Ink,
    secondary = AccentSoft,
    onSecondary = Ink,
    background = Color(0xFF0E1624),
    onBackground = Color(0xFFF6F0E4),
    surface = Color(0xFF142033),
    onSurface = Color(0xFFF6F0E4),
    surfaceVariant = Color(0xFF1C2A41),
    onSurfaceVariant = Color(0xFFD5CCBD),
    outline = Color(0xFF41526D),
    outlineVariant = Color(0xFF41526D),
)

@Composable
fun IpadressTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DadDarkColorScheme else DadLightColorScheme,
        content = content
    )
}
