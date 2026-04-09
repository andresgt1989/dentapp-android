package com.dentapp.app.ui.perfil

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dentapp.app.ui.theme.*
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// Modelo
// ─────────────────────────────────────────────────────────────────────────────

data class NotifPref(
    val id: String,
    val icon: ImageVector,
    val titulo: String,
    val descripcion: String,
    val default: Boolean,
)

private val NOTIF_PREFS = listOf(
    NotifPref("recordatorio_cita",  Icons.Outlined.CalendarToday, "Recordatorio de cita",        "24 h y 2 h antes de tu cita",                        default = true),
    NotifPref("confirmacion",       Icons.Outlined.CheckCircle,   "Confirmación de reserva",      "Cuando el doctor confirme tu cita",                   default = true),
    NotifPref("cancelacion",        Icons.Outlined.Cancel,        "Cancelaciones",                "Si tu cita es cancelada o modificada",                default = true),
    NotifPref("pago",               Icons.Outlined.Receipt,       "Confirmación de pago",         "Recibo y estado de tu pago",                          default = true),
    NotifPref("medicamentos",       Icons.Outlined.Medication,    "Recordatorio de medicamentos", "Alertas de toma de medicación postoperatoria",        default = true),
    NotifPref("ia_consejo",         Icons.Outlined.AutoAwesome,   "Consejos de tu IA dental",     "Tips personalizados según tu perfil",                 default = false),
    NotifPref("marketing",          Icons.Outlined.Campaign,      "Promociones y novedades",      "Ofertas de clínicas y nuevas funciones",              default = false),
)

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel — persiste en DataStore
// ─────────────────────────────────────────────────────────────────────────────

data class NotificacionesState(
    val enabled: Map<String, Boolean> = emptyMap(),
    val saved: Boolean = false,
)

@HiltViewModel
class NotificacionesViewModel @Inject constructor(
    private val store: TokenStore,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificacionesState())
    val state: StateFlow<NotificacionesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val flows = NOTIF_PREFS.map { pref ->
                store.getNotifPref(pref.id, pref.default).map { pref.id to it }
            }
            combine(flows) { pairs -> pairs.toMap() }
                .collect { map -> _state.update { it.copy(enabled = map) } }
        }
    }

    fun toggle(id: String, value: Boolean) {
        _state.update { it.copy(enabled = it.enabled + (id to value), saved = false) }
    }

    fun save() {
        viewModelScope.launch {
            _state.value.enabled.forEach { (id, enabled) ->
                store.saveNotifPref(id, enabled)
            }
            _state.update { it.copy(saved = true) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    onBack: () -> Unit,
    viewModel: NotificacionesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.saved) {
        if (state.saved) snackbarHostState.showSnackbar("Preferencias guardadas")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones", fontWeight = FontWeight.Bold) },
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
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryLight),
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Outlined.Info, null, tint = Primary, modifier = Modifier.size(20.dp))
                        Text(
                            "Las notificaciones críticas (citas, pagos) siempre estarán activas.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Primary,
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            items(NOTIF_PREFS, key = { it.id }) { pref ->
                val enabled = state.enabled[pref.id] ?: pref.default
                NotifRow(
                    pref = pref,
                    enabled = enabled,
                    onToggle = { viewModel.toggle(pref.id, it) },
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.save() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) {
                    Text("Guardar preferencias")
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun NotifRow(
    pref: NotifPref,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(if (enabled) PrimaryLight else DividerColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    pref.icon,
                    contentDescription = null,
                    tint = if (enabled) Primary else TextSecondary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Column(Modifier.weight(1f)) {
                Text(pref.titulo, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text(pref.descripcion, color = TextSecondary, fontSize = 12.sp)
            }
            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Primary,
                    checkedTrackColor = PrimaryLight,
                ),
            )
        }
    }
}
