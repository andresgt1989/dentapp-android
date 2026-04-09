package com.dentapp.app.ui.expediente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.DentalRecordDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExpedienteUiState(
    val records: List<DentalRecordDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val patientName: String = "",
)

@HiltViewModel
class ExpedienteViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(ExpedienteUiState())
    val state: StateFlow<ExpedienteUiState> = _state.asStateFlow()

    init {
        loadRecords()
    }

    fun loadRecords(patientId: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val r = if (patientId != null) {
                    api.getDentalRecordsByPatient(patientId)
                } else {
                    api.getDentalRecords()
                }
                if (r.isSuccessful) {
                    _state.update {
                        it.copy(
                            records = r.body()?.records ?: emptyList(),
                            isLoading = false,
                        )
                    }
                } else {
                    _state.update { it.copy(isLoading = false, error = "Error al cargar expediente") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión. Verifica tu internet.") }
            }
        }
    }

    fun clearError() = _state.update { it.copy(error = null) }
}
