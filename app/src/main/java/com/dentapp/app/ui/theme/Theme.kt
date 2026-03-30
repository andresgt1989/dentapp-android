package com.dentapp.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

// Colors are defined in Color.kt

private val DentColorScheme = lightColorScheme(
    primary          = Primary,
    onPrimary        = OnPrimary,
    primaryContainer = PrimaryLight,
    secondary        = Secondary,
    onSecondary      = OnPrimary,
    secondaryContainer = SecondaryLight,
    background       = Background,
    onBackground     = OnBackground,
    surface          = Surface,
    onSurface        = OnBackground,
    error            = Error,
)

@Composable
fun DentAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DentColorScheme,
        typography  = DentTypography,
        content     = content,
    )
}
