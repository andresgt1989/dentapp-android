package com.dentapp.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dentapp.app.ui.theme.GradientEnd
import com.dentapp.app.ui.theme.GradientStart
import com.dentapp.app.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val toothScale = remember { Animatable(0f) }
    val toothAlpha = remember { Animatable(0f) }
    val textAlpha  = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Tooth: scale 0→1 spring + fade in
        toothScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness    = Spring.StiffnessMediumLow,
            ),
        )
        toothAlpha.animateTo(1f, tween(200))
        // Text fades in 200ms after tooth
        delay(200)
        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(400, easing = FastOutSlowInEasing),
        )
        delay(900)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(GradientStart, GradientEnd),
                    start  = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end    = androidx.compose.ui.geometry.Offset(1000f, 1000f),
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Tooth icon — emoji as placeholder (replace with ic_logo vector when available)
            Text(
                text = "🦷",
                fontSize = 80.sp,
                modifier = Modifier
                    .scale(toothScale.value)
                    .alpha(toothAlpha.value),
            )

            Spacer(Modifier.height(28.dp))

            Text(
                text = "DentApp",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1).sp,
                modifier = Modifier.alpha(textAlpha.value),
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Tu salud dental, inteligente",
                color = White.copy(alpha = 0.70f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.alpha(textAlpha.value),
            )
        }

        Text(
            text = "v1.0.0",
            color = White.copy(alpha = 0.35f),
            fontSize = 11.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(textAlpha.value),
        )
    }
}
