package com.dentapp.app.ui.qr

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerarQRScreen(
    onBack: () -> Unit,
    viewModel: GenerarQRViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val duraciones = listOf(
        "1h"      to "1 hora",
        "24h"     to "24 horas",
        "7d"      to "7 días",
        "forever" to "Sin límite",
    )

    if (state.showConfirmRegenerate) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissConfirmRegenerate() },
            title = { Text("¿Regenerar QR?") },
            text  = { Text("El QR anterior dejará de funcionar. Solo tus datos dentales son compartidos.") },
            confirmButton = {
                TextButton(onClick = { viewModel.generarNuevoToken() }) {
                    Text("Regenerar", color = DentBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissConfirmRegenerate() }) {
                    Text("Cancelar")
                }
            },
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compartir expediente", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(24.dp))

            // Card de disclaimer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = DentBlueLight),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Solo compartes tu información dental.\nEmail, contraseña y datos bancarios nunca se incluyen.",
                    modifier = Modifier.padding(14.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = DentBlueDark,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(20.dp))

            // Selector de duración
            Text(
                "Vigencia del enlace",
                style = MaterialTheme.typography.labelMedium,
                color = DentTextSecond,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                duraciones.forEach { (valor, etiqueta) ->
                    FilterChip(
                        selected = state.duracionSeleccionada == valor,
                        onClick  = { viewModel.setDuracion(valor) },
                        label    = { Text(etiqueta, fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        colors   = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DentBlue,
                            selectedLabelColor = Color.White,
                        ),
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // QR o loading
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, DividerColor, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = DentBlue)
                } else if (state.shareUrl != null) {
                    // Placeholder QR visual — en producción usar biblioteca QR
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🦷", fontSize = 48.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Escanea para ver\nmi expediente dental",
                            style = MaterialTheme.typography.bodySmall,
                            color = DentTextSecond,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            // Contador de vistas
            state.token?.let { t ->
                Spacer(Modifier.height(12.dp))
                Badge(containerColor = DentBlueLight) {
                    Text(
                        "Visto ${t.viewsCount} veces",
                        color = DentBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
                t.expiresAt?.let { exp ->
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Expira: $exp",
                        style = MaterialTheme.typography.labelSmall,
                        color = DentTextSecond,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Botón Compartir
            Button(
                onClick = {
                    val url = state.shareUrl ?: return@Button
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Mi expediente dental: $url")
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Compartir expediente"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
                enabled = state.shareUrl != null && !state.isLoading,
            ) {
                Icon(Icons.Outlined.Share, null, Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Compartir enlace")
            }

            Spacer(Modifier.height(12.dp))

            // Botón Regenerar
            OutlinedButton(
                onClick  = { viewModel.showConfirmRegenerate() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled  = !state.isLoading,
            ) {
                Icon(Icons.Outlined.Refresh, null, Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Regenerar QR")
            }

            // Error
            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = ErrorRed, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
