package com.dentapp.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DentColorScheme = lightColorScheme(
    primary             = TealPrimary,
    onPrimary           = OnPrimary,
    primaryContainer    = TealLight,
    onPrimaryContainer  = TealDark,
    secondary           = GradientEnd,
    onSecondary         = OnPrimary,
    secondaryContainer  = SecondaryLight,
    background          = Background,
    onBackground        = TextPrimary,
    surface             = SurfaceWhite,
    onSurface           = TextPrimary,
    surfaceVariant      = SurfaceGray,
    error               = AlertRed,
    onError             = OnPrimary,
    errorContainer      = AlertRedLight,
)

@Composable
fun DentAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DentColorScheme,
        typography  = DentTypography,
        content     = content,
    )
}
