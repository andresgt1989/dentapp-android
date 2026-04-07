package com.dentapp.app.ui.subscriptions

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(
    onBack: () -> Unit,
    viewModel: SubscriptionViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planes DentApp", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            // Toggle mensual / anual
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Mensual", style = MaterialTheme.typography.bodyMedium, color = if (!state.anual) DentBlue else DentTextSecond)
                Switch(
                    checked = state.anual,
                    onCheckedChange = { viewModel.toggleAnual() },
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = SwitchDefaults.colors(checkedThumbColor = DentBlue, checkedTrackColor = DentBlueLight),
                )
                Text("Anual", style = MaterialTheme.typography.bodyMedium, color = if (state.anual) DentBlue else DentTextSecond)
                Spacer(Modifier.width(6.dp))
                if (state.anual) {
                    Badge(containerColor = DentGreen) {
                        Text("Ahorra 33%", color = Color.White, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }

            // Plan GRATIS
            PlanCard(
                nombre = "Gratis",
                precio = "Gratis",
                color = Color(0xFFE8F5E9),
                borderColor = DentGreen,
                destacado = false,
                features = listOf(
                    "Expediente básico",
                    "1 tratamiento activo",
                    "AI Manager 10 mensajes/día",
                    "Recordatorios básicos",
                ),
                botonText = "Tu plan actual",
                botonEnabled = false,
                onBoton = {},
            )

            // Plan PRO
            PlanCard(
                nombre = "Pro",
                precio = if (state.anual) "$39.99/año" else "$4.99/mes",
                color = Color.White,
                borderColor = DentBlue,
                destacado = true,
                features = listOf(
                    "Tratamientos ilimitados",
                    "AI Manager ilimitado",
                    "QR compartible + Escanear recetas",
                    "Captura de radiografías",
                    "Historial completo",
                    "Screening cáncer oral",
                ),
                botonText = "Empezar 14 días gratis",
                botonEnabled = !state.isLoading,
                onBoton = { viewModel.startTrial() },
            )

            // Plan DOCTOR PRO (solo si rol = doctor)
            if (state.isDoctor) {
                PlanCard(
                    nombre = "Doctor Pro",
                    precio = "$59/mes",
                    color = Color(0xFFF3E5F5),
                    borderColor = Color(0xFF7B1FA2),
                    destacado = false,
                    features = listOf(
                        "Todo lo de Pro",
                        "Dashboard de pacientes",
                        "Stripe Connect (cobros)",
                        "Alertas clínicas por QR",
                        "Reportes semanales IA",
                    ),
                    botonText = "Contactar ventas",
                    botonEnabled = true,
                    onBoton = { /* abrir chat/email */ },
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "ℹ️ Orientación dental — no reemplaza diagnóstico profesional",
                style = MaterialTheme.typography.bodySmall,
                color = DentTextSecond,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PlanCard(
    nombre: String,
    precio: String,
    color: Color,
    borderColor: Color,
    destacado: Boolean,
    features: List<String>,
    botonText: String,
    botonEnabled: Boolean,
    onBoton: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(if (destacado) 2.dp else 1.dp, borderColor, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(if (destacado) 4.dp else 1.dp),
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(precio, color = borderColor, fontWeight = FontWeight.SemiBold)
                }
                if (destacado) {
                    Badge(containerColor = DentBlue) {
                        Text("Más popular", color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = borderColor.copy(alpha = 0.3f))
            Spacer(Modifier.height(12.dp))

            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Outlined.CheckCircle, null,
                        tint = borderColor, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(feature, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onBoton,
                modifier = Modifier.fillMaxWidth(),
                enabled = botonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = borderColor,
                    disabledContainerColor = borderColor.copy(alpha = 0.4f),
                ),
            ) {
                Text(botonText)
            }
        }
    }
}
