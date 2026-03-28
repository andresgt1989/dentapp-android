package com.dentapp.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.R
import com.dentapp.app.ui.components.DentButton
import com.dentapp.app.ui.components.DentTextField
import com.dentapp.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) onLoginSuccess(state.role ?: "patient")
    }

    Box(modifier = Modifier.fillMaxSize().background(DentGray)) {

        // Cabecera azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(DentBlue, DentBlueDark),
                        start  = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end    = androidx.compose.ui.geometry.Offset(1000f, 600f),
                    )
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = "Logo DentApp",
                    modifier = Modifier.size(80.dp),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "DentApp",
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                )
                Text(
                    "Bienvenido de vuelta",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }

        // Tarjeta del formulario — superpuesta sobre la cabecera
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 220.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = DentGray,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        "Iniciar sesión",
                        style = MaterialTheme.typography.headlineMedium,
                        color = DentTextPrimary,
                    )

                    Spacer(Modifier.height(4.dp))

                    DentTextField(
                        value = email,
                        onValueChange = { email = it; viewModel.clearError() },
                        label = "Correo electrónico",
                        keyboardType = KeyboardType.Email,
                        leadingIcon = { Icon(Icons.Outlined.Email, null, tint = DentBlue) },
                    )

                    DentTextField(
                        value = password,
                        onValueChange = { password = it; viewModel.clearError() },
                        label = "Contraseña",
                        isPassword = true,
                        imeAction = ImeAction.Done,
                        leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = DentBlue) },
                    )

                    // Error
                    if (!state.error.isNullOrBlank()) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = ErrorRed.copy(alpha = 0.1f),
                        ) {
                            Text(
                                state.error!!,
                                color = ErrorRed,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            )
                        }
                    }

                    DentButton(
                        text = "Entrar",
                        onClick = { viewModel.login(email, password) },
                        isLoading = state.isLoading,
                        enabled = email.isNotBlank() && password.isNotBlank(),
                    )

                    // Divider
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp),
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = DentBlueLight)
                        Text(
                            "  o  ",
                            color = DentTextSecond,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Divider(modifier = Modifier.weight(1f), color = DentBlueLight)
                    }

                    // Registro
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = DentTextSecond)) {
                                    append("¿No tienes cuenta? ")
                                }
                                withStyle(SpanStyle(color = DentBlue, fontWeight = FontWeight.Bold)) {
                                    append("Regístrate gratis")
                                }
                            },
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}
