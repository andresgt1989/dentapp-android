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

    // Auto-scroll al último mensaje
    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    // Mostrar error como snackbar
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
                        // Avatar AI
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
                            Text("AI Manager Dental", style = MaterialTheme.typography.titleMedium, color = White, fontWeight = FontWeight.Bold)
                            state.context?.let {
                                Text("Día ${it.diasPostCirugia} • Dra. Andreá Oris",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = White.copy(alpha = 0.8f))
                            }
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
            ChatInput(
                text = inputText,
                onTextChange = { inputText = it },
                isSending = state.isSending,
                onSend = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText.trim())
                        inputText = ""
                    }
                },
            )
        },
        containerColor = Color(0xFFF1F5F9),
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {

            // Tarjeta de contexto (colapsa cuando hay mensajes)
            AnimatedVisibility(visible = state.messages.isEmpty() && state.context != null) {
                state.context?.let { ContextCard(it) }
            }

            // Lista de mensajes
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
                    }
                    if (state.isSending) {
                        item { TypingIndicator() }
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

            // Progreso de recuperación
            LinearProgressIndicator(
                progress = { ctx.diasPostCirugia / 30f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = DentBlue,
                trackColor = Color(0xFFE2E8F0),
            )

            // Medicación
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

            // Alertas de hábitos
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

// ── Estado vacío ──────────────────────────────────────────────────────────────
@Composable
private fun EmptyState() {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("🦷", fontSize = 48.sp)
        Spacer(Modifier.height(12.dp))
        Text("Hola, soy tu AI Manager Dental", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Tengo acceso a tu historial clínico, medicación e indicaciones de la Dra. Andreá. Pregúntame lo que necesites.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        // Sugerencias rápidas
        listOf(
            "¿Cuántas pastillas me faltan?",
            "¿Puedo comer hoy pizza?",
            "Me duele, ¿es normal?",
            "Ayúdame a dejar el vape",
        ).forEach { sugerencia ->
            SuggestionChip(
                onClick = { },
                label = { Text(sugerencia, style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.padding(vertical = 3.dp),
                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
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
