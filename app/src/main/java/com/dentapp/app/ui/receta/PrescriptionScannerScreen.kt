package com.dentapp.app.ui.receta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.dentapp.app.data.model.MedicamentoOcr
import com.dentapp.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionScannerScreen(
    onBack: () -> Unit,
    viewModel: PrescriptionScannerViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Escanear receta", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            when (state.paso) {
                1 -> PasoOpciones(state, viewModel)
                2 -> PasoPreview(state, viewModel)
                3 -> PasoDisclaimer(state, viewModel)
                4 -> PasoLoading()
                5 -> PasoResultado(state, viewModel, onBack)
            }
        }
    }
}

@Composable
private fun PasoOpciones(state: PrescriptionUiState, viewModel: PrescriptionScannerViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("💊", fontSize = 48.sp)
        Spacer(Modifier.height(12.dp))
        Text(
            "Escanea tu receta dental",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "La IA leerá los medicamentos y activará tus recordatorios automáticamente.",
            style = MaterialTheme.typography.bodyMedium,
            color = DentTextSecond,
            textAlign = TextAlign.Center,
        )

        state.error?.let { err ->
            Spacer(Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F5)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(err, modifier = Modifier.padding(12.dp), color = ErrorRed,
                    style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                // En producción: abrir cámara con CameraX / MediaStore
                // viewModel.setImagen(bitmap)
                // Por ahora simular con imagen placeholder
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("📷 Tomar foto")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                // En producción: abrir galería con ActivityResultLauncher
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("🖼️ Elegir de galería")
        }
    }
}

@Composable
private fun PasoPreview(state: PrescriptionUiState, viewModel: PrescriptionScannerViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Vista previa de la receta", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFFEDF2F7), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text("📄 Imagen de receta", color = DentTextSecond)
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { viewModel.usarEstaFoto() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("Usar esta foto")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(onClick = { viewModel.retomar() }, modifier = Modifier.fillMaxWidth()) {
            Text("Tomar de nuevo")
        }
    }
}

@Composable
private fun PasoDisclaimer(state: PrescriptionUiState, viewModel: PrescriptionScannerViewModel) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = DentBlueLight),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("ℹ️ Antes de continuar", fontWeight = FontWeight.Bold, color = DentBlueDark)
                Spacer(Modifier.height(8.dp))
                Text(
                    "La IA leerá los datos de tu receta para activar recordatorios. " +
                    "Esta información solo se usa para tu seguimiento médico y " +
                    "no se comparte con terceros. " +
                    "La IA puede cometer errores — siempre verifica con tu médico.",
                    style = MaterialTheme.typography.bodySmall,
                    color = DentBlueDark,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.disclaimerAceptado,
                onCheckedChange = { viewModel.aceptarDisclaimer(it) },
                colors = CheckboxDefaults.colors(checkedColor = DentBlue),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Entiendo y acepto el uso de IA para leer mi receta",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { viewModel.confirmarYSubir() },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.disclaimerAceptado,
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("Analizar receta con IA 💊")
        }
    }
}

@Composable
private fun PasoLoading() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(color = DentBlue)
        Spacer(Modifier.height(20.dp))
        Text(
            "Leyendo tu receta con IA... 💊",
            style = MaterialTheme.typography.bodyLarge,
            color = DentTextSecond,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PasoResultado(
    state: PrescriptionUiState,
    viewModel: PrescriptionScannerViewModel,
    onBack: () -> Unit,
) {
    val resultado = state.resultado ?: return

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Medicamentos detectados", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(
            "${resultado.medicamentos.size} medicamento(s) encontrado(s)",
            style = MaterialTheme.typography.bodySmall,
            color = DentTextSecond,
        )
        Spacer(Modifier.height(16.dp))

        resultado.medicamentos.forEach { med ->
            MedicamentoCard(med)
            Spacer(Modifier.height(10.dp))
        }

        resultado.indicacionesEspeciales?.let { ind ->
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text("Indicaciones especiales", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelMedium)
                    Text(ind, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
        ) {
            Text("Listo ✅")
        }

        Spacer(Modifier.height(8.dp))

        OutlinedButton(onClick = { viewModel.retomar() }, modifier = Modifier.fillMaxWidth()) {
            Text("Escanear otra receta")
        }
    }
}

@Composable
private fun MedicamentoCard(med: MedicamentoOcr) {
    val tieneAlerta = med.alertasCriticas.isNotEmpty() ||
        med.nombre.contains("metronidazol") ||
        med.nombre.contains("ketorolaco")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (tieneAlerta) Color(0xFFFFF5F5) else Color.White
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (tieneAlerta) {
                    Text("🚨 ", fontSize = 16.sp)
                } else {
                    Text("💊 ", fontSize = 16.sp)
                }
                Text(
                    med.nombre.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    color = if (tieneAlerta) ErrorRed else DentTextPrimary,
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "${med.dosis ?: ""} · cada ${med.frecuenciaHoras}h · ${med.duracionDias} días",
                style = MaterialTheme.typography.bodySmall,
                color = DentTextSecond,
            )
            med.instrucciones?.let { inst ->
                Text(inst, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            med.alertasCriticas.forEach { alerta ->
                Spacer(Modifier.height(4.dp))
                Text(alerta, style = MaterialTheme.typography.bodySmall, color = ErrorRed)
            }
        }
    }
}
