package com.dentapp.app.ui.tratamiento

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// TreatmentDetailScreen — Detalle por tipo de tratamiento
// El caso de endodoncia tiene el contador de corona crítico
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentDetailScreen(
    tratamiento: TratamientoDto,
    onBack: () -> Unit,
) {
    val icon  = tratamientoIcon(tratamiento.tipo)
    val color = tratamientoColor(tratamiento.tipo)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        tratamiento.tipo.replaceFirstChar { it.uppercase() }.replace('_', ' '),
                        fontWeight = FontWeight.Bold,
                    )
                },
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
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // ── Header ────────────────────────────────────────────────────
            TratHeaderCard(tratamiento = tratamiento, icon = icon, color = color)

            // ── Detalle específico por tipo ────────────────────────────────
            when (tratamiento.tipo.lowercase()) {
                "endodoncia" -> EndodonciaDetailCard(tratamiento)
                "ortodoncia" -> OrtodonciaDetailCard(tratamiento)
                "implante"   -> ImplanteDetailCard(tratamiento)
                "periodoncia"-> PeriodonciDetailCard(tratamiento)
                "blanqueamiento" -> BlanqueamientoDetailCard(tratamiento)
                else         -> GenericDetailCard(tratamiento)
            }

            // ── Notas del doctor ──────────────────────────────────────────
            if (!tratamiento.notas.isNullOrBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 8.dp),
                        ) {
                            Icon(Icons.Outlined.Notes, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                            Text("Notas del doctor", fontWeight = FontWeight.SemiBold)
                        }
                        Text(tratamiento.notas, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Header común
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun TratHeaderCard(
    tratamiento: TratamientoDto,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(
                tratamiento.tipo.replaceFirstChar { it.uppercase() }.replace('_', ' '),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            if (!tratamiento.subtipo.isNullOrBlank()) {
                Text(tratamiento.subtipo, color = TextSecondary, fontSize = 14.sp)
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoChip(icon = Icons.Outlined.CalendarToday, label = tratamiento.fechaInicio)
                tratamiento.doctorNombre?.let {
                    InfoChip(icon = Icons.Outlined.Person, label = it)
                }
            }
        }
    }
}

@Composable
private fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = PrimaryLight,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(12.dp))
            Text(label, color = Primary, fontSize = 12.sp)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Endodoncia — contador de corona CRÍTICO
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun EndodonciaDetailCard(t: TratamientoDto) {
    // La endodoncia necesita corona protectora o el diente puede fracturarse
    // El contador de días es la alerta más importante de este módulo

    // Calcular días desde inicio (aproximación)
    val diasDesdeInicio = remember(t.fechaInicio) {
        try {
            val inicio = java.time.LocalDate.parse(t.fechaInicio)
            java.time.temporal.ChronoUnit.DAYS.between(inicio, java.time.LocalDate.now()).toInt()
        } catch (_: Exception) { 0 }
    }

    val necesitaCoronaUrgente = diasDesdeInicio > 90
    val advertenciaCoronaProxima = diasDesdeInicio in 60..90
    val coronaYaInstalada = t.faseActual?.lowercase()?.contains("corona") == true ||
                            t.status == "completado"

    // Etapas de endodoncia
    val fases = listOf(
        "Diagnóstico y Rx inicial",
        "Apertura y conductometría",
        "Conformación de conductos",
        "Obturación (sellado final)",
        "Restauración temporal",
        "Corona definitiva",
    )

    val faseIndex = when {
        t.faseActual?.contains("diagnos", ignoreCase = true) == true -> 0
        t.faseActual?.contains("apertura", ignoreCase = true) == true ||
        t.faseActual?.contains("conducto", ignoreCase = true) == true -> 1
        t.faseActual?.contains("conform", ignoreCase = true) == true -> 2
        t.faseActual?.contains("obtura", ignoreCase = true) == true -> 3
        t.faseActual?.contains("temporal", ignoreCase = true) == true -> 4
        t.faseActual?.contains("corona", ignoreCase = true) == true ||
        t.status == "completado" -> 5
        else -> 2
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // ── Alerta corona crítica ─────────────────────────────────────────
        AnimatedVisibility(!coronaYaInstalada && (necesitaCoronaUrgente || advertenciaCoronaProxima)) {
            val bgColor = if (necesitaCoronaUrgente) Error.copy(alpha = 0.1f) else Warning.copy(alpha = 0.1f)
            val textColor = if (necesitaCoronaUrgente) Error else Warning
            val borderColor = if (necesitaCoronaUrgente) Error.copy(alpha = 0.4f) else Warning.copy(alpha = 0.4f)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(
                        if (necesitaCoronaUrgente) Icons.Outlined.Error else Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(22.dp),
                    )
                    Column {
                        Text(
                            if (necesitaCoronaUrgente) "⚠️ Corona requerida URGENTE"
                            else "Corona recomendada pronto",
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontSize = 14.sp,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            if (necesitaCoronaUrgente)
                                "Han pasado $diasDesdeInicio días desde el inicio. El diente tratado con endodoncia sin corona tiene alto riesgo de fractura vertical irreparable. Agenda con tu doctor a la brevedad."
                            else
                                "Han pasado $diasDesdeInicio días. Es buen momento para planificar la corona definitiva y proteger el diente.",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (necesitaCoronaUrgente) Error else Warning,
                        )
                    }
                }
            }
        }

        // ── Progreso de fases ─────────────────────────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Fases del tratamiento", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 12.dp))
                fases.forEachIndexed { i, fase ->
                    FaseRow(
                        label = fase,
                        completada = i < faseIndex,
                        actual = i == faseIndex,
                        pendiente = i > faseIndex,
                        esCritica = i == 5 && !coronaYaInstalada, // corona
                    )
                    if (i < fases.size - 1) {
                        Box(
                            Modifier
                                .padding(start = 15.dp)
                                .width(2.dp)
                                .height(12.dp)
                                .background(if (i < faseIndex) Primary else DividerColor)
                        )
                    }
                }
            }
        }

        // ── Días sin corona ────────────────────────────────────────────────
        if (!coronaYaInstalada) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (necesitaCoronaUrgente) Error.copy(alpha = 0.1f)
                                else PrimaryLight
                            )
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "$diasDesdeInicio",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = if (necesitaCoronaUrgente) Error else Primary,
                        )
                        Text(
                            "días",
                            fontSize = 11.sp,
                            color = if (necesitaCoronaUrgente) Error else Primary,
                        )
                    }
                    Column {
                        Text("Sin corona protectora", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text(
                            "El límite recomendado es 90 días. Después el riesgo de fractura aumenta significativamente.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FaseRow(
    label: String,
    completada: Boolean,
    actual: Boolean,
    pendiente: Boolean,
    esCritica: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when {
                        completada -> Primary
                        actual -> Primary.copy(alpha = 0.15f)
                        esCritica -> Error.copy(alpha = 0.15f)
                        else -> DividerColor
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (completada) {
                Icon(Icons.Outlined.Check, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(16.dp))
            } else if (esCritica) {
                Icon(Icons.Outlined.Warning, contentDescription = null, tint = Error, modifier = Modifier.size(14.dp))
            }
        }
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = if (actual) FontWeight.SemiBold else FontWeight.Normal,
            color = when {
                completada -> TextSecondary
                actual -> Primary
                esCritica -> Error
                else -> TextSecondary
            },
        )
        if (actual) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Primary.copy(alpha = 0.12f),
            ) {
                Text(
                    "Actual",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    color = Primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Ortodoncia
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun OrtodonciaDetailCard(t: TratamientoDto) {
    val fases = listOf(
        "Evaluación y moldes", "Colocación de brackets/alineadores",
        "Ajustes y seguimiento", "Retención activa", "Retención pasiva (retenedor)",
    )
    EtapasCard(titulo = "Fases de ortodoncia", fases = fases, faseActual = t.faseActual)
    TipsCard(
        titulo = "Cuidados clave",
        tips = listOf(
            "Usar retención después del tratamiento es obligatorio para evitar recaída.",
            "Limpiar entre brackets con hilo dental o cepillo interproximal diariamente.",
            "Evitar alimentos duros o pegajosos que dañen los brackets.",
        ),
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Implante
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ImplanteDetailCard(t: TratamientoDto) {
    val fases = listOf(
        "Diagnóstico y planeación 3D", "Colocación del implante",
        "Oseointegración (2-6 meses)", "Colocación de pilar",
        "Corona definitiva", "Mantenimiento",
    )
    EtapasCard(titulo = "Etapas del implante", fases = fases, faseActual = t.faseActual)
    TipsCard(
        titulo = "Cuidados post-operatorios",
        tips = listOf(
            "No fumar durante la oseointegración — reduce el éxito del implante hasta un 20%.",
            "Mantener higiene estricta alrededor del implante con cepillo sulcular.",
            "Evitar presión en la zona del implante durante los primeros 3 meses.",
        ),
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Periodoncia
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PeriodonciDetailCard(t: TratamientoDto) {
    TipsCard(
        titulo = "Plan de mantenimiento periodontal",
        tips = listOf(
            "Limpiezas profesionales cada 3 meses (no cada 6) en paciente periodontal.",
            "El mantenimiento es de por vida — la periodontitis no se 'cura', se controla.",
            "Sondaje periodontal de control en cada visita para detectar recidiva.",
            "Control de glucemia si eres diabético — afecta directamente la respuesta periodontal.",
        ),
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Blanqueamiento
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun BlanqueamientoDetailCard(t: TratamientoDto) {
    TipsCard(
        titulo = "Cuidados post-blanqueamiento",
        tips = listOf(
            "Dieta blanca las primeras 48-72h (sin café, vino, curry, tabaco).",
            "Sensibilidad post-tratamiento es normal; usa dentífrico para dientes sensibles.",
            "El blanqueamiento no actúa sobre coronas, carillas ni restauraciones.",
            "Retoque recomendado cada 6-12 meses según hábitos.",
        ),
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Genérico
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun GenericDetailCard(t: TratamientoDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Información del tratamiento", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            InfoRow("Fase actual", t.faseActual ?: "—")
            InfoRow("Estado", t.status)
            t.fechaEstimadaFin?.let { InfoRow("Fin estimado", it) }
            t.clinicaNombre?.let { InfoRow("Clínica", it) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helpers compartidos
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun EtapasCard(titulo: String, fases: List<String>, faseActual: String?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 12.dp))
            fases.forEachIndexed { i, fase ->
                val esActual = faseActual?.contains(fase.take(6), ignoreCase = true) == true
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(vertical = 3.dp),
                ) {
                    Box(
                        Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (esActual) Primary else DividerColor),
                    )
                    Text(
                        fase,
                        fontSize = 13.sp,
                        fontWeight = if (esActual) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (esActual) Primary else TextSecondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun TipsCard(titulo: String, tips: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(bottom = 10.dp),
            ) {
                Icon(Icons.Outlined.Lightbulb, contentDescription = null, tint = Warning, modifier = Modifier.size(18.dp))
                Text(titulo, fontWeight = FontWeight.SemiBold)
            }
            tips.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("•", color = Primary, fontWeight = FontWeight.Bold)
                    Text(tip, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 13.sp)
    }
}
