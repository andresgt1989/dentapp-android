package com.dentapp.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dentapp.app.ui.theme.*

// ── Gradient brush helper ─────────────────────────────────────────────────────

val PrimaryGradient: Brush
    @Composable get() = Brush.linearGradient(listOf(GradientStart, GradientEnd))

// ── GradientCard ──────────────────────────────────────────────────────────────

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    gradient: Brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
    cornerRadius: Dp = 20.dp,
    padding: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(gradient)
            .padding(padding),
    ) {
        Column(content = content)
    }
}

// ── ElevatedCard (premium soft shadow style) ──────────────────────────────────

@Composable
fun PremiumCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(cornerRadius)
    if (onClick != null) {
        Card(
            onClick = onClick,
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceGray),
            modifier = modifier,
        ) {
            Column(content = content)
        }
    } else {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceGray),
            modifier = modifier,
        ) {
            Column(content = content)
        }
    }
}

// ── IconBadge ─────────────────────────────────────────────────────────────────

@Composable
fun IconBadge(
    icon: ImageVector,
    tint: Color = TealPrimary,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    cornerRadius: Dp = 14.dp,
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .clip(RoundedCornerShape(cornerRadius))
            .background(tint.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize),
        )
    }
}

// ── PrimaryButton ─────────────────────────────────────────────────────────────

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "buttonScale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(100),
        label = "buttonAlpha",
    )

    Box(
        modifier = modifier
            .scale(scale)
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = if (enabled && !isLoading)
                    Brush.linearGradient(listOf(GradientStart, GradientEnd))
                else
                    Brush.linearGradient(listOf(TextTertiary, TextTertiary)),
                alpha = alpha,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onClick,
            enabled = enabled && !isLoading,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp),
                )
            } else {
                Text(
                    text = text,
                    color = White,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

// ── DentTextField (updated) ───────────────────────────────────────────────────

@Composable
fun DentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = isError,
            leadingIcon = leadingIcon,
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
                imeAction = imeAction,
            ),
            trailingIcon = if (isPassword) {{
                TextButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(
                        if (passwordVisible) "Ocultar" else "Ver",
                        style = MaterialTheme.typography.labelSmall,
                        color = TealPrimary,
                    )
                }
            }} else null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = TealPrimary,
                focusedLabelColor    = TealPrimary,
                unfocusedBorderColor = SurfaceGray,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        if (isError && !errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = AlertRed,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp),
            )
        }
    }
}

// ── DentButton (updated alias → PrimaryButton) ────────────────────────────────

@Composable
fun DentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    PrimaryButton(
        text = text,
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        isLoading = isLoading,
    )
}

// ── DentOutlinedButton ────────────────────────────────────────────────────────

@Composable
fun DentOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, TealPrimary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary),
        modifier = modifier.fillMaxWidth().height(56.dp),
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}

// ── StepIndicator ─────────────────────────────────────────────────────────────

@Composable
fun StepIndicator(currentStep: Int, totalSteps: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(totalSteps) { index ->
            val active = index == currentStep
            val done   = index < currentStep
            Surface(
                shape = RoundedCornerShape(50),
                color = when {
                    active || done -> TealPrimary
                    else           -> TealLight
                },
                modifier = Modifier.weight(1f).height(4.dp),
            ) {}
        }
    }
}
