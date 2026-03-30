package com.dentapp.app.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.CreateAppointmentRequest
import com.dentapp.app.data.model.CreateVideoConsultaRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val reservaId: String? = null,
)

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(BookingUiState())
    val state: StateFlow<BookingUiState> = _state.asStateFlow()

    fun createBooking(doctorId: String, typeId: String, modalidad: String, fecha: String, hora: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                when (modalidad) {
                    "presencial" -> {
                        val scheduledAt = "${fecha}T${hora}:00"
                        val r = api.createAppointment(
                            CreateAppointmentRequest(
                                doctorId = doctorId,
                                scheduledAt = scheduledAt,
                            )
                        )
                        if (r.isSuccessful) {
                            _state.update { it.copy(isLoading = false, success = true, reservaId = r.body()?.id) }
                        } else {
                            _state.update { it.copy(isLoading = false, error = "Error al reservar cita") }
                        }
                    }
                    else -> {
                        // video | chat
                        val r = api.createVideoConsulta(
                            CreateVideoConsultaRequest(
                                doctorId = doctorId,
                                consultationTypeId = typeId,
                                fecha = fecha,
                                hora = hora,
                            )
                        )
                        if (r.isSuccessful) {
                            _state.update { it.copy(isLoading = false, success = true, reservaId = r.body()?.reservaId) }
                        } else {
                            _state.update { it.copy(isLoading = false, error = "Error al crear la consulta") }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión. Verifica tu internet.") }
            }
        }
    }

    fun clearError() = _state.update { it.copy(error = null) }
    fun resetSuccess() = _state.update { it.copy(success = false, reservaId = null) }
}
