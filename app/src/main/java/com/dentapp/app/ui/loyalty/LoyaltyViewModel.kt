package com.dentapp.app.ui.loyalty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.LoyaltyBalanceResponse
import com.dentapp.app.data.model.LoyaltyTransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoyaltyUiState(
    val isLoading: Boolean = false,
    val balance: LoyaltyBalanceResponse? = null,
    val history: List<LoyaltyTransactionDto> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class LoyaltyViewModel @Inject constructor(
    private val api: ApiService,
) : ViewModel() {

    private val _state = MutableStateFlow(LoyaltyUiState())
    val state: StateFlow<LoyaltyUiState> = _state.asStateFlow()

    init { cargarDatos() }

    fun cargarDatos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val balanceRes = api.getLoyaltyBalance()
                val historyRes = api.getLoyaltyHistory()
                _state.update {
                    it.copy(
                        isLoading = false,
                        balance = if (balanceRes.isSuccessful) balanceRes.body() else null,
                        history = if (historyRes.isSuccessful) historyRes.body()?.history ?: emptyList() else emptyList(),
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Sin conexión") }
            }
        }
    }
}
