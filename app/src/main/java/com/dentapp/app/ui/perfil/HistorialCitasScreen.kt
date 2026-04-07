package com.dentapp.app.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.AppointmentDto
import com.dentapp.app.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

data class HistorialCitasState(
    val loading: Boolean = true,
    val appointments: List<AppointmentDto> = emptyList(),
    val canceling: String? = null,         // id siendo cancelado
    val error: String? = null,
    val successMsg: String? = null,
)

@HiltViewModel
class HistorialCitasViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(HistorialCitasState())
    val state: StateFlow<HistorialCitasState> = _state.asStateFlow()

    init { cargar() }

    private fun cargar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val resp = api.getPatientAppointments()
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(
                        loading = false,
                        appointments = resp.body()?.appointments ?: emptyList(),
                    )
                } else {
                    _state.value = _state.value.copy(loading = false, error = "Error ${resp.code()}")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message)
            }
        }
    }

    fun cancelar(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(canceling = id)
            try {
                val resp = api.cancelAppointment(id)
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(
                        canceling = null,
                        appointments = _state.value.appointments.map {
                            if (it.id == id) it.copy(status = "cancelled") else it
                        },
                        successMsg = "Cita cancelada",
                    )
                } else {
                    _state.value = _state.value.copy(
                        canceling = null,
                        error = "No se pudo cancelar (${resp.code()})",
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(canceling = null, error = e.message)
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(error = null, successMsg = null)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialCitasScreen(
    onBack: () -> Unit,
    viewModel: HistorialCitasViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var confirmCancelId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.successMsg) {
        state.successMsg?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }
    LaunchedEffect(state.error) {
        state.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mis Citas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface),
            )
        },
        containerColor = Background,
    ) { padding ->

        if (state.loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
            return@Scaffold
        }

        if (state.appointments.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.EventBusy,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TextSecondary,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("No tienes citas registradas", color = TextSecondary)
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.appointments, key = { it.id }) { appt ->
                CitaCard(
                    appt = appt,
                    canceling = state.canceling == appt.id,
                    onCancelar = { confirmCancelId = appt.id },
                )
            }
        }
    }

    // Dialog de confirmación de cancelación
    confirmCancelId?.let { id ->
        AlertDialog(
            onDismissRequest = { confirmCancelId = null },
            icon = { Icon(Icons.Outlined.Warning, contentDescription = null, tint = Warning) },
            title = { Text("Cancelar cita") },
            text = { Text("¿Estás seguro de que quieres cancelar esta cita? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.cancelar(id); confirmCancelId = null },
                    colors = ButtonDefaults.textButtonColors(contentColor = Error),
                ) {
                    Text("Sí, cancelar")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmCancelId = null }) { Text("No") }
            },
        )
    }
}

@Composable
private fun CitaCard(
    appt: AppointmentDto,
    canceling: Boolean,
    onCancelar: () -> Unit,
) {
    val statusColor = when (appt.status) {
        "scheduled", "confirmed" -> Primary
        "completed" -> Success
        "cancelled" -> TextSecondary
        else -> Warning
    }
    val statusLabel = when (appt.status) {
        "scheduled" -> "Programada"
        "confirmed" -> "Confirmada"
        "completed" -> "Completada"
        "cancelled" -> "Cancelada"
        else -> appt.status.replaceFirstChar { it.uppercase() }
    }

    val dateFormatted = remember(appt.scheduledAt) {
        try {
            val zdt = ZonedDateTime.parse(appt.scheduledAt)
            zdt.format(DateTimeFormatter.ofPattern("d MMM yyyy · HH:mm", Locale("es", "MX")))
        } catch (_: Exception) { appt.scheduledAt }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        appt.doctorName ?: "Dr./Dra.",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                    )
                    if (!appt.specialty.isNullOrBlank()) {
                        Text(appt.specialty, color = TextSecondary, fontSize = 13.sp)
                    }
                }
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = statusColor.copy(alpha = 0.12f),
                ) {
                    Text(
                        statusLabel,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(Icons.Outlined.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = TextSecondary)
                Text(dateFormatted, color = TextSecondary, fontSize = 13.sp)
            }
            if (!appt.notes.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(appt.notes, color = TextSecondary, fontSize = 13.sp)
            }

            // Botón cancelar — solo si está en estado cancelable y es futura
            val cancelable = appt.status in listOf("scheduled", "confirmed")
            if (cancelable) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = DividerColor)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    if (canceling) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp, color = Error)
                    } else {
                        TextButton(
                            onClick = onCancelar,
                            colors = ButtonDefaults.textButtonColors(contentColor = Error),
                        ) {
                            Icon(Icons.Outlined.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Cancelar cita", fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}
