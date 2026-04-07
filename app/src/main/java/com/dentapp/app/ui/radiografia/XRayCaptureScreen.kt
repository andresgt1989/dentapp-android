package com.dentapp.app.ui.radiografia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XRayCaptureScreen(
    onBack: () -> Unit,
    viewModel: XRayCaptureViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Capturar radiografía", fontWeight = FontWeight.SemiBold) },
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
        ) {
            when (state.paso) {
                1 -> PasoChecklist(state, viewModel)
                2 -> PasoCamara(state, viewModel)
                3 -> PasoResultado(state, viewModel, onBack)
            }
        }
    }
}

@Composable
private fun PasoChecklist(state: XRayUiState, viewModel: XRayCaptureViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            "Prepara tu placa radiográfica",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            "Asegúrate de cumplir estas condiciones para obtener la mejor captura:",
            style = MaterialTheme.typography.bodyMedium,
            color = DentTextSecond,
            modifier = Modifier.padding(bottom = 20.dp),
        )

        CONDICIONES_XRAY.forEachIndexed { i, condicion ->
            val ok = state.condicionesOk.getOrElse(i) { false }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (ok) Color(0xFFE8F5E9) else Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = { viewModel.toggleCondicion(i) },
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = if (ok) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (ok) DentGreen else DentTextSecond,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(condicion, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { viewModel.irACamara() },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.todasCondicionesOk(),
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("📷 Abrir cámara")
        }

        if (!viewModel.todasCondicionesOk()) {
            Spacer(Modifier.height(8.dp))
            Text(
                "Marca todas las condiciones para continuar",
                style = MaterialTheme.typography.bodySmall,
                color = DentTextSecond,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PasoCamara(state: XRayUiState, viewModel: XRayCaptureViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Apunta la cámara a tu placa radiográfica",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(16.dp))

        // Viewfinder placeholder — en producción integrar CameraX
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(Color.Black, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text("📷", fontSize = 64.sp)
            Text(
                "Vista previa de cámara\n(integrar CameraX)",
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            )
        }

        Spacer(Modifier.height(24.dp))

        // Para demo: simular captura
        Button(
            onClick = {
                // En producción: capturar desde CameraX
                // viewModel.capturarImagen(bitmap)
                // Por ahora navegar al resultado simulado
                viewModel.reintentar()
            },
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("Capturar imagen")
        }
    }
}

@Composable
private fun PasoResultado(state: XRayUiState, viewModel: XRayCaptureViewModel, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.isAnalyzing) {
            Spacer(Modifier.height(60.dp))
            CircularProgressIndicator(color = DentBlue)
            Spacer(Modifier.height(16.dp))
            Text(
                "Analizando tu placa... 🦷",
                style = MaterialTheme.typography.bodyLarge,
                color = DentTextSecond,
            )
            return
        }

        state.error?.let { err ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F5)),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("No se pudo analizar la imagen", fontWeight = FontWeight.Bold, color = ErrorRed)
                    Spacer(Modifier.height(4.dp))
                    Text(err, style = MaterialTheme.typography.bodySmall)
                }
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(onClick = { viewModel.reintentar() }, modifier = Modifier.fillMaxWidth()) {
                Text("Tomar de nuevo")
            }
            return
        }

        state.resultado?.let { r ->
            val esDeficiente = r.calidad == "deficiente"

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (esDeficiente) Color(0xFFFFF5F5) else Color(0xFFE8F5E9)
                ),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        if (esDeficiente) "❌ Imagen de baja calidad" else "✅ Imagen analizada",
                        fontWeight = FontWeight.Bold,
                        color = if (esDeficiente) ErrorRed else DentGreen,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Tipo: ${r.tipo}", style = MaterialTheme.typography.bodyMedium)
                    Text("Zona: ${r.zona}", style = MaterialTheme.typography.bodyMedium)
                    Text("Calidad: ${r.calidad}", style = MaterialTheme.typography.bodyMedium)
                    r.razonCalidad?.let { razon ->
                        Spacer(Modifier.height(4.dp))
                        Text("⚠️ $razon", style = MaterialTheme.typography.bodySmall, color = Warning)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (esDeficiente) {
                OutlinedButton(onClick = { viewModel.reintentar() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Tomar de nuevo")
                }
            } else {
                Button(
                    onClick = { onBack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
                ) {
                    Text("Guardar en mi expediente ✅")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { viewModel.reintentar() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Tomar otra")
                }
            }
        }
    }
}
