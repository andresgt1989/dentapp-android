package com.dentapp.app.ui.perfil

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.ui.theme.*

// ─────────────────────────────────────────────────────────────────────────────
// HistorialMedicoScreen — Perfil sistémico del paciente con badge "Alimenta tu IA"
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMedicoScreen(
    onBack: () -> Unit,
    viewModel: HistorialMedicoViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.successMsg) {
        state.successMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessages()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Historial Médico", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.guardar() },
                        enabled = !state.saving,
                    ) {
                        if (state.saving) {
                            CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                        } else {
                            Text("Guardar", color = Primary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface),
            )
        }
    ) { padding ->
        if (state.loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .background(Background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // ── Badge "Alimenta tu IA" ──────────────────────────────────────
            AiCompletnessBadge(completeness = state.completeness)

            // ── Sección: Datos generales ───────────────────────────────────
            SeccionCard(titulo = "Datos generales", icon = Icons.Outlined.Person) {
                LabeledDropdown(
                    label = "Rango de edad",
                    options = listOf("< 18", "18-30", "31-45", "46-60", "60+"),
                    selected = state.form.ageRange,
                    onSelect = { viewModel.update { copy(ageRange = it) } },
                )
                Spacer(Modifier.height(8.dp))
                LabeledDropdown(
                    label = "Frecuencia de cepillado",
                    options = listOf("1 vez/día", "2 veces/día", "3 o más veces/día"),
                    selected = when (state.form.brushingFreq) {
                        1 -> "1 vez/día"; 2 -> "2 veces/día"; else -> "3 o más veces/día"
                    },
                    onSelect = {
                        viewModel.update {
                            copy(brushingFreq = when (it) {
                                "1 vez/día" -> 1; "2 veces/día" -> 2; else -> 3
                            })
                        }
                    },
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    SwitchField(
                        label = "Usa hilo dental",
                        checked = state.form.usesFloss == true,
                        onChecked = { viewModel.update { copy(usesFloss = it) } },
                        modifier = Modifier.weight(1f),
                    )
                    SwitchField(
                        label = "Usa enjuague",
                        checked = state.form.usesMouthwash == true,
                        onChecked = { viewModel.update { copy(usesMouthwash = it) } },
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Ansiedad dental",
                    checked = state.form.dentalAnxiety == true,
                    onChecked = { viewModel.update { copy(dentalAnxiety = it) } },
                )
            }

            // ── Sección: Embarazo ──────────────────────────────────────────
            SeccionCard(titulo = "Embarazo", icon = Icons.Outlined.FavoriteBorder) {
                LabeledDropdown(
                    label = "Estado",
                    options = listOf("No aplica", "Sí, embarazada", "Desconoce"),
                    selected = when (state.form.pregnancyStatus) {
                        "yes" -> "Sí, embarazada"
                        "unknown" -> "Desconoce"
                        else -> "No aplica"
                    },
                    onSelect = {
                        viewModel.update {
                            copy(pregnancyStatus = when (it) {
                                "Sí, embarazada" -> "yes"
                                "Desconoce" -> "unknown"
                                else -> "no"
                            })
                        }
                    },
                )
                AnimatedVisibility(state.form.pregnancyStatus == "yes") {
                    Column(Modifier.padding(top = 8.dp)) {
                        LabeledDropdown(
                            label = "Trimestre",
                            options = listOf("1er trimestre", "2do trimestre", "3er trimestre"),
                            selected = when (state.form.trimester) {
                                1 -> "1er trimestre"; 2 -> "2do trimestre"; else -> "3er trimestre"
                            },
                            onSelect = {
                                viewModel.update {
                                    copy(trimester = when (it) {
                                        "1er trimestre" -> 1; "2do trimestre" -> 2; else -> 3
                                    })
                                }
                            },
                        )
                    }
                }
            }

            // ── Sección: Cardíaco ──────────────────────────────────────────
            SeccionCard(titulo = "Condición cardíaca", icon = Icons.Outlined.MonitorHeart) {
                SwitchField(
                    label = "Tiene condición cardíaca",
                    checked = state.form.cardiacCondition == true,
                    onChecked = { viewModel.update { copy(cardiacCondition = it) } },
                )
                AnimatedVisibility(state.form.cardiacCondition == true) {
                    Column(Modifier.padding(top = 8.dp)) {
                        OutlinedTextField(
                            value = state.form.cardiacDetail ?: "",
                            onValueChange = { viewModel.update { copy(cardiacDetail = it) } },
                            label = { Text("Descripción (ej. arritmia, válvula)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Tiene marcapasos",
                    checked = state.form.pacemaker == true,
                    onChecked = { viewModel.update { copy(pacemaker = it) } },
                )
            }

            // ── Sección: Bifosfonatos ──────────────────────────────────────
            SeccionCard(titulo = "Bifosfonatos", icon = Icons.Outlined.Medication) {
                BifosfonatosRow(
                    checked = state.form.takesBisphosphonates == true,
                    onChecked = { viewModel.update { copy(takesBisphosphonates = it) } },
                    name = state.form.bisphosphonateName ?: "",
                    onNameChange = { viewModel.update { copy(bisphosphonateName = it) } },
                    route = state.form.bisphosphonateRoute,
                    onRouteChange = { viewModel.update { copy(bisphosphonateRoute = it) } },
                )
            }

            // ── Sección: Renal / Hepático ──────────────────────────────────
            SeccionCard(titulo = "Función renal y hepática", icon = Icons.Outlined.Science) {
                SwitchField(
                    label = "Insuficiencia renal",
                    checked = state.form.renalInsufficiency == true,
                    onChecked = { viewModel.update { copy(renalInsufficiency = it) } },
                )
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Insuficiencia hepática",
                    checked = state.form.hepaticInsufficiency == true,
                    onChecked = { viewModel.update { copy(hepaticInsufficiency = it) } },
                )
            }

            // ── Sección: Hematológico ──────────────────────────────────────
            SeccionCard(titulo = "Hematológico", icon = Icons.Outlined.Bloodtype) {
                SwitchField(
                    label = "Hemofilia u otras coagulopatías",
                    checked = state.form.hemophilia == true,
                    onChecked = { viewModel.update { copy(hemophilia = it) } },
                )
            }

            // ── Sección: VIH ───────────────────────────────────────────────
            SeccionCard(titulo = "Estado VIH", icon = Icons.Outlined.HealthAndSafety) {
                Text(
                    "Esta información es confidencial y solo visible para tu médico.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Diagnóstico positivo VIH",
                    checked = state.form.hivStatus == true,
                    onChecked = { viewModel.update { copy(hivStatus = it) } },
                )
            }

            // ── Sección: Oncológico ────────────────────────────────────────
            SeccionCard(titulo = "Tratamiento oncológico", icon = Icons.Outlined.LocalHospital) {
                SwitchField(
                    label = "Tratamiento oncológico activo",
                    checked = state.form.oncologyActive == true,
                    onChecked = { viewModel.update { copy(oncologyActive = it) } },
                )
                AnimatedVisibility(state.form.oncologyActive == true) {
                    Column(Modifier.padding(top = 8.dp)) {
                        OutlinedTextField(
                            value = state.form.oncologyType ?: "",
                            onValueChange = { viewModel.update { copy(oncologyType = it) } },
                            label = { Text("Tipo de cáncer / tratamiento") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        )
                    }
                }
            }

            // ── Sección: Neurológico / GI ──────────────────────────────────
            SeccionCard(titulo = "Neurológico / Gastrointestinal", icon = Icons.Outlined.Psychology) {
                SwitchField(
                    label = "Epilepsia",
                    checked = state.form.epilepsy == true,
                    onChecked = { viewModel.update { copy(epilepsy = it) } },
                )
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Trastorno alimentario (anorexia / bulimia)",
                    checked = state.form.eatingDisorder == true,
                    onChecked = { viewModel.update { copy(eatingDisorder = it) } },
                )
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "ERGE / Reflujo gastroesofágico",
                    checked = state.form.gerd == true,
                    onChecked = { viewModel.update { copy(gerd = it) } },
                )
                Spacer(Modifier.height(8.dp))
                SwitchField(
                    label = "Síndrome de Sjögren",
                    checked = state.form.sjogren == true,
                    onChecked = { viewModel.update { copy(sjogren = it) } },
                )
            }

            // ── Sección: Medicamentos sistémicos ───────────────────────────
            SeccionCard(titulo = "Medicamentos sistémicos", icon = Icons.Outlined.MedicationLiquid) {
                Text(
                    "Lista los medicamentos que tomas actualmente (separados por coma).",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.form.systemicMeds ?: "",
                    onValueChange = { viewModel.update { copy(systemicMeds = it) } },
                    label = { Text("Medicamentos") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 5,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    placeholder = { Text("Ej. Metformina, Atorvastatina, Levotiroxina…") },
                )
            }

            // ── Sección: Hábitos tabáquicos ────────────────────────────────
            SeccionCard(titulo = "Hábitos tabáquicos", icon = Icons.Outlined.SmokingRooms) {
                LabeledDropdown(
                    label = "Tipo",
                    options = listOf("No fumador", "Cigarro", "Vaporizador", "Otros"),
                    selected = when (state.form.tobaccoType) {
                        "cigarette" -> "Cigarro"
                        "vape" -> "Vaporizador"
                        "other" -> "Otros"
                        else -> "No fumador"
                    },
                    onSelect = {
                        viewModel.update {
                            copy(tobaccoType = when (it) {
                                "Cigarro" -> "cigarette"
                                "Vaporizador" -> "vape"
                                "Otros" -> "other"
                                else -> "none"
                            })
                        }
                    },
                )
                AnimatedVisibility(state.form.tobaccoType != null && state.form.tobaccoType != "none") {
                    Column(Modifier.padding(top = 8.dp)) {
                        LabeledDropdown(
                            label = "Frecuencia",
                            options = listOf("Diario", "Ocasional", "Lo dejé"),
                            selected = when (state.form.tobaccoFreq) {
                                "daily" -> "Diario"; "occasional" -> "Ocasional"; else -> "Lo dejé"
                            },
                            onSelect = {
                                viewModel.update {
                                    copy(tobaccoFreq = when (it) {
                                        "Diario" -> "daily"; "Ocasional" -> "occasional"; else -> "quit"
                                    })
                                }
                            },
                        )
                    }
                }
            }

            // Padding inferior para el scroll
            Spacer(Modifier.height(32.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Composables reutilizables internos
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AiCompletnessBadge(completeness: Int) {
    val color = when {
        completeness >= 80 -> Success
        completeness >= 40 -> Warning
        else -> Error
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A237E).copy(alpha = 0.08f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A237E).copy(alpha = 0.2f)),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Círculo de progreso
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { completeness / 100f },
                    modifier = Modifier.size(56.dp),
                    color = color,
                    strokeWidth = 5.dp,
                    trackColor = DividerColor,
                )
                Text(
                    "$completeness%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = color,
                )
            }
            Column(Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF5C6BC0),
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        "Alimenta tu IA",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E),
                        fontSize = 14.sp,
                    )
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    when {
                        completeness >= 80 -> "Perfil completo. Tu IA tiene contexto sistémico óptimo."
                        completeness >= 40 -> "Completa más campos para que tu IA te ayude mejor."
                        else -> "Tu IA necesita información médica para darte consejos precisos."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
    }
}

@Composable
private fun SeccionCard(
    titulo: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit,
) {
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
                modifier = Modifier.padding(bottom = 12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(PrimaryLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                }
                Text(titulo, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            }
            content()
        }
    }
}

@Composable
private fun SwitchField(
    label: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = checked,
            onCheckedChange = onChecked,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Primary,
                checkedTrackColor = PrimaryLight,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledDropdown(
    label: String,
    options: List<String>,
    selected: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = { onSelect(option); expanded = false },
                )
            }
        }
    }
}

@Composable
private fun BifosfonatosRow(
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    route: String?,
    onRouteChange: (String) -> Unit,
) {
    Column {
        Text(
            "Los bifosfonatos aumentan el riesgo de osteonecrosis en procedimientos orales.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
        )
        Spacer(Modifier.height(8.dp))
        SwitchField(
            label = "Toma bifosfonatos",
            checked = checked,
            onChecked = onChecked,
        )
        AnimatedVisibility(checked) {
            Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Nombre del medicamento") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ej. Alendronato, Zoledrónico…") },
                )
                LabeledDropdown(
                    label = "Vía de administración",
                    options = listOf("Oral", "Intravenosa"),
                    selected = when (route) { "iv" -> "Intravenosa"; else -> "Oral" },
                    onSelect = { onRouteChange(if (it == "Intravenosa") "iv" else "oral") },
                )
            }
        }
    }
}
