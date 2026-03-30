package com.dentapp.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dentapp.app.ui.theme.OnPrimary
import com.dentapp.app.ui.theme.Primary
import com.dentapp.app.ui.theme.PrimaryDark
import kotlinx.coroutines.delay

/**
 * Animated splash screen shown at startup.
 * [onFinished] receives [hasToken] — callers should check DataStore
 * to determine whether a valid JWT exists before invoking this lambda.
 */
@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val scale       = remember { Animatable(0.6f) }
    val alpha       = remember { Animatable(0f) }
    val textOffset  = remember { Animatable(40f) }
    val textAlpha   = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness    = Spring.StiffnessMedium,
            )
        )
        alpha.animateTo(1f, tween(400))
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
                    colors = listOf(Primary, PrimaryDark),
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
            // Logo emoji — replace with Image(painterResource(R.drawable.ic_logo)) when asset is ready
            Text(
                text = "\uD83E\uDDB7",   // tooth emoji
                fontSize = 80.sp,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value),
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "DentApp",
                color = OnPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.graphicsLayer {
                    translationY = textOffset.value
                    this.alpha   = textAlpha.value
                },
            )

            Text(
                text = "Tu salud dental, siempre bajo control",
                color = OnPrimary.copy(alpha = 0.75f),
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

        Text(
            text = "v1.0.0",
            color = OnPrimary.copy(alpha = 0.4f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )
    }
}
