package com.dentapp.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.data.model.AppointmentDto
import com.dentapp.app.data.model.DoctorDto
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// HomePatientScreen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePatientScreen(
    onLogout: () -> Unit,
    onOpenAiManager: () -> Unit,
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
                modifier = Modifier.padding(padding),
            )
            1 -> DoctoresTab(
                doctors = state.doctors,
                isLoading = state.isLoadingDoctors,
                onRefresh = { viewModel.loadDoctors() },
                modifier = Modifier.padding(padding),
            )
            2 -> CitasTab(
                appointments = state.appointments,
                isLoading = state.isLoadingAppointments,
                onRefresh = { viewModel.loadAppointments() },
                modifier = Modifier.padding(padding),
            )
            3 -> PerfilTab(
                patientName = state.patientName,
                email = state.email,
                onLogout = onLogout,
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
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
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

        // Tips dentales
        item {
            TipCard()
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
                    DoctorCard(doctor)
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier.padding(32.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("📅", style = MaterialTheme.typography.displaySmall)
                    Spacer(Modifier.height(12.dp))
                    Text("Sin citas registradas", style = MaterialTheme.typography.titleMedium,
                        color = DentTextPrimary)
                    Text("Busca un doctor y agenda tu primera cita",
                        style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
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
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
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

        // Opciones
        item {
            ProfileMenuItem(Icons.Outlined.Person, "Mis datos", "Ver y editar perfil")
        }
        item {
            ProfileMenuItem(Icons.Outlined.HealthAndSafety, "Historial médico", "Condiciones y alergias")
        }
        item {
            ProfileMenuItem(Icons.Outlined.Notifications, "Notificaciones", "Recordatorios y alertas")
        }
        item {
            ProfileMenuItem(Icons.Outlined.Security, "Privacidad", "Datos y seguridad")
        }
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
private fun DoctorCard(doctor: DoctorDto) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
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
                Text(doctor.fullName, style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold, color = DentTextPrimary)
                Text(doctor.specialty, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                if (doctor.consultationFee > 0) {
                    Spacer(Modifier.height(4.dp))
                    Text("$${doctor.consultationFee.toInt()} consulta",
                        style = MaterialTheme.typography.labelMedium, color = Primary)
                }
            }
            if (doctor.isAvailable) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32))
                )
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
private fun TipCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Text("💡", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.width(10.dp))
            Column {
                Text("Consejo del día", style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Cepilla tus dientes al menos 2 veces al día durante 2 minutos. " +
                        "Usa hilo dental antes de dormir para prevenir la periodontitis.",
                    style = MaterialTheme.typography.bodySmall, color = DentTextPrimary,
                )
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
