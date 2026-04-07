package com.dentapp.app.ui.rx

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.ManualPrescriptionRequest
import com.dentapp.app.ui.theme.Background
import com.dentapp.app.ui.theme.TealPrimary
import com.dentapp.app.ui.theme.TextPrimary
import com.dentapp.app.ui.theme.TextSecondary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

data class RxForm(
    val medicationName: String = "",
    val dose: String = "",
    val frequency: String = "",
    val doctorName: String = "",
)

data class RxState(
    val saving: Boolean = false,
    val form: RxForm = RxForm(),
    val successMsg: String? = null,
    val error: String? = null,
)

@HiltViewModel
class RxPatientViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(RxState())
    val state: StateFlow<RxState> = _state.asStateFlow()

    fun updateForm(block: RxForm.() -> RxForm) {
        _state.value = _state.value.copy(form = _state.value.form.block())
    }

    fun saveManual() {
        val f = _state.value.form
        if (f.medicationName.isBlank()) {
            _state.value = _state.value.copy(error = "Nombre del medicamento requerido")
            return
        }
        viewModelScope.launch {
            _state.value = _state.value.copy(saving = true, error = null)
            try {
                val resp = api.createPrescription(
                    ManualPrescriptionRequest(
                        medicationName = f.medicationName,
                        dose = f.dose,
                        frequency = f.frequency,
                        doctorName = f.doctorName,
                    )
                )
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(
                        saving = false,
                        successMsg = "Receta guardada correctamente",
                        form = RxForm(),
                    )
                } else {
                    _state.value = _state.value.copy(
                        saving = false,
                        error = "Error ${resp.code()} al guardar",
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(saving = false, error = e.message)
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(error = null, successMsg = null)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RxPatientScreen(
    onBack: () -> Unit,
    viewModel: RxPatientViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(0) }

    // Camera state
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var photoTaken by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        photoTaken = success
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            val uri = createImageUri(context)
            photoUri = uri
            cameraLauncher.launch(uri)
        }
    }

    LaunchedEffect(state.successMsg) {
        state.successMsg?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }
    LaunchedEffect(state.error) {
        state.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessages() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mis Recetas", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            )
        },
        containerColor = Background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            // ── Tabs ─────────────────────────────────────────────────────────
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = TealPrimary,
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Cámara 📷") },
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Manual ✍️") },
                )
            }

            when (selectedTab) {
                0 -> CameraTab(
                    photoTaken = photoTaken,
                    photoUri = photoUri,
                    onLaunchCamera = {
                        val hasCam = ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.CAMERA,
                        ) == PackageManager.PERMISSION_GRANTED
                        if (hasCam) {
                            val uri = createImageUri(context)
                            photoUri = uri
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    },
                )
                1 -> ManualFormTab(
                    form = state.form,
                    saving = state.saving,
                    onUpdateForm = { viewModel.updateForm(it) },
                    onSave = { viewModel.saveManual() },
                )
            }
        }
    }
}

@Composable
private fun CameraTab(
    photoTaken: Boolean,
    photoUri: Uri?,
    onLaunchCamera: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("📷", fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            if (photoTaken) "¡Foto tomada!" else "Fotografía tu receta",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            if (photoTaken) "La receta fue enviada para procesarse"
            else "Toca el botón para fotografiar tu receta médica",
            fontSize = 14.sp,
            color = TextSecondary,
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onLaunchCamera,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
        ) {
            Icon(Icons.Outlined.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                if (photoTaken) "Tomar otra foto" else "Abrir cámara",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun ManualFormTab(
    form: RxForm,
    saving: Boolean,
    onUpdateForm: (RxForm.() -> RxForm) -> Unit,
    onSave: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = form.medicationName,
            onValueChange = { onUpdateForm { copy(medicationName = it) } },
            label = { Text("Nombre del medicamento *") },
            leadingIcon = { Icon(Icons.Outlined.Medication, null, tint = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )

        OutlinedTextField(
            value = form.dose,
            onValueChange = { onUpdateForm { copy(dose = it) } },
            label = { Text("Dosis (ej: 500mg)") },
            leadingIcon = { Icon(Icons.Outlined.Straighten, null, tint = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        OutlinedTextField(
            value = form.frequency,
            onValueChange = { onUpdateForm { copy(frequency = it) } },
            label = { Text("Frecuencia (ej: cada 8 horas)") },
            leadingIcon = { Icon(Icons.Outlined.Schedule, null, tint = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )

        OutlinedTextField(
            value = form.doctorName,
            onValueChange = { onUpdateForm { copy(doctorName = it) } },
            label = { Text("Nombre del médico") },
            leadingIcon = { Icon(Icons.Outlined.Person, null, tint = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onSave,
            enabled = !saving,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
        ) {
            if (saving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White,
                )
            } else {
                Icon(Icons.Outlined.Save, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Guardar receta", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────────────────────────────────────

private fun createImageUri(context: Context): Uri {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val file = File(context.cacheDir, "rx_photo_$timestamp.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file,
    )
}
