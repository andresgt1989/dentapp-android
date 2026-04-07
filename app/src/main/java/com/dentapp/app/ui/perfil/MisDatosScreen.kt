package com.dentapp.app.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

data class MisDatosForm(
    val fullName: String = "",
    val phone: String = "",
    val dateOfBirth: String = "",
    val address: String = "",
    val emergencyContact: String = "",
    val emergencyPhone: String = "",
    val medicalNotes: String = "",
)

data class MisDatosState(
    val loading: Boolean = true,
    val saving: Boolean = false,
    val form: MisDatosForm = MisDatosForm(),
    val email: String = "",
    val error: String? = null,
    val successMsg: String? = null,
)

@HiltViewModel
class MisDatosViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(MisDatosState())
    val state: StateFlow<MisDatosState> = _state.asStateFlow()

    init { cargar() }

    private fun cargar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val resp = api.getMyPatientProfile()
                if (resp.isSuccessful) {
                    val p = resp.body()!!.patient
                    _state.value = _state.value.copy(
                        loading = false,
                        form = MisDatosForm(
                            fullName = p.fullName,
                            phone = p.phone ?: "",
                            dateOfBirth = p.dateOfBirth ?: "",
                            address = p.address ?: "",
                            emergencyContact = p.emergencyContact ?: "",
                            emergencyPhone = p.emergencyPhone ?: "",
                            medicalNotes = p.medicalNotes ?: "",
                        ),
                    )
                } else {
                    _state.value = _state.value.copy(loading = false, error = "Error ${resp.code()}")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message)
            }
        }
    }

    fun updateForm(block: MisDatosForm.() -> MisDatosForm) {
        _state.value = _state.value.copy(form = _state.value.form.block())
    }

    fun guardar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(saving = true, error = null)
            val f = _state.value.form
            try {
                val body = mapOf(
                    "full_name" to f.fullName,
                    "phone" to f.phone,
                    "date_of_birth" to f.dateOfBirth,
                    "address" to f.address,
                    "emergency_contact" to f.emergencyContact,
                    "emergency_phone" to f.emergencyPhone,
                    "medical_notes" to f.medicalNotes,
                )
                val resp = api.updatePatientProfile(body)
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(saving = false, successMsg = "Datos guardados")
                } else {
                    _state.value = _state.value.copy(saving = false, error = "Error al guardar (${resp.code()})")
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
fun MisDatosScreen(
    onBack: () -> Unit,
    viewModel: MisDatosViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

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
                title = { Text("Mis Datos", fontWeight = FontWeight.Bold) },
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
        },
        containerColor = Background,
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // ── Información personal ──────────────────────────────────────
            DatosCard(titulo = "Información personal", icon = Icons.Outlined.Person) {
                DatosField(
                    value = state.form.fullName,
                    onValueChange = { viewModel.updateForm { copy(fullName = it) } },
                    label = "Nombre completo",
                    icon = Icons.Outlined.Person,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                )
                Spacer(Modifier.height(8.dp))
                DatosField(
                    value = state.form.phone,
                    onValueChange = { viewModel.updateForm { copy(phone = it) } },
                    label = "Teléfono",
                    icon = Icons.Outlined.Phone,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                )
                Spacer(Modifier.height(8.dp))
                DatosField(
                    value = state.form.dateOfBirth,
                    onValueChange = { viewModel.updateForm { copy(dateOfBirth = it) } },
                    label = "Fecha de nacimiento (YYYY-MM-DD)",
                    icon = Icons.Outlined.CalendarToday,
                    placeholder = "1990-01-15",
                )
                Spacer(Modifier.height(8.dp))
                DatosField(
                    value = state.form.address,
                    onValueChange = { viewModel.updateForm { copy(address = it) } },
                    label = "Dirección",
                    icon = Icons.Outlined.Home,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                )
            }

            // ── Contacto de emergencia ────────────────────────────────────
            DatosCard(titulo = "Contacto de emergencia", icon = Icons.Outlined.Emergency) {
                DatosField(
                    value = state.form.emergencyContact,
                    onValueChange = { viewModel.updateForm { copy(emergencyContact = it) } },
                    label = "Nombre del contacto",
                    icon = Icons.Outlined.Person,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                )
                Spacer(Modifier.height(8.dp))
                DatosField(
                    value = state.form.emergencyPhone,
                    onValueChange = { viewModel.updateForm { copy(emergencyPhone = it) } },
                    label = "Teléfono de emergencia",
                    icon = Icons.Outlined.Phone,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                )
            }

            // ── Notas médicas generales ───────────────────────────────────
            DatosCard(titulo = "Notas médicas generales", icon = Icons.Outlined.Notes) {
                OutlinedTextField(
                    value = state.form.medicalNotes,
                    onValueChange = { viewModel.updateForm { copy(medicalNotes = it) } },
                    label = { Text("Alergias, condiciones, notas para el doctor…") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 6,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DatosCard(
    titulo: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
                Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                Text(titulo, fontWeight = FontWeight.SemiBold)
            }
            content()
        }
    }
}

@Composable
private fun DatosField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp)) },
        placeholder = if (placeholder != null) ({ Text(placeholder, color = TextSecondary) }) else null,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        singleLine = true,
    )
}
