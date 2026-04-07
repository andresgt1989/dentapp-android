package com.dentapp.app.ui.receta

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.MedicamentoOcr
import com.dentapp.app.data.model.RecetaOcrRequest
import com.dentapp.app.data.model.RecetaOcrResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

data class PrescriptionUiState(
    val paso: Int = 1,               // 1=opciones, 2=preview, 3=disclaimer, 4=loading, 5=resultado
    val capturedBitmap: Bitmap? = null,
    val disclaimerAceptado: Boolean = false,
    val isLoading: Boolean = false,
    val resultado: RecetaOcrResponse? = null,
    val error: String? = null,
    val snackbarMessage: String? = null,
)

@HiltViewModel
class PrescriptionScannerViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(PrescriptionUiState())
    val state: StateFlow<PrescriptionUiState> = _state.asStateFlow()

    fun setImagen(bitmap: Bitmap) {
        _state.update { it.copy(capturedBitmap = bitmap, paso = 2) }
    }

    fun usarEstaFoto() {
        _state.update { it.copy(paso = 3) }
    }

    fun aceptarDisclaimer(aceptado: Boolean) {
        _state.update { it.copy(disclaimerAceptado = aceptado) }
    }

    fun confirmarYSubir() {
        val bitmap = _state.value.capturedBitmap ?: return
        if (!_state.value.disclaimerAceptado) return

        viewModelScope.launch {
            _state.update { it.copy(paso = 4, isLoading = true, error = null) }
            try {
                val b64 = bitmapToBase64(bitmap)
                val res = api.escanearReceta(RecetaOcrRequest(imagenBase64 = b64))
                if (res.isSuccessful) {
                    val body = res.body()!!
                    _state.update {
                        it.copy(
                            paso = 5,
                            isLoading = false,
                            resultado = body,
                            snackbarMessage = "${body.recordatoriosActivados} recordatorios activados ✅",
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            paso = 1,
                            error = "No se pudo leer la receta. Intenta con mejor iluminación.",
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, paso = 1, error = "Sin conexión") }
            }
        }
    }

    fun retomar() = _state.update { it.copy(capturedBitmap = null, paso = 1, error = null, disclaimerAceptado = false) }
    fun clearSnackbar() = _state.update { it.copy(snackbarMessage = null) }
    fun clearError() = _state.update { it.copy(error = null) }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }
}
