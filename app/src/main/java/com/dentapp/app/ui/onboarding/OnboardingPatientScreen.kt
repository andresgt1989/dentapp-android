package com.dentapp.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.auth.AuthViewModel
import com.dentapp.app.ui.components.*
import com.dentapp.app.ui.theme.*

@Composable
fun OnboardingPatientScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var step by remember { mutableIntStateOf(0) }

    // Campos del formulario
    var fullName    by remember { mutableStateOf("") }
    var email       by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }
    var phone       by remember { mutableStateOf("") }
    var dob         by remember { mutableStateOf("") }  // YYYY-MM-DD

    LaunchedEffect(state.success) {
        if (state.success) onSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DentGray)
            .padding(24.dp),
    ) {
        // Header con back y step indicator
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (step > 0) step-- else onBack() }) {
                Icon(Icons.Outlined.ArrowBack, "Atrás", tint = DentBlue)
            }
            StepIndicator(
                currentStep = step,
                totalSteps = 3,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "Paso ${step + 1} de 3",
            style = MaterialTheme.typography.labelSmall,
            color = DentTextSecond,
        )

        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > initialState)
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                else
                    slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            },
            label = "step_anim",
        ) { currentStep ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (currentStep) {
                    // ── Paso 1: Nombre ────────────────────────────────────────
                    0 -> {
                        StepTitle("¿Cómo te llamas?", "Ingresa tu nombre completo")
                        DentTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = "Nombre completo",
                            leadingIcon = { Icon(Icons.Outlined.Person, null, tint = DentBlue) },
                        )
                    }
                    // ── Paso 2: Contacto ──────────────────────────────────────
                    1 -> {
                        StepTitle("Datos de contacto", "Usaremos esto para enviarte recordatorios")
                        DentTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = "Teléfono",
                            keyboardType = KeyboardType.Phone,
                            leadingIcon = { Icon(Icons.Outlined.Phone, null, tint = DentBlue) },
                        )
                        DentTextField(
                            value = dob,
                            onValueChange = { dob = it },
                            label = "Fecha de nacimiento (YYYY-MM-DD)",
                            leadingIcon = { Icon(Icons.Outlined.CalendarMonth, null, tint = DentBlue) },
                        )
                    }
                    // ── Paso 3: Cuenta ────────────────────────────────────────
                    2 -> {
                        StepTitle("Crea tu cuenta", "Email y contraseña para ingresar")
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
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (step < 2) {
            DentButton(
                text = "Continuar",
                onClick = { step++ },
                enabled = when (step) {
                    0 -> fullName.isNotBlank()
                    1 -> phone.isNotBlank()
                    else -> true
                },
            )
        } else {
            DentButton(
                text = "Crear mi cuenta",
                onClick = {
                    viewModel.registerPatient(email, password, fullName, phone, dob)
                },
                isLoading = state.isLoading,
                enabled = email.isNotBlank() && password.length >= 6,
            )
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun StepTitle(title: String, subtitle: String) {
    Column {
        Text(title, style = MaterialTheme.typography.headlineMedium, color = DentTextPrimary)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium,
            color = DentTextSecond, modifier = Modifier.padding(top = 4.dp))
    }
}
