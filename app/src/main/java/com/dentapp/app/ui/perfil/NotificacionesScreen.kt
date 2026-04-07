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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// NotificacionesScreen — Preferencias de notificación (local, sin VM extra)
// ─────────────────────────────────────────────────────────────────────────────

data class NotifPref(
    val id: String,
    val icon: ImageVector,
    val titulo: String,
    val descripcion: String,
    var enabled: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(onBack: () -> Unit) {
    val prefs = remember {
        mutableStateListOf(
            NotifPref(
                "recordatorio_cita", Icons.Outlined.CalendarToday,
                "Recordatorio de cita",
                "24 h y 2 h antes de tu cita",
                enabled = true,
            ),
            NotifPref(
                "confirmacion", Icons.Outlined.CheckCircle,
                "Confirmación de reserva",
                "Cuando el doctor confirme tu cita",
                enabled = true,
            ),
            NotifPref(
                "cancelacion", Icons.Outlined.Cancel,
                "Cancelaciones",
                "Si tu cita es cancelada o modificada",
                enabled = true,
            ),
            NotifPref(
                "pago", Icons.Outlined.Receipt,
                "Confirmación de pago",
                "Recibo y estado de tu pago",
                enabled = true,
            ),
            NotifPref(
                "medicamentos", Icons.Outlined.Medication,
                "Recordatorio de medicamentos",
                "Alertas de toma de medicación postoperatoria",
                enabled = true,
            ),
            NotifPref(
                "ia_consejo", Icons.Outlined.AutoAwesome,
                "Consejos de tu IA dental",
                "Tips personalizados según tu perfil",
                enabled = false,
            ),
            NotifPref(
                "marketing", Icons.Outlined.Campaign,
                "Promociones y novedades",
                "Ofertas de clínicas y nuevas funciones",
                enabled = false,
            ),
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }

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
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp),
                        )
                        Text(
                            "Las notificaciones críticas (citas, pagos) siempre estarán activas.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Primary,
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            items(prefs, key = { it.id }) { pref ->
                val index = prefs.indexOf(pref)
                NotifRow(
                    pref = pref,
                    onToggle = { prefs[index] = pref.copy(enabled = it) },
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: persistir en SharedPreferences / API */ },
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
                    .background(if (pref.enabled) PrimaryLight else DividerColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    pref.icon,
                    contentDescription = null,
                    tint = if (pref.enabled) Primary else TextSecondary,
                    modifier = Modifier.size(20.dp),
                )
            }
            Column(Modifier.weight(1f)) {
                Text(pref.titulo, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text(pref.descripcion, color = TextSecondary, fontSize = 12.sp)
            }
            Switch(
                checked = pref.enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Primary,
                    checkedTrackColor = PrimaryLight,
                ),
            )
        }
    }
}
