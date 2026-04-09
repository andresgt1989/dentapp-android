package com.dentapp.app.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
// ViewModel (solo eliminar cuenta)
// ─────────────────────────────────────────────────────────────────────────────

data class PrivacidadState(
    val deletingAccount: Boolean = false,
    val accountDeleted: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class PrivacidadViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(PrivacidadState())
    val state: StateFlow<PrivacidadState> = _state.asStateFlow()

    fun eliminarCuenta() {
        viewModelScope.launch {
            _state.value = _state.value.copy(deletingAccount = true, error = null)
            try {
                val resp = api.deleteAccount()
                if (resp.isSuccessful) {
                    _state.value = _state.value.copy(deletingAccount = false, accountDeleted = true)
                } else {
                    _state.value = _state.value.copy(
                        deletingAccount = false,
                        error = "Error al eliminar cuenta (${resp.code()})",
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(deletingAccount = false, error = e.message)
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacidadScreen(
    onBack: () -> Unit,
    onAccountDeleted: () -> Unit,
    onOpenGenerarQR: () -> Unit = {},
    viewModel: PrivacidadViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.accountDeleted) {
        if (state.accountDeleted) onAccountDeleted()
    }
    LaunchedEffect(state.error) {
        state.error?.let { snackbarHostState.showSnackbar(it); viewModel.clearError() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Privacidad y seguridad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface),
            )
        },
        containerColor = Background,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            // ── Datos que almacenamos ─────────────────────────────────────
            PrivCardInfo(
                titulo = "Tus datos en DentApp",
                items = listOf(
                    Icons.Outlined.Lock to "Tu expediente dental está cifrado en tránsito y en reposo.",
                    Icons.Outlined.Visibility to "Solo tú y los doctores autorizados pueden ver tu historial.",
                    Icons.Outlined.Share to "Comparte tu expediente de forma temporal con código QR.",
                    Icons.Outlined.Storage to "No vendemos tu información a terceros.",
                ),
            )

            // ── Información de pago ───────────────────────────────────────
            PrivCardInfo(
                titulo = "Pagos seguros",
                items = listOf(
                    Icons.Outlined.CreditCard to "Los pagos se procesan con Stripe (PCI DSS Nivel 1).",
                    Icons.Outlined.Security to "DentApp nunca almacena datos de tarjeta.",
                ),
            )

            // ── Controles de privacidad ───────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Controles", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 12.dp))

                    PrivAction(
                        icon = Icons.Outlined.Download,
                        titulo = "Exportar mis datos",
                        descripcion = "Descarga una copia de tu historial",
                        onClick = { /* TODO: implementar export */ },
                    )
                    HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 4.dp))
                    PrivAction(
                        icon = Icons.Outlined.QrCode,
                        titulo = "Gestionar QR compartidos",
                        descripcion = "Ver y revocar tokens de acceso activos",
                        onClick = onOpenGenerarQR,
                    )
                }
            }

            // ── Zona de peligro ───────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Error.copy(alpha = 0.05f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Error.copy(alpha = 0.3f)),
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(bottom = 8.dp),
                    ) {
                        Icon(Icons.Outlined.Warning, contentDescription = null, tint = Error, modifier = Modifier.size(18.dp))
                        Text("Zona de peligro", fontWeight = FontWeight.SemiBold, color = Error)
                    }
                    Text(
                        "Eliminar tu cuenta borrará permanentemente todos tus datos, incluyendo historial dental, citas y perfil médico. Esta acción es irreversible.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Error),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Error),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Icon(Icons.Outlined.DeleteForever, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Eliminar mi cuenta")
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }

    // Dialog eliminar cuenta
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = { Icon(Icons.Outlined.DeleteForever, contentDescription = null, tint = Error) },
            title = { Text("¿Eliminar cuenta?", color = Error) },
            text = {
                Text("Esta acción es permanente e irreversible. Todos tus datos se eliminarán de nuestros servidores.")
            },
            confirmButton = {
                Button(
                    onClick = { showDeleteDialog = false; viewModel.eliminarCuenta() },
                    enabled = !state.deletingAccount,
                    colors = ButtonDefaults.buttonColors(containerColor = Error),
                ) {
                    if (state.deletingAccount) {
                        CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp, color = OnPrimary)
                    } else {
                        Text("Sí, eliminar")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
            },
        )
    }
}

@Composable
private fun PrivCardInfo(
    titulo: String,
    items: List<Pair<ImageVector, String>>,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 10.dp))
            items.forEach { (icon, text) ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp).padding(top = 2.dp))
                    Text(text, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun PrivAction(
    icon: ImageVector,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 0.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
            Column(Modifier.weight(1f)) {
                Text(titulo, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = OnBackground)
                Text(descripcion, color = TextSecondary, fontSize = 12.sp)
            }
            Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        }
    }
}
