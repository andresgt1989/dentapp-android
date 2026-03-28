package com.dentapp.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

private val SPECIALTIES = listOf(
    "Odontología General", "Ortodoncia", "Endodoncia",
    "Periodoncia", "Odontopediatría", "Cirugía Oral",
    "Implantología", "Estética Dental",
)

@Composable
fun OnboardingDoctorScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var step by remember { mutableIntStateOf(0) }

    var fullName       by remember { mutableStateOf("") }
    var phone          by remember { mutableStateOf("") }
    var specialty      by remember { mutableStateOf(SPECIALTIES[0]) }
    var licenseNumber  by remember { mutableStateOf("") }
    var email          by remember { mutableStateOf("") }
    var password       by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) onSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DentGray)
            .padding(24.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (step > 0) step-- else onBack() }) {
                Icon(Icons.Outlined.ArrowBack, "Atrás", tint = DentBlue)
            }
            StepIndicator(
                currentStep = step,
                totalSteps = 5,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
            )
        }

        Spacer(Modifier.height(8.dp))

        Text("Paso ${step + 1} de 5",
            style = MaterialTheme.typography.labelSmall, color = DentTextSecond)

        Spacer(Modifier.height(24.dp))

        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > initialState)
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                else
                    slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            },
            label = "doctor_step_anim",
        ) { currentStep ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (currentStep) {
                    // ── Paso 1: Nombre ─────────────────────────────────────────
                    0 -> {
                        StepTitle("Datos personales", "Tu nombre como aparecerá a los pacientes")
                        DentTextField(fullName, { fullName = it }, "Nombre completo",
                            leadingIcon = { Icon(Icons.Outlined.Person, null, tint = DentBlue) })
                        DentTextField(phone, { phone = it }, "Teléfono",
                            keyboardType = KeyboardType.Phone,
                            leadingIcon = { Icon(Icons.Outlined.Phone, null, tint = DentBlue) })
                    }
                    // ── Paso 2: Especialidad ───────────────────────────────────
                    2 -> {
                        StepTitle("Tu especialidad", "Selecciona tu área de práctica")
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                        ) {
                            OutlinedTextField(
                                value = specialty,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Especialidad") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                            )
                            ExposedDropdownMenu(expanded, { expanded = false }) {
                                SPECIALTIES.forEach { s ->
                                    DropdownMenuItem(
                                        text = { Text(s) },
                                        onClick = { specialty = s; expanded = false },
                                    )
                                }
                            }
                        }
                    }
                    // ── Paso 3: Licencia ───────────────────────────────────────
                    2 -> {
                        StepTitle("Registro profesional", "Tu número de licencia o SENESCYT")
                        DentTextField(licenseNumber, { licenseNumber = it }, "Número de licencia",
                            leadingIcon = { Icon(Icons.Outlined.Badge, null, tint = DentBlue) })
                    }
                    // ── Paso 4: Tarifa ─────────────────────────────────────────
                    3 -> {
                        StepTitle("Tu tarifa de consulta", "Valor en USD por consulta")
                        Icon(Icons.Outlined.AttachMoney, null,
                            tint = DentBlue, modifier = Modifier.size(48.dp))
                        Text(
                            "Este valor lo verán los pacientes al agendar su cita. " +
                            "Recibirás el 50% de cada consulta directamente en tu cuenta bancaria.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = DentTextSecond,
                        )
                    }
                    // ── Paso 5: Cuenta ─────────────────────────────────────────
                    4 -> {
                        StepTitle("Crea tu cuenta", "Email y contraseña para ingresar")
                        DentTextField(email, { email = it; viewModel.clearError() },
                            "Correo electrónico", keyboardType = KeyboardType.Email,
                            leadingIcon = { Icon(Icons.Outlined.Email, null, tint = DentBlue) })
                        DentTextField(password, { password = it; viewModel.clearError() },
                            "Contraseña", isPassword = true, imeAction = ImeAction.Done)
                        state.error?.let {
                            Text(it, color = ErrorRed, style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (step < 4) {
            DentButton(
                text = "Continuar",
                onClick = { step++ },
                enabled = when (step) {
                    0 -> fullName.isNotBlank() && phone.isNotBlank()
                    1 -> specialty.isNotBlank()
                    2 -> licenseNumber.isNotBlank()
                    else -> true
                },
            )
        } else {
            DentButton(
                text = "Crear mi cuenta de doctor",
                onClick = {
                    viewModel.registerDoctor(email, password, fullName, phone, specialty, licenseNumber)
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
