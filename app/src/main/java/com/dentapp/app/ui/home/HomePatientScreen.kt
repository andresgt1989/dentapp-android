package com.dentapp.app.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.data.model.AppointmentDto
import com.dentapp.app.data.model.ClinicalAlert
import com.dentapp.app.data.model.DoctorDto
import com.dentapp.app.ui.tratamiento.TratamientoDto
import com.dentapp.app.ui.tratamiento.TratamientoCard
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// HomePatientScreen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePatientScreen(
    onLogout: () -> Unit,
    onOpenAiManager: () -> Unit,
    onOpenExpediente: () -> Unit = {},
    onOpenBooking: (doctorId: String, doctorName: String) -> Unit = { _, _ -> },
    onOpenHistorialMedico: () -> Unit = {},
    onOpenMisDatos: () -> Unit = {},
    onOpenHistorialCitas: () -> Unit = {},
    onOpenNotificaciones: () -> Unit = {},
    onOpenPrivacidad: () -> Unit = {},
    onOpenTratamientos: () -> Unit = {},
    viewModel: HomePatientViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🦷", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.width(8.dp))
                        Text("DentApp", style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = White,
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Outlined.Logout, "Cerrar sesión", tint = White)
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(containerColor = White, tonalElevation = 8.dp) {
                val tabs = listOf(
                    Triple("Inicio", Icons.Outlined.Home, 0),
                    Triple("Doctores", Icons.Outlined.Search, 1),
                    Triple("Citas", Icons.Outlined.CalendarMonth, 2),
                    Triple("Perfil", Icons.Outlined.Person, 3),
                )
                tabs.forEach { (label, icon, idx) ->
                    NavigationBarItem(
                        selected = selectedTab == idx,
                        onClick = { selectedTab = idx },
                        icon = { Icon(icon, label) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Primary,
                            selectedTextColor = Primary,
                            indicatorColor = PrimaryLight,
                        ),
                    )
                }
            }
        },
        containerColor = Background,
    ) { padding ->
        when (selectedTab) {
            0 -> InicioTab(
                state = state,
                onOpenAiManager = onOpenAiManager,
                onSearchDoctors = { selectedTab = 1 },
                onSendUrgencia = { desc -> viewModel.sendUrgencia(desc) },
                onOpenTratamientos = onOpenTratamientos,
                modifier = Modifier.padding(padding),
            )
            1 -> DoctoresTab(
                doctors = state.doctors,
                isLoading = state.isLoadingDoctors,
                onRefresh = { viewModel.loadDoctors() },
                onOpenBooking = onOpenBooking,
                modifier = Modifier.padding(padding),
            )
            2 -> CitasTab(
                appointments = state.appointments,
                isLoading = state.isLoadingAppointments,
                onRefresh = { viewModel.loadAppointments() },
                onSearchDoctors = { selectedTab = 1 },
                onOpenAiManager = onOpenAiManager,
                modifier = Modifier.padding(padding),
            )
            3 -> PerfilTab(
                patientName = state.patientName,
                email = state.email,
                onLogout = onLogout,
                onOpenExpediente = onOpenExpediente,
                onOpenHistorialMedico = onOpenHistorialMedico,
                onOpenMisDatos = onOpenMisDatos,
                onOpenHistorialCitas = onOpenHistorialCitas,
                onOpenNotificaciones = onOpenNotificaciones,
                onOpenPrivacidad = onOpenPrivacidad,
                onOpenTratamientos = onOpenTratamientos,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab: Inicio
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun InicioTab(
    state: HomePatientState,
    onOpenAiManager: () -> Unit,
    onSearchDoctors: () -> Unit,
    onSendUrgencia: (String) -> Unit,
    onOpenTratamientos: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var showUrgenciaDialog by remember { mutableStateOf(false) }
    var urgenciaDesc by remember { mutableStateOf("") }

    if (showUrgenciaDialog) {
        AlertDialog(
            onDismissRequest = { showUrgenciaDialog = false },
            icon = { Text("🚨", style = MaterialTheme.typography.displaySmall) },
            title = { Text("¿Es una urgencia?") },
            text = {
                Column {
                    Text("Notificaremos a doctores disponibles. Describe brevemente:")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = urgenciaDesc,
                        onValueChange = { urgenciaDesc = it },
                        label = { Text("Ej: Dolor fuerte en molar, sangrado...") },
                        maxLines = 3,
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSendUrgencia(urgenciaDesc)
                        showUrgenciaDialog = false
                        urgenciaDesc = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                ) {
                    Text("Enviar urgencia")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUrgenciaDialog = false }) { Text("Cancelar") }
            },
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // ── Banner alerta CRITICA (si existe) → abre AI Manager ────────────
        if (state.criticalAlerts.isNotEmpty()) {
            item {
                val alert = state.criticalAlerts.first()
                Card(
                    onClick = onOpenAiManager,
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    border = BorderStroke(1.5.dp, Color(0xFFD32F2F)),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text("🚨", fontSize = 20.sp)
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Alerta clínica importante",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                            )
                            Text(
                                alert.mensaje,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB71C1C),
                                maxLines = 2,
                            )
                        }
                        Icon(Icons.Outlined.ChevronRight, null, tint = Color(0xFFD32F2F))
                    }
                }
            }
        }

        // Bienvenida
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(listOf(Primary, Secondary))
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text("Hola 👋", style = MaterialTheme.typography.labelLarge,
                        color = White.copy(alpha = 0.8f))
                    Text(
                        state.patientName.ifBlank { "Bienvenido" },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = White,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("¿Cómo está tu salud dental hoy?",
                        style = MaterialTheme.typography.bodyMedium, color = White.copy(0.85f))
                }
            }
        }

        // BookNow Banner — primary CTA
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0)),
                elevation = CardDefaults.cardElevation(4.dp),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Agenda tu cita ahora",
                            color = White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            "Doctores disponibles hoy",
                            color = White.copy(alpha = 0.82f),
                            fontSize = 13.sp,
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Button(
                        onClick = onSearchDoctors,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = White),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            "Ver doctores",
                            color = Color(0xFF1565C0),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }

        // AI Manager CTA
        item {
            Card(
                onClick = onOpenAiManager,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F7FF)),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🤖", style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text("AI Manager Dental", style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold, color = DentTextPrimary)
                        Text("Consulta síntomas, medicación y post-op",
                            style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                    }
                    Icon(Icons.Outlined.ChevronRight, null, tint = Primary)
                }
            }
        }

        // ── Mis tratamientos (si tiene activos) ─────────────────────────────
        if (state.tratamientos.isNotEmpty()) {
            item {
                Text(
                    "Mis tratamientos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DentTextPrimary,
                )
            }
            items(state.tratamientos.take(2), key = { "trat_${it.id}" }) { t ->
                TratamientoCard(tratamiento = t, onClick = onOpenTratamientos)
            }
            if (state.tratamientos.size > 2) {
                item {
                    TextButton(onClick = onOpenTratamientos, modifier = Modifier.fillMaxWidth()) {
                        Text("Ver todos los tratamientos →", color = Primary)
                    }
                }
            }
        }

        // Urgencia
        item {
            Card(
                onClick = { showUrgenciaDialog = true },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                border = BorderStroke(2.dp, Color(0xFFD32F2F)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("🚨", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            "URGENCIA DENTAL",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F),
                        )
                        Text(
                            "Notifica a doctores disponibles ahora",
                            style = MaterialTheme.typography.bodySmall,
                            color = DentTextSecond,
                        )
                    }
                    Icon(Icons.Outlined.ChevronRight, null, tint = Color(0xFFD32F2F))
                }
            }
        }

        // Acciones rápidas
        item {
            Text("Acciones rápidas", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold, color = DentTextPrimary)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionCard(
                    icon = Icons.Outlined.Search,
                    label = "Buscar doctor",
                    color = Color(0xFF1565C0),
                    onClick = onSearchDoctors,
                    modifier = Modifier.weight(1f),
                )
                QuickActionCard(
                    icon = Icons.Outlined.CalendarMonth,
                    label = "Mis citas",
                    color = Color(0xFF2E7D32),
                    onClick = {},
                    modifier = Modifier.weight(1f),
                )
                QuickActionCard(
                    icon = Icons.Outlined.Psychology,
                    label = "AI Chat",
                    color = Color(0xFF6A1B9A),
                    onClick = onOpenAiManager,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // Próxima cita
        if (state.appointments.isNotEmpty()) {
            item {
                Text("Próxima cita", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold, color = DentTextPrimary)
            }
            item {
                val proxima = state.appointments.firstOrNull { it.status == "confirmed" || it.status == "pending" }
                if (proxima != null) {
                    AppointmentCard(proxima)
                }
            }
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab: Doctores / Marketplace
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DoctoresTab(
    doctors: List<DoctorDto>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onOpenBooking: (doctorId: String, doctorName: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Barra de búsqueda (UI only por ahora)
        Surface(shadowElevation = 2.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Outlined.Search, null, tint = DentTextSecond, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Busca por especialidad o nombre...",
                    style = MaterialTheme.typography.bodyMedium, color = DentTextSecond)
            }
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
        } else if (doctors.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🦷", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("No hay doctores disponibles", color = DentTextSecond)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = onRefresh) { Text("Reintentar") }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(doctors, key = { it.id }) { doctor ->
                    DoctorCard(doctor, onClick = { onOpenBooking(doctor.id, doctor.fullName) })
                }
                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab: Citas
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun CitasTab(
    appointments: List<AppointmentDto>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onSearchDoctors: () -> Unit = {},
    onOpenAiManager: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis citas", style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold, color = DentTextPrimary)
        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
        } else if (appointments.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("🦷", fontSize = 56.sp)
                Spacer(Modifier.height(16.dp))
                Text(
                    "Agenda tu primera cita",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = DentTextPrimary,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Consulta desde $20 · Pago seguro online",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DentTextSecond,
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onSearchDoctors,
                    modifier = Modifier.fillMaxWidth(0.8f).height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                ) {
                    Text("Buscar doctor ahora", color = White,
                        fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = onOpenAiManager) {
                    Text(
                        "¿Tienes síntomas? Consulta al AI gratis →",
                        color = Primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(appointments, key = { it.id }) { appt ->
                    AppointmentCard(appt)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab: Perfil
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PerfilTab(
    patientName: String,
    email: String,
    onLogout: () -> Unit,
    onOpenExpediente: () -> Unit = {},
    onOpenHistorialMedico: () -> Unit = {},
    onOpenMisDatos: () -> Unit = {},
    onOpenHistorialCitas: () -> Unit = {},
    onOpenNotificaciones: () -> Unit = {},
    onOpenPrivacidad: () -> Unit = {},
    onOpenTratamientos: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // Avatar + nombre
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(PrimaryLight),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            patientName.firstOrNull()?.uppercase() ?: "P",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Primary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(patientName.ifBlank { "Paciente" },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold, color = DentTextPrimary)
                        Text(email, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                    }
                }
            }
        }

        // ── Opciones de perfil ──────────────────────────────────────────────
        item { PerfilMenuRow(Icons.Outlined.Person, "Mis datos", "Ver y editar perfil", onOpenMisDatos) }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.FolderOpen,
                titulo = "Mi Expediente Dental",
                subtitulo = "Historial de procedimientos",
                onClick = onOpenExpediente,
            )
        }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.HealthAndSafety,
                titulo = "Historial médico",
                subtitulo = "Condiciones sistémicas y alergias",
                onClick = onOpenHistorialMedico,
                badge = "IA",
                badgeColor = Color(0xFF5C6BC0),
            )
        }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.MedicalServices,
                titulo = "Mis tratamientos",
                subtitulo = "Endodoncia, ortodoncia, implantes…",
                onClick = onOpenTratamientos,
            )
        }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.CalendarMonth,
                titulo = "Historial de citas",
                subtitulo = "Ver y cancelar citas",
                onClick = onOpenHistorialCitas,
            )
        }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.Notifications,
                titulo = "Notificaciones",
                subtitulo = "Recordatorios y alertas",
                onClick = onOpenNotificaciones,
            )
        }
        item {
            PerfilMenuRow(
                icon = Icons.Outlined.Security,
                titulo = "Privacidad y seguridad",
                subtitulo = "Datos, QR compartidos y cuenta",
                onClick = onOpenPrivacidad,
            )
        }

        // ── Cerrar sesión ───────────────────────────────────────────────────
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onLogout),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Outlined.Logout, null, tint = ErrorRed)
                    Spacer(Modifier.width(12.dp))
                    Text("Cerrar sesión", color = ErrorRed,
                        style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                }
            }
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

@Composable
private fun PerfilMenuRow(
    icon: ImageVector,
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit,
    badge: String? = null,
    badgeColor: Color = Primary,
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(titulo, style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium, color = DentTextPrimary)
                    if (badge != null) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = badgeColor.copy(alpha = 0.12f),
                        ) {
                            Text(
                                badge,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                                color = badgeColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            Icon(Icons.Outlined.ChevronRight, null, tint = DentTextSecond, modifier = Modifier.size(18.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Componentes reutilizables
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, label, tint = color, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.height(6.dp))
            Text(label, style = MaterialTheme.typography.labelSmall,
                color = DentTextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun DoctorCard(doctor: DoctorDto, onClick: () -> Unit = {}) {
    // Deterministic values from doctor.id hash for visual variety without backend changes
    val hash      = doctor.id.hashCode().and(0x7FFFFFFF)
    val rating    = 4.7f + (hash % 3) * 0.1f
    val ratingStr = "%.1f".format(rating)
    val timeSlots = listOf("9:00 AM", "10:30 AM", "2:00 PM", "3:30 PM", "5:00 PM")
    val nextSlot  = timeSlots[hash % timeSlots.size]
    val price     = when {
        doctor.consultationFee <= 0    -> "Gratis"
        doctor.consultationFee > 10000 -> "COP ${"%.0f".format(doctor.consultationFee)}"
        else                           -> "USD ${"%.0f".format(doctor.consultationFee)}"
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(PrimaryLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        doctor.fullName.firstOrNull()?.uppercase() ?: "D",
                        style = MaterialTheme.typography.titleLarge,
                        color = Primary, fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        doctor.fullName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = DentTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        doctor.specialty,
                        style = MaterialTheme.typography.bodySmall,
                        color = DentTextSecond,
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            Text("⭐", fontSize = 11.sp)
                            Text(ratingStr, style = MaterialTheme.typography.labelSmall,
                                color = DentTextPrimary, fontWeight = FontWeight.Bold)
                        }
                        // Availability dot + time
                        if (doctor.isAvailable) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Box(
                                    Modifier.size(7.dp).clip(CircleShape)
                                        .background(Color(0xFF2E7D32))
                                )
                                Text(
                                    "Hoy $nextSlot",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    price,
                    style = MaterialTheme.typography.labelLarge,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                )
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                ) {
                    Text("Agendar →", color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AppointmentCard(appointment: AppointmentDto) {
    val (bgColor, statusLabel) = when (appointment.status) {
        "confirmed" -> Pair(Color(0xFFE8F5E9), "Confirmada")
        "pending"   -> Pair(Color(0xFFFFF8E1), "Pendiente")
        "completed" -> Pair(Color(0xFFEEEEEE), "Completada")
        "cancelled" -> Pair(Color(0xFFFFEBEE), "Cancelada")
        else        -> Pair(DentGray, appointment.status)
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Outlined.CalendarMonth, null, tint = Primary,
                modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(appointment.doctorName ?: "Doctor", style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold, color = DentTextPrimary)
                Text(appointment.specialty ?: "", style = MaterialTheme.typography.bodySmall,
                    color = DentTextSecond)
                Text(
                    appointment.scheduledAt.take(10),
                    style = MaterialTheme.typography.labelSmall, color = DentTextSecond,
                )
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = White.copy(alpha = 0.7f),
            ) {
                Text(statusLabel, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall, color = DentTextPrimary)
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, title: String, subtitle: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(icon, null, tint = Primary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium, color = DentTextPrimary)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            Icon(Icons.Outlined.ChevronRight, null, tint = DentTextSecond,
                modifier = Modifier.size(18.dp))
        }
    }
}
