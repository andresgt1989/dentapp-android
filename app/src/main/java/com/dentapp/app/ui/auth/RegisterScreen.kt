package com.dentapp.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dentapp.app.R
import com.dentapp.app.ui.components.DentButton
import com.dentapp.app.ui.theme.*

@Composable
fun RegisterScreen(
    onNavigateToPatientOnboarding: () -> Unit,
    onNavigateToDoctorOnboarding: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(DentGray)) {

        // Cabecera
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(DentBlue, DentBlueDark),
                        start  = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end    = androidx.compose.ui.geometry.Offset(1000f, 600f),
                    )
                ),
        ) {
            IconButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
            ) {
                Icon(Icons.Outlined.ArrowBack, "Volver", tint = White)
            }

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Crear cuenta",
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "¿Cómo vas a usar DentApp?",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 160.dp),
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
                        "Selecciona tu rol",
                        style = MaterialTheme.typography.titleLarge,
                        color = DentTextPrimary,
                    )
                    Text(
                        "Esto determina cómo funciona la app para ti.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DentTextSecond,
                    )

                    Spacer(Modifier.height(8.dp))

                    // Tarjeta Paciente
                    RoleCard(
                        emoji        = "🧑",
                        title        = "Soy Paciente",
                        description  = "Agenda citas, recibe recordatorios y paga de forma segura desde tu teléfono.",
                        features     = listOf("Buscar doctores", "Agendar citas", "Pagar en app", "Recordatorios"),
                        selected     = selectedRole == "patient",
                        onClick      = { selectedRole = "patient" },
                    )

                    // Tarjeta Doctor
                    RoleCard(
                        emoji        = "👨‍⚕️",
                        title        = "Soy Doctor",
                        description  = "Gestiona tu agenda, tus pacientes y recibe pagos directamente en tu cuenta.",
                        features     = listOf("Ver mi agenda", "Historial pacientes", "Recibir pagos 50%", "Notificaciones"),
                        selected     = selectedRole == "doctor",
                        onClick      = { selectedRole = "doctor" },
                    )

                    Spacer(Modifier.height(8.dp))

                    // Botón continuar — animado cuando hay selección
                    AnimatedVisibility(
                        visible = selectedRole != null,
                        enter   = fadeIn() + slideInVertically { it },
                    ) {
                        DentButton(
                            text = "Continuar como ${if (selectedRole == "doctor") "Doctor" else "Paciente"}",
                            onClick = {
                                if (selectedRole == "doctor") onNavigateToDoctorOnboarding()
                                else onNavigateToPatientOnboarding()
                            },
                        )
                    }

                    if (selectedRole == null) {
                        Text(
                            "↑ Selecciona un rol para continuar",
                            color = DentTextSecond,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    // Ya tengo cuenta
                    TextButton(
                        onClick = onNavigateToLogin,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = DentTextSecond)) {
                                    append("¿Ya tienes cuenta? ")
                                }
                                withStyle(SpanStyle(color = DentBlue, fontWeight = FontWeight.Bold)) {
                                    append("Iniciar sesión")
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

@Composable
private fun RoleCard(
    emoji: String,
    title: String,
    description: String,
    features: List<String>,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (selected) DentBlue else DentBlueLight
    val bgColor     = if (selected) DentBlueLight else White

    Surface(
        onClick  = onClick,
        shape    = RoundedCornerShape(20.dp),
        color    = bgColor,
        border   = BorderStroke(width = if (selected) 2.dp else 1.dp, color = borderColor),
        shadowElevation = if (selected) 4.dp else 1.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            if (selected) DentBlue else DentBlueLight,
                            RoundedCornerShape(14.dp),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(emoji, fontSize = 26.sp)
                }
                Spacer(Modifier.width(14.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selected) DentBlue else DentTextPrimary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DentTextSecond,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
                if (selected) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = "Seleccionado",
                        tint = DentBlue,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            // Features chips
            Spacer(Modifier.height(14.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                features.take(4).forEach { feature ->
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (selected) DentBlue.copy(alpha = 0.12f) else DentGray,
                    ) {
                        Text(
                            text = feature,
                            fontSize = 11.sp,
                            color = if (selected) DentBlue else DentTextSecond,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        )
                    }
                }
            }
        }
    }
}
