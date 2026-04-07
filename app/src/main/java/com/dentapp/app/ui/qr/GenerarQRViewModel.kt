package com.dentapp.app.ui.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.ShareTokenDto
import com.dentapp.app.data.model.ShareTokenRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QrUiState(
    val isLoading: Boolean = false,
    val token: ShareTokenDto? = null,
    val shareUrl: String? = null,
    val duracionSeleccionada: String = "24h",
    val error: String? = null,
    val showConfirmRegenerate: Boolean = false,
)

@HiltViewModel
class GenerarQRViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(QrUiState())
    val state: StateFlow<QrUiState> = _state.asStateFlow()

    init { cargarTokenActivo() }

    fun cargarTokenActivo() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val res = api.getMisShareTokens()
                if (res.isSuccessful) {
                    val token = res.body()?.tokens?.firstOrNull()
                    if (token != null) {
                        val url = buildUrl(token.token)
                        _state.update { it.copy(isLoading = false, token = token, shareUrl = url) }
                    } else {
                        generarNuevoToken()
                    }
                } else {
                    generarNuevoToken()
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión") }
            }
        }
    }

    fun generarNuevoToken() {
        val duracion = _state.value.duracionSeleccionada
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, showConfirmRegenerate = false) }
            try {
                val res = api.createShareToken(ShareTokenRequest(duracion))
                if (res.isSuccessful) {
                    val body = res.body()!!
                    val url = body.shareUrl
                    val dto = ShareTokenDto(
                        id = "",
                        token = body.token,
                        expiresAt = body.expiresAt,
                        viewsCount = 0,
                        createdAt = "",
                    )
                    _state.update { it.copy(isLoading = false, token = dto, shareUrl = url) }
                } else {
                    _state.update { it.copy(isLoading = false, error = "Error al generar QR") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión") }
            }
        }
    }

    fun setDuracion(d: String) = _state.update { it.copy(duracionSeleccionada = d) }
    fun showConfirmRegenerate() = _state.update { it.copy(showConfirmRegenerate = true) }
    fun dismissConfirmRegenerate() = _state.update { it.copy(showConfirmRegenerate = false) }
    fun clearError() = _state.update { it.copy(error = null) }

    private fun buildUrl(token: String) =
        "https://dentapp.site/expediente/$token"
}
