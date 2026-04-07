package com.dentapp.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DentTypography = Typography(
    // Display — large hero titles
    displayLarge = TextStyle(
        fontWeight   = FontWeight.Bold,
        fontSize     = 48.sp,
        lineHeight   = 56.sp,
        letterSpacing = (-1).sp,
    ),
    displayMedium = TextStyle(
        fontWeight   = FontWeight.Bold,
        fontSize     = 36.sp,
        lineHeight   = 44.sp,
        letterSpacing = (-0.5).sp,
    ),
    displaySmall = TextStyle(
        fontWeight   = FontWeight.Bold,
        fontSize     = 28.sp,
        lineHeight   = 36.sp,
        letterSpacing = (-0.5).sp,
    ),
    // Headline
    headlineLarge = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 26.sp,
        lineHeight   = 34.sp,
        letterSpacing = (-0.3).sp,
    ),
    headlineMedium = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 22.sp,
        lineHeight   = 30.sp,
        letterSpacing = (-0.3).sp,
    ),
    headlineSmall = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 18.sp,
        lineHeight   = 26.sp,
        letterSpacing = (-0.2).sp,
    ),
    // Title
    titleLarge = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 18.sp,
        lineHeight   = 26.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleMedium = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 16.sp,
        lineHeight   = 24.sp,
        letterSpacing = (-0.1).sp,
    ),
    titleSmall = TextStyle(
        fontWeight   = FontWeight.SemiBold,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.sp,
    ),
    // Body
    bodyLarge = TextStyle(
        fontWeight   = FontWeight.Normal,
        fontSize     = 16.sp,
        lineHeight   = 24.sp,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight   = FontWeight.Normal,
        fontSize     = 14.sp,
        lineHeight   = 22.sp,
        letterSpacing = 0.sp,
    ),
    bodySmall = TextStyle(
        fontWeight   = FontWeight.Normal,
        fontSize     = 12.sp,
        lineHeight   = 18.sp,
        letterSpacing = 0.sp,
    ),
    // Label
    labelLarge = TextStyle(
        fontWeight   = FontWeight.Medium,
        fontSize     = 14.sp,
        lineHeight   = 20.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontWeight   = FontWeight.Medium,
        fontSize     = 12.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight   = FontWeight.Medium,
        fontSize     = 11.sp,
        lineHeight   = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)
