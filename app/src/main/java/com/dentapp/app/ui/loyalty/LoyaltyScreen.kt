package com.dentapp.app.ui.loyalty

import android.content.Intent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.theme.*

private val FORMAS_GANAR = listOf(
    Triple("📅", "Confirmar cita",          "+50 pts"),
    Triple("⭐", "Calificar al doctor",      "+20 pts"),
    Triple("💊", "Registrar medicamento",    "+5 pts"),
    Triple("💬", "Feedback al asistente IA", "+2 pts"),
    Triple("👥", "Referir un amigo",         "+100 pts"),
    Triple("📋", "Completar perfil",         "+30 pts"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoyaltyScreen(
    onBack: () -> Unit,
    viewModel: LoyaltyViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis puntos", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.isLoading) {
                Spacer(Modifier.height(80.dp))
                CircularProgressIndicator(color = DentBlue)
                return@Column
            }

            state.error?.let {
                Spacer(Modifier.height(40.dp))
                Text(it, color = ErrorRed)
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = { viewModel.cargarDatos() }) { Text("Reintentar") }
                return@Column
            }

            val balance = state.balance ?: return@Column

            // Balance grande con animación
            Spacer(Modifier.height(24.dp))
            val animatedPoints by animateIntAsState(
                targetValue = balance.puntos,
                animationSpec = tween(durationMillis = 1200, easing = EaseOut),
                label = "puntos_animados",
            )

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(DentBlue, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        animatedPoints.toString(),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Text("puntos", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Nivel actual
            Text(
                balance.nivel,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DentTextPrimary,
            )

            // Progreso al siguiente nivel
            balance.siguienteNivel?.let { siguiente ->
                Spacer(Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(balance.nivel, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                        Text(siguiente, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                    }
                    Spacer(Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { balance.progresoPct / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = DentBlue,
                        trackColor = DentBlueLight,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Faltan ${balance.puntosParaSiguiente} puntos para $siguiente",
                        style = MaterialTheme.typography.bodySmall,
                        color = DentTextSecond,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Código de referido
            balance.codigoReferido?.let { codigo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text("Tu código de referido", style = MaterialTheme.typography.labelSmall, color = DentTextSecond)
                            Text(codigo, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = DentBlue)
                        }
                        IconButton(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT,
                                        "Únete a DentApp con mi código $codigo y lleva el control de tu salud dental 🦷")
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Compartir código"))
                            }
                        ) {
                            Icon(Icons.Outlined.Share, "Compartir", tint = DentBlue)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Formas de ganar puntos
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Cómo ganar puntos", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
                    FORMAS_GANAR.forEach { (emoji, accion, puntos) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(emoji, fontSize = 20.sp)
                            Spacer(Modifier.width(10.dp))
                            Text(accion, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
                            Text(puntos, color = DentBlue, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Historial
            if (state.history.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Historial", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
                        state.history.take(20).forEach { tx ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val positivo = tx.puntos > 0
                                Text(
                                    if (positivo) "+${tx.puntos}" else "${tx.puntos}",
                                    color = if (positivo) DentGreen else ErrorRed,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(56.dp),
                                )
                                Text(
                                    tx.descripcion ?: tx.tipo,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = DentTextSecond,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
