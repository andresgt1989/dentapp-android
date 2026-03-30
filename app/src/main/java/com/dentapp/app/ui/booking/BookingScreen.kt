package com.dentapp.app.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.ui.theme.*

// ── Modelo de opción de consulta ──────────────────────────────────────────────

private data class ConsultaOption(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precio: String,
    val duracion: String,
    val modalidad: String, // "video" | "presencial" | "chat"
    val icon: String,
    val color: Color,
)

private val OPCIONES_CONSULTA = listOf(
    ConsultaOption("video_15", "Orientación Rápida", "Video 15 min — orientación dental", "$8", "15 min", "video", "🎥", Color(0xFF1565C0)),
    ConsultaOption("video_30", "Consulta Completa", "Video 30 min — consulta dental", "$18", "30 min", "video", "📹", Color(0xFF0288D1)),
    ConsultaOption("video_45", "Segunda Opinión", "Video 45 min — análisis profundo", "$28", "45 min", "video", "🔬", Color(0xFF00695C)),
    ConsultaOption("presencial", "Cita Presencial", "En la clínica del doctor", "Precio doctor", "1h", "presencial", "🏥", Color(0xFF2E7D32)),
    ConsultaOption("chat_doctor", "Chat con Doctor", "Respuesta en <2h por doctor real", "$5", "2h", "chat", "💬", Color(0xFF6A1B9A)),
)

private val HORARIOS = listOf("08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
    "14:00", "15:00", "16:00", "17:00", "18:00")

private val DIAS_SEMANA = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

// ── BookingScreen ─────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    doctorId: String,
    doctorName: String,
    onBack: () -> Unit,
    onBookingComplete: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedOption by remember { mutableStateOf<ConsultaOption?>(null) }
    var selectedFecha by remember { mutableStateOf<String?>(null) }
    var selectedHora by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            showSuccessDialog = true
        }
    }

    // Generar fechas de la próxima semana
    val fechasDisponibles = remember {
        val hoy = java.util.Calendar.getInstance()
        (1..7).map { i ->
            val cal = java.util.Calendar.getInstance()
            cal.add(java.util.Calendar.DAY_OF_YEAR, i)
            val day = DIAS_SEMANA[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1]
            val dayNum = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val month = cal.get(java.util.Calendar.MONTH) + 1
            val year = cal.get(java.util.Calendar.YEAR)
            Triple(
                "$day $dayNum",
                "%04d-%02d-%02d".format(year, month, dayNum),
                dayNum
            )
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            icon = { Text("✅", style = MaterialTheme.typography.displaySmall) },
            title = { Text("¡Reservado!", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Tu cita con Dr. $doctorName ha sido confirmada.")
                    state.reservaId?.let {
                        Spacer(Modifier.height(6.dp))
                        Text("Reserva: $it", style = MaterialTheme.typography.labelSmall, color = DentTextSecond)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    showSuccessDialog = false
                    viewModel.resetSuccess()
                    onBookingComplete()
                }) {
                    Text("Aceptar")
                }
            },
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Reservar cita",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = White,
                        )
                        Text(
                            "Dr. $doctorName",
                            style = MaterialTheme.typography.labelSmall,
                            color = White.copy(alpha = 0.8f),
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Volver", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary),
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp, color = White) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(),
                ) {
                    Button(
                        onClick = {
                            val opt = selectedOption
                            val fecha = selectedFecha
                            val hora = selectedHora
                            if (opt != null && fecha != null && hora != null) {
                                viewModel.createBooking(
                                    doctorId = doctorId,
                                    typeId = opt.id,
                                    modalidad = opt.modalidad,
                                    fecha = fecha,
                                    hora = hora,
                                )
                            }
                        },
                        enabled = selectedOption != null && selectedFecha != null && selectedHora != null && !state.isLoading,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = White,
                                strokeWidth = 2.dp,
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                        Text(
                            if (state.isLoading) "Reservando..." else "Confirmar reserva",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        },
        containerColor = Background,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Título sección tipo de consulta
            item {
                Text(
                    "Tipo de consulta",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = DentTextPrimary,
                )
            }

            // Lista de opciones
            items(OPCIONES_CONSULTA) { option ->
                ConsultaOptionCard(
                    option = option,
                    isSelected = selectedOption?.id == option.id,
                    onClick = {
                        selectedOption = option
                        selectedFecha = null
                        selectedHora = null
                    },
                )
            }

            // Selector de fecha
            if (selectedOption != null) {
                item {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Selecciona una fecha",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = DentTextPrimary,
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(fechasDisponibles) { (label, isoDate, _) ->
                            val isSelected = selectedFecha == isoDate
                            Card(
                                onClick = {
                                    selectedFecha = isoDate
                                    selectedHora = null
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Primary else White,
                                ),
                                elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp),
                                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE0E0E0)),
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        label,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) White else DentTextPrimary,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Grid de horarios
            if (selectedFecha != null) {
                item {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Selecciona un horario",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = DentTextPrimary,
                    )
                    Spacer(Modifier.height(12.dp))
                    // Grid 3 columnas
                    val chunked = HORARIOS.chunked(3)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        chunked.forEach { row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                row.forEach { hora ->
                                    val isSelected = selectedHora == hora
                                    Card(
                                        onClick = { selectedHora = hora },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isSelected) Primary else White,
                                        ),
                                        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp),
                                        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE0E0E0)),
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp),
                                        ) {
                                            Text(
                                                hora,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium,
                                                color = if (isSelected) White else DentTextPrimary,
                                            )
                                        }
                                    }
                                }
                                // Rellenar columnas vacías si la fila tiene menos de 3
                                repeat(3 - row.size) {
                                    Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            // Resumen selección
            if (selectedOption != null && selectedFecha != null && selectedHora != null) {
                item {
                    Spacer(Modifier.height(4.dp))
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        border = BorderStroke(1.dp, Color(0xFF2E7D32)),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Icon(Icons.Outlined.CheckCircle, null, tint = Color(0xFF2E7D32), modifier = Modifier.size(24.dp))
                            Column {
                                Text(
                                    selectedOption!!.titulo,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DentTextPrimary,
                                )
                                Text(
                                    "$selectedFecha a las $selectedHora — ${selectedOption!!.precio}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DentTextSecond,
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

// ── ConsultaOptionCard ────────────────────────────────────────────────────────

@Composable
private fun ConsultaOptionCard(
    option: ConsultaOption,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) option.color.copy(alpha = 0.07f) else White,
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) option.color else Color(0xFFE0E0E0),
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(option.color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(option.icon, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    option.titulo,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = DentTextPrimary,
                )
                Text(
                    option.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = DentTextSecond,
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = option.color.copy(alpha = 0.12f),
                    ) {
                        Text(
                            option.precio,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = option.color,
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFF0F4F8),
                    ) {
                        Text(
                            option.duracion,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = DentTextSecond,
                        )
                    }
                }
            }
            if (isSelected) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    null,
                    tint = option.color,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}
