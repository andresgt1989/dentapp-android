package com.dentapp.app.ui.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubscriptionUiState(
    val anual: Boolean = false,
    val isLoading: Boolean = false,
    val isDoctor: Boolean = false,
    val snackbarMessage: String? = null,
    val error: String? = null,
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenStore: TokenStore,
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionUiState())
    val state: StateFlow<SubscriptionUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val role = tokenStore.role.firstOrNull()
            _state.update { it.copy(isDoctor = role == "doctor") }
        }
    }

    fun toggleAnual() = _state.update { it.copy(anual = !it.anual) }

    fun startTrial() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val res = api.startTrial()
                if (res.isSuccessful) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            snackbarMessage = "¡Trial de 14 días activado! Disfruta DentApp Pro.",
                        )
                    }
                } else {
                    val errMsg = when (res.code()) {
                        400 -> "Ya tienes un período de prueba activo"
                        else -> "Error al activar el trial. Intenta de nuevo."
                    }
                    _state.update { it.copy(isLoading = false, error = errMsg) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión") }
            }
        }
    }

    fun clearSnackbar() = _state.update { it.copy(snackbarMessage = null) }
}
