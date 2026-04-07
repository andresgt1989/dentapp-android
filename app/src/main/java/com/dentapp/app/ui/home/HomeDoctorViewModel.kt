package com.dentapp.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.*
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class HomeDoctorUiState(
    val selectedTab: Int = 0,
    val doctorName: String = "Doctor",
    // Stripe Connect
    val stripeStatus: String = "pending",       // pending | pending_verification | active
    val stripeOnboardingUrl: String? = null,
    val isLoadingStripe: Boolean = false,
    // Tab 1 — Hoy
    val citasHoy: List<DoctorCitaDto> = emptyList(),
    val ingresosHoy: Double = 0.0,
    val isLoadingHoy: Boolean = false,
    // Tab 2 — Pacientes
    val pacientes: List<DoctorPacienteDto> = emptyList(),
    val searchQuery: String = "",
    val isLoadingPacientes: Boolean = false,
    // Tab 3 — Agenda
    val horarioInicio: String = "08:00",
    val horarioFin: String = "18:00",
    val diasLaborables: List<Int> = listOf(1, 2, 3, 4, 5),
    val isSavingHorario: Boolean = false,
    val horarioGuardado: Boolean = false,
    // Tab 4 — Ingresos
    val totalMes: Double = 0.0,
    val totalPendiente: Double = 0.0,
    val payouts: List<DoctorPayoutDto> = emptyList(),
    val isLoadingIngresos: Boolean = false,
    val payoutEnviado: String? = null,
    val isSolicitandoPago: Boolean = false,
    // Tab 5 — Alertas
    val patientAlerts: List<PatientAlertDto> = emptyList(),
    val alertasNoVistas: Int = 0,
    val isLoadingAlerts: Boolean = false,
    // Global
    val error: String? = null,
)

@HiltViewModel
class HomeDoctorViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenStore: TokenStore,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeDoctorUiState())
    val state: StateFlow<HomeDoctorUiState> = _state.asStateFlow()

    init {
        loadDoctorName()
        loadCitasHoy()
        checkStripeStatus()
    }

    private fun loadDoctorName() {
        viewModelScope.launch {
            try {
                val r = api.getMyDoctorProfile()
                if (r.isSuccessful) {
                    _state.update { it.copy(doctorName = r.body()?.doctor?.fullName ?: "Doctor") }
                }
            } catch (_: Exception) {}
        }
    }

    fun selectTab(tab: Int) {
        _state.update { it.copy(selectedTab = tab, error = null) }
        when (tab) {
            0 -> loadCitasHoy()
            1 -> loadPacientes()
            2 -> loadHorario()
            3 -> loadIngresos()
            4 -> loadAlerts()
        }
    }

    // ── Tab 1: Hoy ──────────────────────────────────────────────────────────
    fun loadCitasHoy() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHoy = true, error = null) }
            try {
                val r = api.getCitasHoy()
                if (r.isSuccessful && r.body() != null) {
                    _state.update { it.copy(
                        citasHoy = r.body()!!.citas,
                        ingresosHoy = r.body()!!.ingresosHoy,
                        isLoadingHoy = false,
                    )}
                } else {
                    _state.update { it.copy(isLoadingHoy = false, citasHoy = emptyList()) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingHoy = false, error = "Sin conexión") }
            }
        }
    }

    // ── Tab 2: Pacientes ────────────────────────────────────────────────────
    fun loadPacientes() {
        if (_state.value.pacientes.isNotEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoadingPacientes = true) }
            try {
                val r = api.getMisPacientes()
                if (r.isSuccessful) {
                    _state.update { it.copy(
                        pacientes = r.body()?.pacientes ?: emptyList(),
                        isLoadingPacientes = false,
                    )}
                } else {
                    _state.update { it.copy(isLoadingPacientes = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingPacientes = false, error = "Sin conexión") }
            }
        }
    }

    fun updateSearch(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun pacientesFiltrados(): List<DoctorPacienteDto> {
        val q = _state.value.searchQuery.trim().lowercase()
        return if (q.isBlank()) _state.value.pacientes
        else _state.value.pacientes.filter { it.fullName.lowercase().contains(q) }
    }

    // ── Tab 3: Agenda ────────────────────────────────────────────────────────
    private fun loadHorario() {
        viewModelScope.launch {
            try {
                val r = api.getMyDoctorProfile()
                if (r.isSuccessful) {
                    val doc = r.body()?.doctor ?: return@launch
                    // DoctorDto no trae horario aún — usar defaults o ampliar
                }
            } catch (_: Exception) {}
        }
    }

    fun toggleDiaLaborable(dia: Int) {
        val actual = _state.value.diasLaborables.toMutableList()
        if (actual.contains(dia)) actual.remove(dia) else actual.add(dia)
        _state.update { it.copy(diasLaborables = actual.sorted(), horarioGuardado = false) }
    }

    fun updateHorarioInicio(hora: String) {
        _state.update { it.copy(horarioInicio = hora, horarioGuardado = false) }
    }

    fun updateHorarioFin(hora: String) {
        _state.update { it.copy(horarioFin = hora, horarioGuardado = false) }
    }

    fun guardarHorario() {
        viewModelScope.launch {
            _state.update { it.copy(isSavingHorario = true) }
            try {
                val r = api.updateHorario(UpdateHorarioRequest(
                    horarioInicio = _state.value.horarioInicio,
                    horarioFin = _state.value.horarioFin,
                    diasLaborables = _state.value.diasLaborables,
                ))
                _state.update { it.copy(
                    isSavingHorario = false,
                    horarioGuardado = r.isSuccessful,
                    error = if (!r.isSuccessful) "Error al guardar horario" else null,
                )}
            } catch (e: Exception) {
                _state.update { it.copy(isSavingHorario = false, error = "Sin conexión") }
            }
        }
    }

    // ── Tab 4: Ingresos ─────────────────────────────────────────────────────
    fun loadIngresos() {
        if (_state.value.payouts.isNotEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoadingIngresos = true) }
            try {
                val r = api.getIngresos()
                if (r.isSuccessful && r.body() != null) {
                    _state.update { it.copy(
                        totalMes = r.body()!!.totalMes,
                        totalPendiente = r.body()!!.totalPendiente,
                        payouts = r.body()!!.payouts,
                        isLoadingIngresos = false,
                    )}
                } else {
                    _state.update { it.copy(isLoadingIngresos = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingIngresos = false, error = "Sin conexión") }
            }
        }
    }

    fun solicitarPago() {
        viewModelScope.launch {
            _state.update { it.copy(isSolicitandoPago = true, error = null) }
            try {
                val r = api.requestPayout()
                if (r.isSuccessful && r.body() != null) {
                    _state.update { it.copy(
                        isSolicitandoPago = false,
                        payoutEnviado = r.body()!!.mensaje,
                        totalPendiente = 0.0,
                    )}
                } else {
                    _state.update { it.copy(isSolicitandoPago = false, error = "Error al solicitar pago") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isSolicitandoPago = false, error = "Sin conexión") }
            }
        }
    }

    fun dismissPayoutMessage() {
        _state.update { it.copy(payoutEnviado = null) }
    }

    // ── Tab 5: Alertas ──────────────────────────────────────────────────────
    fun loadAlerts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingAlerts = true) }
            try {
                val r = api.getPatientAlerts()
                if (r.isSuccessful && r.body() != null) {
                    _state.update { it.copy(
                        patientAlerts = r.body()!!.alertas,
                        alertasNoVistas = r.body()!!.noVistas,
                        isLoadingAlerts = false,
                    )}
                } else {
                    _state.update { it.copy(isLoadingAlerts = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingAlerts = false, error = "Sin conexión") }
            }
        }
    }

    // ── Stripe Connect ──────────────────────────────────────────────────────
    fun checkStripeStatus() {
        viewModelScope.launch {
            try {
                val r = api.stripeConnectStatus()
                if (r.isSuccessful && r.body() != null) {
                    _state.update { it.copy(stripeStatus = r.body()!!.status) }
                }
            } catch (_: Exception) {}
        }
    }

    fun iniciarStripeConnect(onUrlReady: (String) -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingStripe = true) }
            try {
                val r = api.stripeConnectCreate()
                if (r.isSuccessful && r.body() != null) {
                    val url = r.body()!!.onboardingUrl
                    if (url != null) {
                        _state.update { it.copy(isLoadingStripe = false, stripeOnboardingUrl = url) }
                        onUrlReady(url)
                    } else {
                        // Ya activo
                        _state.update { it.copy(isLoadingStripe = false, stripeStatus = "active") }
                    }
                } else {
                    _state.update { it.copy(isLoadingStripe = false, error = "Error al conectar con Stripe") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingStripe = false, error = "Sin conexión") }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
