package com.dentapp.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dentapp.app.R
import com.dentapp.app.ui.theme.DentBlue
import com.dentapp.app.ui.theme.DentBlueDark
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dentapp.app.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    // Animación de escala: 0.6 → 1.0
    val scale = remember { Animatable(0.6f) }
    // Animación de opacidad: 0 → 1
    val alpha = remember { Animatable(0f) }
    // Animación del texto: sube desde abajo
    val textOffset = remember { Animatable(40f) }
    val textAlpha  = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo aparece con bounce
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness    = Spring.StiffnessMedium,
            )
        )
        alpha.animateTo(1f, tween(400))

        // Texto sube
        textOffset.animateTo(0f, tween(400, easing = FastOutSlowInEasing))
        textAlpha.animateTo(1f, tween(400))

        delay(1000)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(DentBlue, DentBlueDark),
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
            // Logo
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "DentApp logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale.value)
                    .alpha(alpha.value),
            )

            Spacer(Modifier.height(24.dp))

            // Nombre app
            Text(
                text = "DentApp",
                color = White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .graphicsLayer {
                        translationY = textOffset.value
                        this.alpha   = textAlpha.value
                    },
            )

            Text(
                text = "Tu clínica dental, siempre contigo",
                color = White.copy(alpha = 0.75f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .graphicsLayer {
                        translationY = textOffset.value
                        this.alpha   = textAlpha.value
                    },
            )
        }

        // Versión abajo
        Text(
            text = "v1.0.0",
            color = White.copy(alpha = 0.4f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )
    }
}
