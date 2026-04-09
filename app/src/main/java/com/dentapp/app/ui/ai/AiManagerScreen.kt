package com.dentapp.app.ui.ai

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

private const val PREFS_AI = "ai_prefs"
private const val KEY_DISCLAIMER_ACCEPTED = "disclaimer_accepted"

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
            // ── Premium gradient header ───────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .statusBarsPadding()
                    .height(72.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, "Volver", tint = White)
                    }
                    // AI avatar
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(White.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("🦷", fontSize = 18.sp)
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            "AI Dental",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Box(
                                Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen),
                            )
                            Text("En línea", color = White.copy(alpha = 0.85f), fontSize = 11.sp)
                        }
                    }
                }
            }
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
        containerColor = Background,
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {

            // ── Alert banner (CRÍTICA = coral, ALTA = amber) ──────────────────
            val topAlert = state.pendingAlerts.firstOrNull()
            if (topAlert != null) {
                val isCritical = topAlert.prioridad == "CRITICA"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isCritical) CoralLight else AlertAmberLight)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (isCritical) CoralAccent else AlertAmber),
                    )
                    Text(if (isCritical) "🚨" else "⚠️", fontSize = 16.sp)
                    Text(
                        topAlert.mensaje,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isCritical) AlertRed else AlertAmber,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            // ── Medicamentos del día (colapsable) ─────────────────────────────
            val activeMeds = state.context?.medicacion?.filter { !it.completado } ?: emptyList()
            if (activeMeds.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showMeds = !showMeds }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text("💊", fontSize = 14.sp)
                                Text("Mis medicamentos de hoy", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            }
                            Icon(
                                if (showMeds) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                                null,
                                tint = TealPrimary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                        AnimatedVisibility(visible = showMeds) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp).padding(bottom = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                activeMeds.forEach { med ->
                                    val medId = med.id
                                    val yaConfirmado = medId != null && state.confirmedMedIds.contains(medId)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (yaConfirmado) Color(0xFFECFDF5) else SurfaceGray)
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Column(Modifier.weight(1f)) {
                                            Text(med.medicamento, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            Text("${med.dosis} · c/${med.frecuenciaHrs}h · ${med.adherenciaPct}% adherencia", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                                        }
                                        if (medId != null) {
                                            IconButton(onClick = { if (!yaConfirmado) viewModel.confirmMedication(medId) }, modifier = Modifier.size(36.dp)) {
                                                Icon(
                                                    if (yaConfirmado) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                                                    "Confirmar toma",
                                                    tint = if (yaConfirmado) SuccessGreen else TextSecondary,
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
                HorizontalDivider(thickness = 0.5.dp, color = SurfaceGray)
            }

            // ── Context card (colapsa cuando hay mensajes) ────────────────────
            AnimatedVisibility(visible = state.messages.isEmpty() && state.context != null) {
                state.context?.let { ContextCard(it) }
            }

            // ── Messages list ─────────────────────────────────────────────────
            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = TealPrimary)
                }
            } else if (state.messages.isEmpty()) {
                EmptyState(onSendMessage = { viewModel.sendMessage(it) })
            } else {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    itemsIndexed(state.messages, key = { index, _ -> index }) { _, msg ->
                        MessageBubble(msg)
                        if (msg.role == "assistant") {
                            FeedbackRow(
                                onUtil   = { viewModel.submitFeedback(null, true, 5) },
                                onNoUtil = { viewModel.submitFeedback(null, false, 2) },
                            )
                        }
                    }
                    if (state.isSending) {
                        item { TypingIndicator() }
                    }
                }
                state.lastFeedbackPoints?.let { pts ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                        Text(
                            "+$pts pts ✨",
                            style = MaterialTheme.typography.labelSmall,
                            color = SuccessGreen,
                            modifier = Modifier.padding(end = 20.dp, bottom = 8.dp),
                        )
                    }
                }
            }
        }
    }
}

// ── Context card ──────────────────────────────────────────────────────────────

@Composable
private fun ContextCard(ctx: AiContextResponse) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, SurfaceGray),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TealPrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("🦷", fontSize = 20.sp)
                }
                Column {
                    Text("Recuperación post-cirugía", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text("Día ${ctx.diasPostCirugia} de 30", style = MaterialTheme.typography.bodySmall, color = TealPrimary)
                }
            }
            LinearProgressIndicator(
                progress = { ctx.diasPostCirugia / 30f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = TealPrimary,
                trackColor = TealLight.copy(alpha = 0.40f),
            )
            ctx.medicacion.filter { !it.completado }.forEach { med ->
                Row(
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (med.adherenciaPct < 50) AlertAmberLight else SurfaceGray)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(if (med.adherenciaPct < 50) "⚠️" else "💊", fontSize = 14.sp)
                        Text(med.medicamento.take(28), style = MaterialTheme.typography.labelMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("c/${med.frecuenciaHrs}h", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
                }
            }
            if (ctx.perfil.tabaco == "vape") {
                Row(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(AlertRedLight).padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("🚭", fontSize = 14.sp)
                    Text("Vapear daña tu cicatrización — habla con el AI", style = MaterialTheme.typography.labelMedium, color = AlertRed)
                }
            }
            Text("Pregúntame cualquier cosa 👇", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        }
    }
}

// ── Message bubble ────────────────────────────────────────────────────────────

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
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center,
            ) { Text("🦷", fontSize = 14.sp) }
            Spacer(Modifier.width(8.dp))
        }

        if (isUser) {
            // User bubble: gradient
            Box(
                Modifier
                    .widthIn(max = 280.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 4.dp, bottomStart = 18.dp, bottomEnd = 18.dp))
                    .background(Brush.linearGradient(listOf(GradientStart, GradientEnd)))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
            ) {
                Text(msg.content, fontSize = 15.sp, color = White, lineHeight = 22.sp)
            }
        } else {
            // AI bubble: white + left accent border
            Row(Modifier.widthIn(max = 280.dp)) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .defaultMinSize(minHeight = 40.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TealPrimary)
                        .align(Alignment.CenterVertically),
                )
                Spacer(Modifier.width(2.dp))
                Card(
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, SurfaceGray),
                ) {
                    Text(
                        msg.content,
                        fontSize = 15.sp,
                        color = TextPrimary,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    )
                }
            }
        }
    }
}

// ── Feedback row ──────────────────────────────────────────────────────────────

@Composable
private fun FeedbackRow(onUtil: () -> Unit, onNoUtil: () -> Unit) {
    var voted by remember { mutableStateOf(false) }
    if (voted) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 52.dp, end = 8.dp, top = 2.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("¿Te fue útil?", style = MaterialTheme.typography.labelSmall, color = TextTertiary)
        Spacer(Modifier.width(6.dp))
        IconButton(onClick = { voted = true; onUtil() }, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Outlined.ThumbUp, "Sí, útil", tint = SuccessGreen, modifier = Modifier.size(16.dp))
        }
        IconButton(onClick = { voted = true; onNoUtil() }, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Outlined.ThumbDown, "No útil", tint = TextTertiary, modifier = Modifier.size(16.dp))
        }
    }
}

// ── Typing indicator — 3 bouncing dots ───────────────────────────────────────

@Composable
private fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing")
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(32.dp).clip(CircleShape).background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
            contentAlignment = Alignment.Center,
        ) { Text("🦷", fontSize = 14.sp) }
        Spacer(Modifier.width(2.dp))
        Card(
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(1.dp, SurfaceGray),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                listOf(0, 150, 300).forEach { delayMs ->
                    val offsetY by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = -6f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(400, easing = FastOutSlowInEasing, delayMillis = delayMs),
                            repeatMode = RepeatMode.Reverse,
                        ),
                        label = "dot_$delayMs",
                    )
                    Box(
                        Modifier
                            .size(7.dp)
                            .offset(y = offsetY.dp)
                            .clip(CircleShape)
                            .background(TealPrimary.copy(alpha = 0.6f)),
                    )
                }
            }
        }
    }
}

// ── Empty state ───────────────────────────────────────────────────────────────

@Composable
private fun EmptyState(onSendMessage: (String) -> Unit = {}) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
            contentAlignment = Alignment.Center,
        ) {
            Text("🦷", fontSize = 36.sp)
        }
        Spacer(Modifier.height(16.dp))
        Text("Hola, soy tu asistente dental", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("¿En qué te puedo ayudar hoy?", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(20.dp))
        listOf("Tengo dolor 🦷", "Medicación 💊", "Mi tratamiento", "Todo bien ✅").forEach { chip ->
            OutlinedButton(
                onClick = { onSendMessage(chip) },
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.5.dp, TealPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary),
                modifier = Modifier.padding(vertical = 3.dp),
            ) {
                Text(chip, style = MaterialTheme.typography.labelMedium)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            "(ℹ️ Orientación dental — no reemplaza diagnóstico profesional)",
            style = MaterialTheme.typography.labelSmall,
            color = TextTertiary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

// ── Quick replies ─────────────────────────────────────────────────────────────

@Composable
private fun QuickReplies(isSending: Boolean, onSelect: (String) -> Unit) {
    val chips = listOf("Me duele 🦷", "Hay sangrado", "¿Es normal?", "Olvidé medicación", "¿Puedo comer?")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .background(SurfaceWhite)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        chips.forEach { chip ->
            var tapped by remember { mutableStateOf(false) }
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (tapped) TealPrimary else SurfaceWhite,
                border = BorderStroke(1.5.dp, TealPrimary),
                modifier = Modifier.clickable(
                    enabled = !isSending,
                    onClick = {
                        tapped = true
                        onSelect(chip)
                    },
                ),
            ) {
                Text(
                    chip,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (tapped) White else TealPrimary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                )
            }
        }
    }
}

// ── Chat input ────────────────────────────────────────────────────────────────

@Composable
private fun ChatInput(
    text: String,
    onTextChange: (String) -> Unit,
    isSending: Boolean,
    onSend: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Camera button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(SurfaceGray),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.CameraAlt, "Cámara", tint = TextSecondary, modifier = Modifier.size(18.dp))
            }

            // Text input
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGray),
                elevation = CardDefaults.cardElevation(0.dp),
                border = BorderStroke(1.dp, SurfaceGray),
                modifier = Modifier.weight(1f),
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    maxLines = 4,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 15.sp,
                        color = TextPrimary,
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { onSend() }),
                    decorationBox = { inner ->
                        if (text.isEmpty()) {
                            Text("Escribe tu pregunta...", fontSize = 15.sp, color = TextTertiary)
                        }
                        inner()
                    },
                )
            }

            // Send button — gradient circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (text.isNotBlank() && !isSending)
                            Brush.linearGradient(listOf(GradientStart, GradientEnd))
                        else
                            Brush.linearGradient(listOf(TextTertiary, TextTertiary))
                    )
                    .clickable(enabled = text.isNotBlank() && !isSending, onClick = onSend),
                contentAlignment = Alignment.Center,
            ) {
                if (isSending) {
                    CircularProgressIndicator(Modifier.size(18.dp), color = White, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Outlined.Send, "Enviar", tint = White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
