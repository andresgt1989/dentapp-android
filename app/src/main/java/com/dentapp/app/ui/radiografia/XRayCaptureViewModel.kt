package com.dentapp.app.ui.radiografia

import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.RadiografiaUploadRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

data class XRayUiState(
    val condicionesOk: List<Boolean> = List(4) { false },
    val capturedBitmap: Bitmap? = null,
    val isAnalyzing: Boolean = false,
    val resultado: XRayResultado? = null,
    val error: String? = null,
    val paso: Int = 1, // 1=checklist, 2=cámara, 3=resultado
)

data class XRayResultado(
    val tipo: String,
    val zona: String,
    val calidad: String,
    val razonCalidad: String?,
    val guardado: Boolean = false,
)

val CONDICIONES_XRAY = listOf(
    "Placa sobre superficie blanca o pantalla iluminada",
    "Celular paralelo a la placa, sin inclinar",
    "Toda la placa visible dentro del marco",
    "Sin reflejos ni dedos en la imagen",
)

@HiltViewModel
class XRayCaptureViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(XRayUiState())
    val state: StateFlow<XRayUiState> = _state.asStateFlow()

    fun toggleCondicion(index: Int) {
        val updated = _state.value.condicionesOk.toMutableList()
        updated[index] = !updated[index]
        _state.update { it.copy(condicionesOk = updated) }
    }

    fun todasCondicionesOk() = _state.value.condicionesOk.all { it }

    fun irACamara() = _state.update { it.copy(paso = 2) }

    fun capturarImagen(bitmap: Bitmap) {
        _state.update { it.copy(capturedBitmap = bitmap, paso = 3) }
        analizarImagen(bitmap)
    }

    private fun analizarImagen(bitmap: Bitmap) {
        viewModelScope.launch {
            _state.update { it.copy(isAnalyzing = true, error = null) }
            try {
                val b64 = bitmapToBase64(bitmap)
                val res = api.subirRadiografia(RadiografiaUploadRequest(imagenBase64 = b64))
                if (res.isSuccessful) {
                    val body = res.body()!!
                    _state.update {
                        it.copy(
                            isAnalyzing = false,
                            resultado = XRayResultado(
                                tipo = body.tipo ?: "Desconocido",
                                zona = body.zona ?: "—",
                                calidad = body.calidad ?: "aceptable",
                                razonCalidad = body.razonCalidad,
                                guardado = body.ok,
                            ),
                        )
                    }
                } else {
                    _state.update { it.copy(isAnalyzing = false, error = "No se pudo analizar la imagen") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isAnalyzing = false, error = "Sin conexión") }
            }
        }
    }

    fun reintentar() = _state.update { it.copy(capturedBitmap = null, resultado = null, error = null, paso = 2) }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }
}
