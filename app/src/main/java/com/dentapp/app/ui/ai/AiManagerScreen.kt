package com.dentapp.app.ui.ai

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.data.model.*
import com.dentapp.app.ui.theme.*

// ── Colores del chat ──────────────────────────────────────────────────────────
private val BubbleAi     = Color(0xFFEFF6FF)   // azul muy claro — mensajes AI
private val BubbleUser   = Color(0xFF2563EB)   // azul — mensajes usuario
private val BubbleAiText = Color(0xFF1E3A5F)
private val SurfaceCard  = Color(0xFFF8FAFC)

private const val PREFS_AI = "ai_prefs"
private const val KEY_DISCLAIMER_ACCEPTED = "disclaimer_accepted"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiManagerScreen(
    onBack: () -> Unit,
    viewModel: AiManagerViewModel = hiltViewModel(),
) {
    val context   = LocalContext.current
    val prefs     = remember { context.getSharedPreferences(PREFS_AI, Context.MODE_PRIVATE) }
    var showDisclaimer by remember {
        mutableStateOf(!prefs.getBoolean(KEY_DISCLAIMER_ACCEPTED, false))
    }

    if (showDisclaimer) {
        MedicalDisclaimerDialog(onAccept = {
            prefs.edit().putBoolean(KEY_DISCLAIMER_ACCEPTED, true).apply()
            showDisclaimer = false
        })
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var inputText by remember { mutableStateOf("") }
    var localSending by remember { mutableStateOf(false) }
    var showMeds by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSending) {
        if (!state.isSending) localSending = false
    }

    val handleSend: () -> Unit = {
        if (!localSending && inputText.isNotBlank()) {
            localSending = true
            viewModel.sendMessage(inputText.trim())
            inputText = ""
        }
    }

    // Auto-scroll al último mensaje
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🦷", fontSize = 18.sp)
                        }
                        Column {
                            Text("DentApp AI™", style = MaterialTheme.typography.titleMedium, color = White, fontWeight = FontWeight.Bold)
                            Text("Asistente dental personal",
                                style = MaterialTheme.typography.labelSmall,
                                color = White.copy(alpha = 0.8f))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Volver", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DentBlue),
            )
        },
        bottomBar = {
            Column {
                QuickReplies(
                    isSending = localSending || state.isSending,
                    onSelect = { msg ->
                        if (!localSending) {
                            localSending = true
                            viewModel.sendMessage(msg)
                        }
                    },
                )
                ChatInput(
                    text = inputText,
                    onTextChange = { inputText = it },
                    isSending = localSending || state.isSending,
                    onSend = handleSend,
                )
            }
        },
        containerColor = Color(0xFFF1F5F9),
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {

            // ── Banner alerta clínica (CRITICA = rojo, ALTA = amarillo) ─────────
            val topAlert = state.pendingAlerts.firstOrNull()
            if (topAlert != null) {
                val isCritical = topAlert.prioridad == "CRITICA"
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (isCritical) Color(0xFFFFEBEE) else Color(0xFFFFFDE7),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(if (isCritical) "🚨" else "⚠️", fontSize = 16.sp)
                        Text(
                            topAlert.mensaje,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isCritical) Color(0xFFB71C1C) else Color(0xFFF57F17),
                            modifier = Modifier.weight(1f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

            // ── Sección medicamentos del día (colapsable) ─────────────────────
            val activeMeds = state.context?.medicacion?.filter { !it.completado } ?: emptyList()
            if (activeMeds.isNotEmpty()) {
                Surface(color = Color.White) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showMeds = !showMeds }
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text("💊", fontSize = 14.sp)
                                Text(
                                    "Mis medicamentos de hoy",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Icon(
                                if (showMeds) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                                contentDescription = null,
                                tint = DentBlue,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                        AnimatedVisibility(visible = showMeds) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp).padding(bottom = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                activeMeds.forEach { med ->
                                    val medId = med.id
                                    val yaConfirmado = medId != null && state.confirmedMedIds.contains(medId)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (yaConfirmado) Color(0xFFE8F5E9) else Color(0xFFF8FAFC))
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Column(Modifier.weight(1f)) {
                                            Text(
                                                med.medicamento,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                            Text(
                                                "${med.dosis} · c/${med.frecuenciaHrs}h · ${med.adherenciaPct}% adherencia",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = DentTextSecond,
                                            )
                                        }
                                        if (medId != null) {
                                            IconButton(
                                                onClick = { if (!yaConfirmado) viewModel.confirmMedication(medId) },
                                                modifier = Modifier.size(36.dp),
                                            ) {
                                                Icon(
                                                    if (yaConfirmado) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                                    contentDescription = "Confirmar toma",
                                                    tint = if (yaConfirmado) DentGreen else DentTextSecond,
                                                    modifier = Modifier.size(22.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(thickness = 0.5.dp)
            }

            // ── Tarjeta de contexto (colapsa cuando hay mensajes) ──────────────
            AnimatedVisibility(visible = state.messages.isEmpty() && state.context != null) {
                state.context?.let { ContextCard(it) }
            }

            // ── Lista de mensajes ──────────────────────────────────────────────
            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DentBlue)
                }
            } else if (state.messages.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.messages, key = { "${it.role}_${it.createdAt}_${it.content.take(10)}" }) { msg ->
                        MessageBubble(msg)
                        if (msg.role == "assistant") {
                            FeedbackRow(
                                onUtil  = { viewModel.submitFeedback(null, true,  5) },
                                onNoUtil = { viewModel.submitFeedback(null, false, 2) },
                            )
                        }
                    }
                    if (state.isSending) {
                        item { TypingIndicator() }
                    }
                }
                state.lastFeedbackPoints?.let { pts ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd,
                    ) {
                        Text(
                            "+$pts pts ✨",
                            style = MaterialTheme.typography.labelSmall,
                            color = DentGreen,
                            modifier = Modifier.padding(end = 20.dp, bottom = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

// ── Tarjeta de contexto clínico ──────────────────────────────────────────────
@Composable
private fun ContextCard(ctx: AiContextResponse) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🦷", fontSize = 22.sp)
                Column {
                    Text("Recuperación post-cirugía", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("Día ${ctx.diasPostCirugia} de 30", style = MaterialTheme.typography.bodySmall, color = DentBlue)
                }
            }

            LinearProgressIndicator(
                progress = { ctx.diasPostCirugia / 30f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = DentBlue,
                trackColor = Color(0xFFE2E8F0),
            )

            ctx.medicacion.filter { !it.completado }.forEach { med ->
                Row(
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (med.adherenciaPct < 50) Color(0xFFFFF3CD) else Color(0xFFEFF6FF))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(if (med.adherenciaPct < 50) "⚠️" else "💊", fontSize = 14.sp)
                        Text(med.medicamento.take(28), style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("c/${med.frecuenciaHrs}h", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }

            if (ctx.perfil.tabaco == "vape") {
                Row(
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFEE2E2))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("🚭", fontSize = 14.sp)
                    Text("Vapear daña tu cicatrización — habla con el AI", style = MaterialTheme.typography.labelMedium, color = Color(0xFF991B1B))
                }
            }

            Text("Pregúntame cualquier cosa 👇", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

// ── Burbuja de mensaje ────────────────────────────────────────────────────────
@Composable
private fun MessageBubble(msg: AiMessage) {
    val isUser = msg.role == "user"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    ) {
        if (!isUser) {
            Box(
                Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(DentBlue),
                contentAlignment = Alignment.Center
            ) { Text("🦷", fontSize = 14.sp) }
            Spacer(Modifier.width(6.dp))
        }

        Box(
            Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = if (isUser) 16.dp else 4.dp,
                        topEnd = if (isUser) 4.dp else 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp,
                    )
                )
                .background(if (isUser) BubbleUser else BubbleAi)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = msg.content,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isUser) White else BubbleAiText,
                lineHeight = 20.sp,
            )
        }
    }
}

// ── Fila de feedback discreta ────────────────────────────────────────────────
@Composable
private fun FeedbackRow(
    onUtil: () -> Unit,
    onNoUtil: () -> Unit,
) {
    var voted by remember { mutableStateOf(false) }
    if (voted) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 48.dp, end = 8.dp, top = 2.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "¿Te fue útil?",
            style = MaterialTheme.typography.labelSmall,
            color = DentTextSecond,
        )
        Spacer(Modifier.width(6.dp))
        IconButton(
            onClick = { voted = true; onUtil() },
            modifier = Modifier.size(28.dp),
        ) {
            Icon(
                Icons.Outlined.ThumbUp,
                contentDescription = "Sí, útil",
                tint = DentGreen,
                modifier = Modifier.size(16.dp),
            )
        }
        IconButton(
            onClick = { voted = true; onNoUtil() },
            modifier = Modifier.size(28.dp),
        ) {
            Icon(
                Icons.Outlined.ThumbDown,
                contentDescription = "No útil",
                tint = DentTextSecond,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

// ── Indicador "escribiendo..." ────────────────────────────────────────────────
@Composable
private fun TypingIndicator() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(32.dp).clip(CircleShape).background(DentBlue),
            contentAlignment = Alignment.Center
        ) { Text("🦷", fontSize = 14.sp) }
        Box(
            Modifier
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(BubbleAi)
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                repeat(3) {
                    Box(Modifier.size(6.dp).clip(CircleShape).background(DentBlue.copy(alpha = 0.5f)))
                }
            }
        }
    }
}

// ── Estado vacío — IA proactiva con chips ─────────────────────────────────────
@Composable
private fun EmptyState() {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("🦷", fontSize = 48.sp)
        Spacer(Modifier.height(12.dp))
        Text("Hola, soy tu asistente dental personal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            "¿En qué te puedo ayudar hoy?",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Spacer(Modifier.height(20.dp))
        // Chips proactivos per insight de retención: personalizar engagement
        listOf(
            "Tengo dolor 🦷",
            "Medicación 💊",
            "Mi tratamiento",
            "Todo bien ✅",
        ).forEach { chip ->
            SuggestionChip(
                onClick = { },
                label = { Text(chip, style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.padding(vertical = 3.dp),
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "(ℹ️ Orientación dental — no reemplaza diagnóstico profesional)",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

// ── Quick replies ─────────────────────────────────────────────────────────────
@Composable
private fun QuickReplies(
    isSending: Boolean,
    onSelect: (String) -> Unit,
) {
    val chips = listOf("Me duele 🦷", "Hay sangrado", "¿Es normal?", "Olvidé medicación", "¿Puedo comer?")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        chips.forEach { chip ->
            FilterChip(
                selected = false,
                onClick = { if (!isSending) onSelect(chip) },
                label = { Text(chip, style = MaterialTheme.typography.labelSmall) },
                enabled = !isSending,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color(0xFFEFF6FF),
                    labelColor = DentBlue,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = false,
                    borderColor = DentBlue.copy(alpha = 0.3f),
                ),
            )
        }
    }
}

// ── Input de mensaje ──────────────────────────────────────────────────────────
@Composable
private fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    isSending: Boolean,
    onSend: () -> Unit,
) {
    Surface(shadowElevation = 8.dp, color = Color.White) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text("Escribe tu pregunta...", style = MaterialTheme.typography.bodyMedium) },
                modifier = Modifier.weight(1f),
                maxLines = 4,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DentBlue,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color(0xFFF8FAFC),
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSend() }),
            )
            FilledIconButton(
                onClick = onSend,
                enabled = text.isNotBlank() && !isSending,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = DentBlue),
                modifier = Modifier.size(48.dp),
            ) {
                if (isSending) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = White, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Outlined.Send, "Enviar", tint = White)
                }
            }
        }
    }
}
