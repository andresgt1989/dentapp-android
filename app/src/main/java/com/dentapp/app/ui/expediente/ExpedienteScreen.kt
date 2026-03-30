package com.dentapp.app.ui.expediente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.data.model.DentalRecordDto
import com.dentapp.app.ui.theme.*

// ── Colores por tipo de procedimiento ─────────────────────────────────────────

private fun procedimientoColor(procedimiento: String): Color {
    val lower = procedimiento.lowercase()
    return when {
        lower.contains("extraccion") || lower.contains("extracción") -> Color(0xFFD32F2F)
        lower.contains("ortodoncia") || lower.contains("braces") -> Color(0xFF1565C0)
        lower.contains("limpieza") || lower.contains("profilaxis") -> Color(0xFF2E7D32)
        lower.contains("endodoncia") || lower.contains("conducto") -> Color(0xFFE65100)
        lower.contains("blanqueamiento") -> Color(0xFF6A1B9A)
        lower.contains("implante") -> Color(0xFF0288D1)
        lower.contains("corona") || lower.contains("protesis") || lower.contains("prótesis") -> Color(0xFF00695C)
        else -> Color(0xFF546E7A)
    }
}

// ── ExpedienteScreen ──────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpedienteScreen(
    onBack: () -> Unit,
    viewModel: ExpedienteViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    // Agrupar registros por año-mes
    val recordsGrouped = remember(state.records) {
        state.records
            .sortedByDescending { it.fecha }
            .groupBy { record ->
                record.fecha.take(7) // "2025-03"
            }
            .toSortedMap(compareByDescending { it })
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Folder,
                            null,
                            tint = White,
                            modifier = Modifier.size(22.dp),
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Mi Expediente Dental",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = White,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Volver", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary),
                actions = {
                    TextButton(
                        onClick = { /* Snackbar handled below */ },
                        colors = ButtonDefaults.textButtonColors(contentColor = White),
                    ) {
                        Text("Exportar PDF", style = MaterialTheme.typography.labelMedium)
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    // Función disponible pronto — mostrar snackbar
                },
                containerColor = Primary,
                contentColor = White,
                icon = { Text("📄") },
                text = { Text("Exportar PDF") },
            )
        },
        containerColor = Background,
    ) { padding ->

        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Primary)
            }
        } else if (state.records.isEmpty()) {
            // Estado vacío
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp),
                ) {
                    Text("🦷", style = MaterialTheme.typography.displayLarge)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Sin registros aún",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DentTextPrimary,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Aún no tienes registros. Tu doctor los irá agregando después de cada procedimiento.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = DentTextSecond,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(24.dp))
                    OutlinedButton(
                        onClick = { viewModel.loadRecords() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                recordsGrouped.forEach { (mesAnio, records) ->
                    item(key = "header_$mesAnio") {
                        val (year, month) = mesAnio.split("-")
                        val monthName = mesAnioLabel(month.toInt())
                        Text(
                            "$monthName $year",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        )
                    }
                    items(records, key = { it.id }) { record ->
                        DentalRecordCard(record)
                    }
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

// ── DentalRecordCard ──────────────────────────────────────────────────────────

@Composable
private fun DentalRecordCard(record: DentalRecordDto) {
    val chipColor = procedimientoColor(record.procedimiento)

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Chip de tipo de procedimiento
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(chipColor.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        record.procedimiento,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = chipColor,
                    )
                }
                Text(
                    record.fecha.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = DentTextSecond,
                )
            }
            Spacer(Modifier.height(8.dp))

            // Descripción
            record.descripcion?.let { desc ->
                Text(
                    desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = DentTextPrimary,
                )
                Spacer(Modifier.height(6.dp))
            }

            // Detalles en fila
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                record.diente?.let {
                    DetailChip(label = "Diente", value = it, color = Color(0xFF546E7A))
                }
                record.clinicaNombre?.let {
                    DetailChip(label = "Clínica", value = it, color = Color(0xFF1565C0))
                }
            }

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                record.doctorName?.let {
                    Text(
                        "Dr. $it",
                        style = MaterialTheme.typography.labelSmall,
                        color = DentTextSecond,
                    )
                }
                record.costo?.let { costo ->
                    Text(
                        "$${"%.2f".format(costo)}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailChip(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.08f))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Text(
            "$label:",
            style = MaterialTheme.typography.labelSmall,
            color = color.copy(alpha = 0.7f),
        )
        Text(
            value,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = color,
        )
    }
}

private fun mesAnioLabel(month: Int): String = when (month) {
    1 -> "Enero"; 2 -> "Febrero"; 3 -> "Marzo"; 4 -> "Abril"
    5 -> "Mayo"; 6 -> "Junio"; 7 -> "Julio"; 8 -> "Agosto"
    9 -> "Septiembre"; 10 -> "Octubre"; 11 -> "Noviembre"; 12 -> "Diciembre"
    else -> "Mes $month"
}
