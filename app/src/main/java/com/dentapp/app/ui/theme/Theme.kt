package com.dentapp.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta DentApp — azul dental profesional
val DentBlue        = Color(0xFF1A6B8A)
val DentBlueDark    = Color(0xFF0D4F6B)
val DentBlueLight   = Color(0xFFE3F4FA)
val DentGreen       = Color(0xFF2ECC71)
val DentGreenDark   = Color(0xFF27AE60)
val DentGray        = Color(0xFFF5F7FA)
val DentTextPrimary = Color(0xFF1C2B36)
val DentTextSecond  = Color(0xFF6B8A99)
val White           = Color(0xFFFFFFFF)
val ErrorRed        = Color(0xFFE74C3C)

private val DentColorScheme = lightColorScheme(
    primary          = DentBlue,
    onPrimary        = White,
    primaryContainer = DentBlueLight,
    secondary        = DentGreen,
    onSecondary      = White,
    background       = DentGray,
    onBackground     = DentTextPrimary,
    surface          = White,
    onSurface        = DentTextPrimary,
    error            = ErrorRed,
)

@Composable
fun DentAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DentColorScheme,
        typography  = DentTypography,
        content     = content,
    )
}
