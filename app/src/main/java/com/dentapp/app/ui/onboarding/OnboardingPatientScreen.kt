package com.dentapp.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.components.DentButton
import com.dentapp.app.ui.components.DentTextField
import com.dentapp.app.ui.theme.*

// ─── Flujo del paciente: <90 segundos ─────────────────────────────────────────
//  0 = pantalla de bienvenida      "Hola, soy tu asistente dental…"
//  1 = 3 preguntas conversacionales (dolor, tratamiento activo, ciudad)
//  4 = dashboard (success)
// -1 = rama secretaria (sin cambios)

private data class StepState(val step: Int, val forward: Boolean = true)

@Composable
fun OnboardingPatientScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val vmState by viewModel.state.collectAsState()
    var stepState by remember { mutableStateOf(StepState(0)) }

    fun go(next: Int, forward: Boolean = next > stepState.step) {
        stepState = StepState(next, forward)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Header con barra de progreso solo en paso 1
        if (stepState.step == 1) {
            OnboardingHeader(
                step = stepState.step,
                onBack = { go(0, forward = false) },
            )
        }

        AnimatedContent(
            targetState = stepState,
            transitionSpec = {
                if (targetState.forward)
                    slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
                else
                    slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            },
            label = "onboarding_step",
            modifier = Modifier.fillMaxSize(),
        ) { state ->
            when (state.step) {
                0 -> WelcomeStep(
                    onStart = { go(1) },
                    onSkip = {
                        viewModel.setRole("patient")
                        viewModel.saveOnboarding()
                        onSuccess()
                    },
                )
                -1 -> SecretaryStep(
                    inviteCode   = vmState.inviteCode,
                    onCodeChange = viewModel::setInviteCode,
                    onContinue   = { viewModel.saveOnboarding(); onSuccess() },
                    isSaving     = vmState.isSaving,
                )
                1 -> ConversationalOnboardingStep(
                    viewModel = viewModel,
                    onFinish = {
                        viewModel.setRole("patient")
                        viewModel.saveOnboarding()
                        onSuccess()
                    },
                )
                else -> WelcomeStep(onStart = { go(1) }, onSkip = onSuccess)
            }
        }
    }
}

// ─── Header con barra de progreso ─────────────────────────────────────────────

@Composable
private fun OnboardingHeader(step: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp),
            ) {
                Text("← Atrás", color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.weight(1f))
            Text("3 preguntas rápidas", style = MaterialTheme.typography.labelMedium, color = DentTextSecond)
        }
        Spacer(Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { 0.33f },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = DentBlueLight,
        )
    }
}

// ─── Paso 0: Pantalla de bienvenida (<15 segundos) ────────────────────────────

@Composable
private fun WelcomeStep(
    onStart: () -> Unit,
    onSkip: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(listOf(Primary, Secondary))
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text("🦷", fontSize = 36.sp)
        }

        Spacer(Modifier.height(28.dp))

        Text(
            "Hola, soy tu asistente dental personal",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = DentTextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "3 preguntas rápidas para empezar.\nMenos de 1 minuto.",
            style = MaterialTheme.typography.bodyLarge,
            color = DentTextSecond,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
        ) {
            Text("Empezar →", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = White)
        }

        Spacer(Modifier.height(14.dp))

        TextButton(onClick = onSkip) {
            Text("Saltar por ahora", color = DentTextSecond,
                style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// ─── Paso 1: Chat conversacional 3 preguntas (<75 segundos) ──────────────────

@Composable
private fun ConversationalOnboardingStep(
    viewModel: OnboardingViewModel,
    onFinish: () -> Unit,
) {
    // Preguntas en orden — UNA a la vez
    val preguntas = listOf(
        "¿Tienes algún dolor o molestia dental ahora mismo,\no llegaste por prevención?" to listOf("Sí, tengo dolor", "Solo prevención", "No sé / Otro"),
        "¿Tienes algún tratamiento dental activo?\n(brackets, implante, corona…)" to listOf("Sí, tengo uno", "No tengo", "Terminé uno hace poco"),
        "¿En qué ciudad estás? Esto nos ayuda a mostrarte dentistas cerca." to emptyList(),
    )

    var preguntaActual by remember { mutableIntStateOf(0) }
    var cityText by remember { mutableStateOf("") }
    // Lista de burbujas de chat para mostrar historial
    val bubbles = remember { mutableStateListOf<Pair<Boolean, String>>() } // isAi, text

    LaunchedEffect(Unit) {
        bubbles.add(true to preguntas[0].first)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9)),
    ) {
        // Chat bubbles
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(bubbles.size) { i ->
                val (isAi, text) = bubbles[i]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End,
                ) {
                    if (isAi) {
                        Box(
                            Modifier.size(32.dp).clip(CircleShape).background(Primary),
                            contentAlignment = Alignment.Center,
                        ) { Text("🦷", fontSize = 14.sp) }
                        Spacer(Modifier.width(8.dp))
                    }
                    Box(
                        Modifier
                            .widthIn(max = 280.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = if (isAi) 4.dp else 16.dp,
                                    topEnd   = if (isAi) 16.dp else 4.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd   = 16.dp,
                                )
                            )
                            .background(if (isAi) Color.White else Primary)
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isAi) DentTextPrimary else White,
                            lineHeight = 20.sp,
                        )
                    }
                }
            }
        }

        // Opciones o input de ciudad
        Surface(shadowElevation = 4.dp, color = Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (preguntaActual < 2) {
                    // Opciones de selección rápida
                    val opciones = preguntas[preguntaActual].second
                    opciones.forEach { opcion ->
                        OutlinedButton(
                            onClick = {
                                bubbles.add(false to opcion)
                                // Guardar en viewModel según pregunta
                                when (preguntaActual) {
                                    0 -> viewModel.setLastVisit(
                                        if (opcion.contains("dolor")) "pain" else "prevention"
                                    )
                                    1 -> viewModel.setMedicalCondition(
                                        if (opcion.contains("Sí")) "active_treatment" else "none"
                                    )
                                }
                                preguntaActual++
                                if (preguntaActual < preguntas.size) {
                                    bubbles.add(true to preguntas[preguntaActual].first)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(opcion, color = DentTextPrimary)
                        }
                    }
                } else {
                    // Input de ciudad (Q3)
                    OutlinedTextField(
                        value = cityText,
                        onValueChange = { cityText = it },
                        placeholder = { Text("Escribe tu ciudad…") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                    )
                    Button(
                        onClick = {
                            if (cityText.isNotBlank()) {
                                bubbles.add(false to cityText)
                                // Mapear ciudad → código de país aproximado
                                val countryCode = when {
                                    cityText.lowercase().contains("bogot") ||
                                    cityText.lowercase().contains("medell") ||
                                    cityText.lowercase().contains("cali") -> "CO"
                                    cityText.lowercase().contains("guayaq") ||
                                    cityText.lowercase().contains("quito") -> "EC"
                                    cityText.lowercase().contains("cdmx") ||
                                    cityText.lowercase().contains("guadal") ||
                                    cityText.lowercase().contains("monterr") -> "MX"
                                    else -> "CO"
                                }
                                viewModel.setCountry(countryCode)
                                onFinish()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        enabled = cityText.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    ) {
                        Text("¡Listo, empieza! →", fontWeight = FontWeight.Bold, color = White)
                    }
                }
            }
        }
    }
}

// ─── Rama secretaria (sin cambios) ────────────────────────────────────────────

@Composable
private fun SecretaryStep(
    inviteCode:   String,
    onCodeChange: (String) -> Unit,
    onContinue:   () -> Unit,
    isSaving:     Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "Modo secretaria / asistente",
                style = MaterialTheme.typography.headlineMedium,
                color = DentTextPrimary,
            )
            Text(
                "En este modo gestionarás citas y pacientes en nombre del médico. " +
                "Para vincularte, ingresa el código de invitación que te compartió tu doctor.",
                style = MaterialTheme.typography.bodyMedium,
                color = DentTextSecond,
            )
            Spacer(Modifier.height(8.dp))
            DentTextField(
                value         = inviteCode,
                onValueChange = onCodeChange,
                label         = "Código de invitación del doctor",
            )
        }
        Spacer(Modifier.height(16.dp))
        DentButton(
            text      = "Continuar",
            onClick   = onContinue,
            isLoading = isSaving,
            enabled   = inviteCode.isNotBlank(),
        )
        Spacer(Modifier.height(24.dp))
    }
}
