package com.dentapp.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.R
import com.dentapp.app.ui.components.*
import com.dentapp.app.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) onLoginSuccess(state.role ?: "patient")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DentGray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(64.dp))

            // Logo / ícono
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(DentBlue),
                contentAlignment = Alignment.Center,
            ) {
                Text("🦷", style = MaterialTheme.typography.headlineLarge)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Bienvenido a DentApp",
                style = MaterialTheme.typography.headlineMedium,
                color = DentTextPrimary,
            )
            Text(
                "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = DentTextSecond,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(Modifier.height(40.dp))

            Surface(
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 1.dp,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                    )

                    state.error?.let {
                        Text(it, color = ErrorRed, style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    }

                    DentButton(
                        text = "Iniciar sesión",
                        onClick = { viewModel.login(email, password) },
                        isLoading = state.isLoading,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes cuenta? ", color = DentTextSecond,
                    style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onNavigateToRegister) {
                    Text("Regístrate", color = DentBlue,
                        style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
