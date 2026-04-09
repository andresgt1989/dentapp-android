package com.dentapp.app.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.CreateAppointmentRequest
import com.dentapp.app.data.model.CreateVideoConsultaRequest
import com.dentapp.app.utils.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookingUiState(
    val isLoading: Boolean = false,
    val isLoadingSchedule: Boolean = true,
    val error: String? = null,
    val success: Boolean = false,
    val reservaId: String? = null,
    // Horario real del doctor
    val availableSlots: List<String> = emptyList(),   // ["08:00","08:30",...]
    val workingDays: Set<Int> = setOf(2, 3, 4, 5, 6), // Calendar.DAY_OF_WEEK: 2=Lun..6=Vie
)

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val api: ApiService,
    private val analytics: Analytics,
) : ViewModel() {

    private val _state = MutableStateFlow(BookingUiState())
    val state: StateFlow<BookingUiState> = _state.asStateFlow()

    fun loadDoctorSchedule(doctorId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingSchedule = true) }
            try {
                val r = api.getDoctorById(doctorId)
                if (r.isSuccessful) {
                    val doctor = r.body()?.doctor
                    if (doctor != null) {
                        _state.update {
                            it.copy(
                                isLoadingSchedule = false,
                                availableSlots = generateSlots(
                                    doctor.horarioInicio,
                                    doctor.horarioFin,
                                ),
                                workingDays = diasLaborablesToCalendar(doctor.diasLaborables),
                            )
                        }
                        return@launch
                    }
                }
            } catch (_: Exception) { /* fallback to defaults */ }
            // Fallback: horario estándar 8-18 Lun-Vie
            _state.update {
                it.copy(
                    isLoadingSchedule = false,
                    availableSlots = generateSlots("08:00", "18:00"),
                    workingDays = setOf(2, 3, 4, 5, 6),
                )
            }
        }
    }

    /**
     * Genera slots de 30 minutos entre apertura y cierre.
     * Ejemplo: "08:00" a "12:00" → ["08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30"]
     */
    private fun generateSlots(inicio: String, fin: String): List<String> {
        return try {
            val (hI, mI) = inicio.split(":").map { it.toInt() }
            val (hF, mF) = fin.split(":").map { it.toInt() }
            val startMin = hI * 60 + mI
            val endMin   = hF * 60 + mF
            (startMin until endMin step 30).map { min ->
                "%02d:%02d".format(min / 60, min % 60)
            }
        } catch (_: Exception) {
            listOf("08:00", "09:00", "10:00", "11:00", "12:00",
                   "14:00", "15:00", "16:00", "17:00")
        }
    }

    /**
     * Convierte dias_laborables del backend (1=Lun..7=Dom) a Calendar.DAY_OF_WEEK (1=Dom,2=Lun..7=Sáb).
     */
    private fun diasLaborablesToCalendar(dias: List<Int>): Set<Int> = dias.map { d ->
        when (d) {
            1 -> 2   // Lun
            2 -> 3   // Mar
            3 -> 4   // Mié
            4 -> 5   // Jue
            5 -> 6   // Vie
            6 -> 7   // Sáb
            7 -> 1   // Dom
            else -> d
        }
    }.toSet()

    fun createBooking(doctorId: String, typeId: String, modalidad: String, fecha: String, hora: String, amountCents: Int = 0) {
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
                            analytics.bookingCompleted(amountCents)
                            _state.update { it.copy(isLoading = false, success = true, reservaId = r.body()?.id) }
                        } else {
                            _state.update { it.copy(isLoading = false, error = "Error al reservar cita") }
                        }
                    }
                    else -> {
                        val r = api.createVideoConsulta(
                            CreateVideoConsultaRequest(
                                doctorId = doctorId,
                                consultationTypeId = typeId,
                                fecha = fecha,
                                hora = hora,
                            )
                        )
                        if (r.isSuccessful) {
                            analytics.bookingCompleted(amountCents)
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

    fun trackBookingStarted(doctorId: String) = analytics.bookingStarted(doctorId)

    fun clearError() = _state.update { it.copy(error = null) }
    fun resetSuccess() = _state.update { it.copy(success = false, reservaId = null) }
}
