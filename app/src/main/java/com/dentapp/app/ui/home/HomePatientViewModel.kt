package com.dentapp.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.model.AppointmentDto
import com.dentapp.app.data.model.ClinicalAlert
import com.dentapp.app.data.model.DoctorDto
import com.dentapp.app.data.model.UrgenciaRequest
import com.dentapp.app.data.repository.DoctorRepository
import com.dentapp.app.data.repository.Result
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.ui.tratamiento.TratamientoDto
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomePatientState(
    val patientName: String = "",
    val email: String = "",
    val doctors: List<DoctorDto> = emptyList(),
    val appointments: List<AppointmentDto> = emptyList(),
    val criticalAlerts: List<ClinicalAlert> = emptyList(),
    val tratamientos: List<TratamientoDto> = emptyList(),
    val isLoadingDoctors: Boolean = false,
    val isLoadingAppointments: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class HomePatientViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository,
    private val api: ApiService,
    private val tokenStore: TokenStore,
) : ViewModel() {

    private val _state = MutableStateFlow(HomePatientState())
    val state: StateFlow<HomePatientState> = _state.asStateFlow()

    init {
        loadDoctors()
        loadAppointments()
        loadPatientProfile()
        loadClinicalAlerts()
        loadTratamientos()
    }

    fun loadDoctors() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingDoctors = true) }
            when (val r = doctorRepository.getDoctors()) {
                is Result.Success -> _state.update { it.copy(doctors = r.data, isLoadingDoctors = false) }
                is Result.Error   -> _state.update { it.copy(isLoadingDoctors = false, error = r.message) }
            }
        }
    }

    fun loadAppointments() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingAppointments = true) }
            try {
                val r = api.getPatientAppointments()
                if (r.isSuccessful) {
                    _state.update {
                        it.copy(
                            appointments = r.body()?.appointments ?: emptyList(),
                            isLoadingAppointments = false,
                        )
                    }
                } else {
                    _state.update { it.copy(isLoadingAppointments = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingAppointments = false) }
            }
        }
    }

    private fun loadPatientProfile() {
        viewModelScope.launch {
            try {
                val r = api.getMyPatientProfile()
                if (r.isSuccessful) {
                    val patient = r.body()?.patient
                    _state.update {
                        it.copy(
                            patientName = patient?.fullName ?: "",
                            email = patient?.email ?: "",
                        )
                    }
                }
            } catch (_: Exception) {}
        }
    }

    private fun loadClinicalAlerts() {
        viewModelScope.launch {
            try {
                val r = api.getClinicalAlerts()
                if (r.isSuccessful) {
                    val criticas = (r.body()?.alerts ?: emptyList())
                        .filter { it.prioridad == "CRITICA" }
                    _state.update { it.copy(criticalAlerts = criticas) }
                }
            } catch (_: Exception) {}
        }
    }

    private fun loadTratamientos() {
        viewModelScope.launch {
            try {
                val r = api.getTratamientos()
                if (r.isSuccessful) {
                    _state.update { it.copy(tratamientos = r.body()?.tratamientos ?: emptyList()) }
                }
            } catch (_: Exception) {}
        }
    }

    fun sendUrgencia(descripcion: String) {
        viewModelScope.launch {
            try {
                val r = api.sendUrgencia(UrgenciaRequest(descripcion = descripcion))
                if (r.isSuccessful) {
                    val n = r.body()?.notificados ?: 0
                    _state.update { it.copy(error = "✅ $n doctores notificados. Espera su respuesta.") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al enviar urgencia") }
            }
        }
    }

    fun clearError() = _state.update { it.copy(error = null) }
}
