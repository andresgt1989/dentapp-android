package com.dentapp.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
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
import com.dentapp.app.data.model.DoctorDto
import com.dentapp.app.ui.tratamiento.TratamientoDto
import com.dentapp.app.ui.tratamiento.TratamientoCard
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// HomePatientScreen
// ─────────────────────────────────────────────────────────────────────────────

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
    onOpenRx: () -> Unit = {},
    onOpenLoyalty: () -> Unit = {},
    onOpenSubscription: () -> Unit = {},
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
        bottomBar = {
            PremiumNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
        containerColor = Background,
    ) { padding ->
        when (selectedTab) {
            0 -> InicioTab(
                state = state,
                onOpenAiManager = onOpenAiManager,
                onSearchDoctors = { selectedTab = 1 },
                onOpenCitas = { selectedTab = 2 },
                onOpenRx = onOpenRx,
                onOpenLoyalty = onOpenLoyalty,
                onOpenNotificaciones = onOpenNotificaciones,
                onSendUrgencia = { desc -> viewModel.sendUrgencia(desc) },
                onOpenTratamientos = onOpenTratamientos,
                onOpenBooking = onOpenBooking,
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
                loyaltyPoints = state.loyaltyPoints,
                onLogout = onLogout,
                onOpenExpediente = onOpenExpediente,
                onOpenHistorialMedico = onOpenHistorialMedico,
                onOpenMisDatos = onOpenMisDatos,
                onOpenHistorialCitas = onOpenHistorialCitas,
                onOpenNotificaciones = onOpenNotificaciones,
                onOpenPrivacidad = onOpenPrivacidad,
                onOpenTratamientos = onOpenTratamientos,
                onOpenRx = onOpenRx,
                onOpenSubscription = onOpenSubscription,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Premium Bottom Navigation Bar
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PremiumNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
) {
    val tabs = listOf(
        Triple("Inicio", Icons.Outlined.Home, 0),
        Triple("Doctores", Icons.Outlined.Search, 1),
        Triple("Citas", Icons.Outlined.CalendarMonth, 2),
        Triple("Perfil", Icons.Outlined.Person, 3),
    )

    Surface(
        color = SurfaceWhite,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = SurfaceGray,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 1f,
                    )
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .navigationBarsPadding()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEach { (label, icon, idx) ->
                    NavBarItem(
                        label = label,
                        icon = icon,
                        selected = selectedTab == idx,
                        onClick = { onTabSelected(idx) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun NavBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1f else 0.92f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f),
        label = "navScale",
    )

    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                onClick = onClick,
            )
            .scale(scale),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .height(32.dp)
                .defaultMinSize(minWidth = 56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (selected) TealLight.copy(alpha = 0.30f) else Color.Transparent
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) TealPrimary else TextTertiary,
                modifier = Modifier.size(22.dp),
            )
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) TealPrimary else TextTertiary,
        )
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
    onOpenCitas: () -> Unit = {},
    onOpenRx: () -> Unit = {},
    onOpenLoyalty: () -> Unit = {},
    onOpenNotificaciones: () -> Unit = {},
    onSendUrgencia: (String) -> Unit,
    onOpenTratamientos: () -> Unit = {},
    onOpenBooking: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
) {
    var showUrgenciaSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showUrgenciaSheet) {
        ModalBottomSheet(
            onDismissRequest = { showUrgenciaSheet = false },
            sheetState = rememberModalBottomSheetState(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("🚨", fontSize = 40.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Urgencia Dental",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                )
                Spacer(Modifier.height(4.dp))
                Text("¿Qué necesitas hacer?", fontSize = 14.sp, color = TextSecondary)
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:911"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        "🚨 Llamar emergencias",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                    )
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = {
                        showUrgenciaSheet = false
                        onOpenAiManager()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, TealPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary),
                ) {
                    Text("💬 Chat urgente con IA", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // ── Header — greeting row ──────────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Avatar circular con iniciales y gradiente
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.patientName.firstOrNull()?.uppercase() ?: "P",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Hola, ${state.patientName.ifBlank { "Bienvenido" }}",
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "¿Cómo estás hoy?",
                        color = TextSecondary,
                        fontSize = 13.sp,
                    )
                }
                IconButton(onClick = { onOpenNotificaciones() }) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Notificaciones",
                        tint = TextSecondary,
                    )
                }
            }
        }

        // ── Hero Card (gradiente, compacto) ───────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .padding(horizontal = 20.dp, vertical = 14.dp),
            ) {
                // Círculo decorativo
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(x = 40.dp, y = (-16).dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.06f)),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Tu salud dental",
                            color = White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Asistente IA listo para ayudarte",
                            color = White.copy(alpha = 0.75f),
                            fontSize = 12.sp,
                        )
                    }
                    Text("🦷", fontSize = 36.sp)
                }
            }
        }

        // ── Alertas críticas ───────────────────────────────────────────────────
        if (state.criticalAlerts.isNotEmpty()) {
            item {
                val alert = state.criticalAlerts.first()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CoralLight)
                        .clickable(onClick = onOpenAiManager),
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(44.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(CoralAccent),
                        )
                        Text("🚨", fontSize = 20.sp)
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Alerta clínica importante",
                                fontWeight = FontWeight.Bold,
                                color = AlertRed,
                                fontSize = 13.sp,
                            )
                            Text(
                                alert.mensaje,
                                style = MaterialTheme.typography.bodySmall,
                                color = AlertRed.copy(alpha = 0.8f),
                                maxLines = 2,
                            )
                        }
                        Icon(Icons.Outlined.ChevronRight, null, tint = AlertRed)
                    }
                }
            }
        }

        // ── Acciones rápidas — grid 3 columnas ────────────────────────────────
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Acciones rápidas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                )
                Spacer(Modifier.height(12.dp))
                val actions = listOf(
                    Triple("Buscar doctor",  Icons.Outlined.Search,          TealPrimary),
                    Triple("Mis citas",      Icons.Outlined.CalendarMonth,   Color(0xFF3B82F6)),
                    Triple("AI Chat",        Icons.Outlined.Psychology,       Color(0xFF8B5CF6)),
                    Triple("Mis Rx",         Icons.Outlined.MedicalServices,  AlertAmber),
                    Triple("Urgencia",       Icons.Outlined.LocalHospital,    CoralAccent),
                    Triple("Puntos",         Icons.Outlined.Stars,            SuccessGreen),
                )
                val rows = actions.chunked(3)
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    rows.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            row.forEach { (label, icon, color) ->
                                QuickActionCard(
                                    icon = icon,
                                    label = label,
                                    color = color,
                                    onClick = when (label) {
                                        "Buscar doctor" -> onSearchDoctors
                                        "Mis citas"     -> onOpenCitas
                                        "AI Chat"       -> onOpenAiManager
                                        "Mis Rx"        -> onOpenRx
                                        "Urgencia"      -> { { showUrgenciaSheet = true } }
                                        "Puntos"        -> onOpenLoyalty
                                        else            -> { {} }
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            // Fill remaining cells if row is incomplete
                            repeat(3 - row.size) {
                                Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        // ── Mis tratamientos ───────────────────────────────────────────────────
        if (state.tratamientos.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Mis tratamientos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f),
                    )
                    TextButton(onClick = onOpenTratamientos) {
                        Text("Ver todos", color = TealPrimary, fontSize = 13.sp)
                    }
                }
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.tratamientos, key = { "trat_${it.id}" }) { t ->
                        TreatmentMiniCard(tratamiento = t, onClick = onOpenTratamientos)
                    }
                }
            }
        }

        // ── Doctores disponibles ───────────────────────────────────────────────
        if (state.doctors.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Doctores disponibles",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f),
                    )
                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = TealLight.copy(alpha = 0.40f),
                    ) {
                        Text(
                            "Tu ciudad",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = TealDark,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.doctors.take(6), key = { it.id }) { doctor ->
                        DoctorMiniCard(
                            doctor = doctor,
                            onClick = { onOpenBooking(doctor.id, doctor.fullName) },
                        )
                    }
                }
            }
        }

        // ── Próxima cita ───────────────────────────────────────────────────────
        if (state.appointments.isNotEmpty()) {
            val proxima = state.appointments.firstOrNull { it.status == "confirmed" || it.status == "pending" }
            if (proxima != null) {
                item {
                    Text(
                        "Próxima cita",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
                item {
                    AppointmentCard(
                        appointment = proxima,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ── Hero chip ─────────────────────────────────────────────────────────────────

@Composable
private fun HeroChip(text: String, onClick: () -> Unit) {
    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = White.copy(alpha = 0.20f),
        border = BorderStroke(1.dp, White.copy(alpha = 0.40f)),
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
    ) {
        Text(
            text = text,
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}

// ── Quick action card (3-col grid item) ───────────────────────────────────────

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f),
        label = "qaScale",
    )

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = modifier.scale(scale),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, label, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(
                label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

// ── Treatment mini card (horizontal scroll) ───────────────────────────────────

@Composable
private fun TreatmentMiniCard(tratamiento: TratamientoDto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = Modifier.width(160.dp),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(TealPrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Outlined.MedicalServices,
                    null,
                    tint = TealPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = tratamiento.tipo,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = tratamiento.faseActual ?: "En progreso",
                fontSize = 11.sp,
                color = TextSecondary,
                maxLines = 1,
            )
            Spacer(Modifier.height(10.dp))
            val progress = when (tratamiento.status) {
                "completado" -> 1f
                "pausado"    -> 0.5f
                else -> try {
                    val fmt = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    val inicio = fmt.parse(tratamiento.fechaInicio)?.time ?: 0L
                    val fin    = tratamiento.fechaEstimadaFin?.let { fmt.parse(it)?.time } ?: 0L
                    if (fin > inicio) ((System.currentTimeMillis() - inicio).toFloat() / (fin - inicio).toFloat()).coerceIn(0.05f, 0.95f)
                    else 0.3f
                } catch (_: Exception) { 0.3f }
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = TealPrimary,
                trackColor = TealLight.copy(alpha = 0.40f),
            )
        }
    }
}

// ── Doctor mini card (horizontal scroll) ──────────────────────────────────────

@Composable
private fun DoctorMiniCard(doctor: DoctorDto, onClick: () -> Unit) {
    val hash      = doctor.id.hashCode().and(0x7FFFFFFF)
    val rating    = 4.7f + (hash % 3) * 0.1f
    val ratingStr = "%.1f".format(rating)

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = Modifier.width(200.dp),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    doctor.fullName.firstOrNull()?.uppercase() ?: "D",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                doctor.fullName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                doctor.specialty,
                fontSize = 12.sp,
                color = TextSecondary,
                maxLines = 1,
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text("★", color = AlertAmber, fontSize = 13.sp)
                Text(ratingStr, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                if (doctor.isAvailable) {
                    Spacer(Modifier.width(4.dp))
                    Box(
                        Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(SuccessGreen),
                    )
                    Text("Disponible", fontSize = 11.sp, color = SuccessGreen)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab: Doctores
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
        // Barra de búsqueda premium
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(width = 0.dp, color = Color.Transparent),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "Doctores",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceGray)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Outlined.Search, null, tint = TextTertiary, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    "Busca por especialidad o nombre...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTertiary,
                )
            }
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else if (doctors.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🦷", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("No hay doctores disponibles", color = TextSecondary)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = onRefresh,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, TealPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary),
                    ) { Text("Reintentar") }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
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
    Column(modifier = modifier.fillMaxSize()) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWhite)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Text(
                "Mis citas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
            )
        }

        if (isLoading) {
            Box(Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else if (appointments.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("🦷", fontSize = 56.sp)
                Spacer(Modifier.height(16.dp))
                Text(
                    "Agenda tu primera cita",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Consulta desde \$20 · Pago seguro online",
                    fontSize = 14.sp,
                    color = TextSecondary,
                )
                Spacer(Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                        .clickable(onClick = onSearchDoctors),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Buscar doctor ahora", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = onOpenAiManager) {
                    Text(
                        "¿Tienes síntomas? Consulta al AI gratis →",
                        color = TealPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
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
    loyaltyPoints: Int = 0,
    onLogout: () -> Unit,
    onOpenExpediente: () -> Unit = {},
    onOpenHistorialMedico: () -> Unit = {},
    onOpenMisDatos: () -> Unit = {},
    onOpenHistorialCitas: () -> Unit = {},
    onOpenNotificaciones: () -> Unit = {},
    onOpenPrivacidad: () -> Unit = {},
    onOpenTratamientos: () -> Unit = {},
    onOpenRx: () -> Unit = {},
    onOpenSubscription: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // ── Gradient header card ───────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .padding(24.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(3.dp, White, CircleShape)
                            .background(White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = patientName.firstOrNull()?.uppercase() ?: "P",
                            color = White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        patientName.ifBlank { "Paciente" },
                        color = White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = White.copy(alpha = 0.20f),
                        ) {
                            Text(
                                "Paciente",
                                color = White,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = CoralAccent.copy(alpha = 0.85f),
                        ) {
                            Text(
                                "★ $loyaltyPoints pts",
                                color = White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            )
                        }
                    }
                }
            }
        }

        // ── Menu items ─────────────────────────────────────────────────────────
        item { Spacer(Modifier.height(4.dp)) }

        item {
            PerfilMenuCard(
                icon = Icons.Outlined.Person,
                iconColor = TealPrimary,
                titulo = "Mis datos",
                subtitulo = "Ver y editar tu perfil",
                onClick = onOpenMisDatos,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.FolderOpen,
                iconColor = Color(0xFF3B82F6),
                titulo = "Mi Expediente Dental",
                subtitulo = "Historial de procedimientos",
                onClick = onOpenExpediente,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.HealthAndSafety,
                iconColor = Color(0xFF8B5CF6),
                titulo = "Historial médico",
                subtitulo = "Condiciones sistémicas y alergias",
                badge = "IA",
                badgeColor = Color(0xFF8B5CF6),
                onClick = onOpenHistorialMedico,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.MedicalServices,
                iconColor = AlertAmber,
                titulo = "Mis tratamientos",
                subtitulo = "Endodoncia, ortodoncia, implantes…",
                onClick = onOpenTratamientos,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.Medication,
                iconColor = Color(0xFFEC4899),
                titulo = "Mis recetas",
                subtitulo = "Ver y registrar tus recetas médicas",
                onClick = onOpenRx,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.CalendarMonth,
                iconColor = SuccessGreen,
                titulo = "Historial de citas",
                subtitulo = "Ver y cancelar citas",
                onClick = onOpenHistorialCitas,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.Notifications,
                iconColor = Color(0xFFF97316),
                titulo = "Notificaciones",
                subtitulo = "Recordatorios y alertas",
                onClick = onOpenNotificaciones,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.Security,
                iconColor = TextSecondary,
                titulo = "Privacidad y seguridad",
                subtitulo = "Datos, QR compartidos y cuenta",
                onClick = onOpenPrivacidad,
            )
        }
        item {
            PerfilMenuCard(
                icon = Icons.Outlined.Star,
                iconColor = Color(0xFFF59E0B),
                titulo = "Planes y suscripción",
                subtitulo = "Free · Pro · Clínica — actualiza tu plan",
                badge = "PRO",
                badgeColor = Color(0xFFF59E0B),
                onClick = onOpenSubscription,
            )
        }

        // ── Cerrar sesión ──────────────────────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AlertRedLight)
                    .clickable(onClick = onLogout)
                    .padding(16.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Logout, null, tint = AlertRed)
                    Spacer(Modifier.width(12.dp))
                    Text("Cerrar sesión", color = AlertRed, fontWeight = FontWeight.Medium)
                }
            }
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

@Composable
private fun PerfilMenuCard(
    icon: ImageVector,
    iconColor: Color,
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit,
    badge: String? = null,
    badgeColor: Color = TealPrimary,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                    )
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
                Text(subtitulo, fontSize = 13.sp, color = TextSecondary)
            }
            Icon(Icons.Outlined.ChevronRight, null, tint = TextTertiary, modifier = Modifier.size(18.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared card components
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DoctorCard(doctor: DoctorDto, onClick: () -> Unit = {}) {
    val ratingStr = doctor.ratingPromedio?.let { "%.1f".format(it) } ?: "Nuevo"
    val nextSlot  = doctor.horarioInicio.ifBlank { "08:00" }
    val price     = when {
        doctor.consultationFee <= 0    -> "Gratis"
        doctor.consultationFee > 10000 -> "COP ${"%.0f".format(doctor.consultationFee)}"
        else                           -> "USD ${"%.0f".format(doctor.consultationFee)}"
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        doctor.fullName.firstOrNull()?.uppercase() ?: "D",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        doctor.fullName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(doctor.specialty, fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text("★", color = AlertAmber, fontSize = 12.sp)
                            Text(ratingStr, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        if (doctor.isAvailable) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(Modifier.size(7.dp).clip(CircleShape).background(SuccessGreen))
                                Text("Hoy $nextSlot", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = SurfaceGray)
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(price, fontSize = 14.sp, color = TealPrimary, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                        .clickable(onClick = onClick)
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                ) {
                    Text("Agendar →", color = White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AppointmentCard(
    appointment: AppointmentDto,
    modifier: Modifier = Modifier,
) {
    val (bgColor, statusLabel, statusColor) = when (appointment.status) {
        "confirmed" -> Triple(Color(0xFFECFDF5), "Confirmada", SuccessGreen)
        "pending"   -> Triple(AlertAmberLight,   "Pendiente",  AlertAmber)
        "completed" -> Triple(SurfaceGray,        "Completada", TextSecondary)
        "cancelled" -> Triple(AlertRedLight,      "Cancelada",  AlertRed)
        else        -> Triple(SurfaceGray,        appointment.status, TextSecondary)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(TealPrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.CalendarMonth, null, tint = TealPrimary, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    appointment.doctorName ?: "Doctor",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                )
                Text(appointment.specialty ?: "", fontSize = 12.sp, color = TextSecondary)
                Text(appointment.scheduledAt.take(10), fontSize = 12.sp, color = TextTertiary)
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = bgColor,
            ) {
                Text(
                    statusLabel,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = statusColor,
                )
            }
        }
    }
}
