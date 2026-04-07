package com.dentapp.app.ui.tratamiento

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// Models locales (los DTOs pueden moverse a Models.kt si se desea)
// ─────────────────────────────────────────────────────────────────────────────

@Serializable
data class TratamientoDto(
    val id: String,
    val tipo: String,
    val subtipo: String? = null,
    @SerialName("fecha_inicio") val fechaInicio: String,
    @SerialName("fecha_estimada_fin") val fechaEstimadaFin: String? = null,
    @SerialName("doctor_nombre") val doctorNombre: String? = null,
    @SerialName("clinica_nombre") val clinicaNombre: String? = null,
    @SerialName("fase_actual") val faseActual: String? = null,
    val status: String = "activo",
    val notas: String? = null,
)

@Serializable
data class TratamientosResponse(val tratamientos: List<TratamientoDto>)

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

data class TreatmentState(
    val loading: Boolean = true,
    val tratamientos: List<TratamientoDto> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class TreatmentTrackingViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(TreatmentState())
    val state: StateFlow<TreatmentState> = _state.asStateFlow()

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                // El endpoint será GET /api/patients/me/tratamientos
                // Por ahora usamos datos simulados si el endpoint no existe aún
                val resp = api.getTratamientos()
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(
                        loading = false,
                        tratamientos = resp.body()?.tratamientos ?: emptyList(),
                    )
                } else {
                    _state.value = _state.value.copy(loading = false, error = "Error ${resp.code()}")
                }
            } catch (_: Exception) {
                // Endpoint puede no existir aún; mostrar vacío sin error
                _state.value = _state.value.copy(loading = false, tratamientos = emptyList())
            }
        }
    }

    fun clearError() { _state.value = _state.value.copy(error = null) }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentTrackingScreen(
    onBack: () -> Unit,
    onVerDetalle: (TratamientoDto) -> Unit,
    viewModel: TreatmentTrackingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearError() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mis Tratamientos", fontWeight = FontWeight.Bold) },
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

        if (state.tratamientos.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.MedicalServices,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TextSecondary,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("No tienes tratamientos activos", color = TextSecondary)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Tus tratamientos aparecerán aquí\ncuando tu doctor los registre.",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.tratamientos, key = { it.id }) { t ->
                TratamientoCard(tratamiento = t, onClick = { onVerDetalle(t) })
            }
        }
    }
}

@Composable
fun TratamientoCard(
    tratamiento: TratamientoDto,
    onClick: () -> Unit,
) {
    val icon = tratamientoIcon(tratamiento.tipo)
    val color = tratamientoColor(tratamiento.tipo)
    val statusColor = when (tratamiento.status) {
        "activo" -> Success; "completado" -> Primary; "pausado" -> Warning; else -> TextSecondary
    }
    val statusLabel = when (tratamiento.status) {
        "activo" -> "Activo"; "completado" -> "Completado"; "pausado" -> "Pausado"
        "abandonado" -> "Abandonado"; "post_alta" -> "Post-alta"; else -> tratamiento.status
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(26.dp))
            }
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        tratamiento.tipo.replaceFirstChar { it.uppercase() }.replace('_', ' '),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                    )
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = statusColor.copy(alpha = 0.12f),
                    ) {
                        Text(
                            statusLabel,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            color = statusColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                if (!tratamiento.faseActual.isNullOrBlank()) {
                    Text(tratamiento.faseActual, color = TextSecondary, fontSize = 12.sp)
                }
                if (!tratamiento.doctorNombre.isNullOrBlank()) {
                    Text(tratamiento.doctorNombre, color = TextSecondary, fontSize = 12.sp)
                }
                Spacer(Modifier.height(4.dp))
                Text("Inicio: ${tratamiento.fechaInicio}", color = TextSecondary, fontSize = 11.sp)
            }
            Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = TextSecondary)
        }
    }
}

internal fun tratamientoIcon(tipo: String): ImageVector = when (tipo.lowercase()) {
    "endodoncia" -> Icons.Outlined.MedicalServices
    "ortodoncia" -> Icons.Outlined.Straighten
    "implante" -> Icons.Outlined.Construction
    "periodoncia" -> Icons.Outlined.Healing
    "cirugia_oral", "cirugía_oral" -> Icons.Outlined.LocalHospital
    "corona", "puente", "carilla" -> Icons.Outlined.Stars
    "blanqueamiento" -> Icons.Outlined.AutoAwesome
    "all_on_4", "all_on_6" -> Icons.Outlined.ViewModule
    "bruxismo", "atm" -> Icons.Outlined.Psychology
    "protesis_fija", "protesis_removible", "prótesis" -> Icons.Outlined.Build
    else -> Icons.Outlined.MedicalServices
}

internal fun tratamientoColor(tipo: String): Color = when (tipo.lowercase()) {
    "endodoncia" -> Color(0xFFE65100)
    "ortodoncia" -> Color(0xFF1565C0)
    "implante" -> Color(0xFF0288D1)
    "periodoncia" -> Color(0xFF2E7D32)
    "cirugia_oral", "cirugía_oral" -> Color(0xFFD32F2F)
    "corona", "puente" -> Color(0xFF00695C)
    "blanqueamiento" -> Color(0xFF6A1B9A)
    "bruxismo", "atm" -> Color(0xFF4527A0)
    else -> Primary
}
