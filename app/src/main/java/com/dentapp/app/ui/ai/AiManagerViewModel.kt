package com.dentapp.app.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.model.*
import com.dentapp.app.data.repository.AiRepository
import com.dentapp.app.data.repository.Result
import com.dentapp.app.utils.Analytics
import kotlinx.coroutines.delay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiUiState(
    val messages: List<AiMessage> = emptyList(),
    val context: AiContextResponse? = null,
    val pendingAlerts: List<ClinicalAlert> = emptyList(),
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val error: String? = null,
    val lastFeedbackPoints: Int? = null,
    val confirmedMedIds: Set<String> = emptySet(),
)

@HiltViewModel
class AiManagerViewModel @Inject constructor(
    private val repo: AiRepository,
    private val analytics: Analytics,
) : ViewModel() {

    private val _state = MutableStateFlow(AiUiState())
    val state: StateFlow<AiUiState> = _state.asStateFlow()

    init {
        loadHistory()
        loadContext()
        loadPendingAlerts()
        analytics.aiSessionStarted()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getHistory()) {
                is Result.Success -> {
                    val msgs = r.data.messages
                    _state.update { it.copy(messages = msgs, isLoading = false) }
                    // IA proactiva: saluda primero si no hay historial
                    if (msgs.isEmpty()) startProactiveGreeting()
                }
                is Result.Error -> _state.update { it.copy(isLoading = false) }
            }
        }
    }

    // Llamar /api/ai/start para obtener saludo personalizado en primera apertura del día
    private fun startProactiveGreeting() {
        viewModelScope.launch {
            _state.update { it.copy(isSending = true) }
            when (val r = repo.startConversation()) {
                is Result.Success -> {
                    val text = r.data.reply.ifBlank { r.data.message }
                    if (text.isNotBlank()) {
                        val aiMsg = AiMessage(role = "assistant", content = text)
                        _state.update { it.copy(messages = listOf(aiMsg), isSending = false) }
                    } else {
                        _state.update { it.copy(isSending = false) }
                    }
                }
                is Result.Error -> _state.update { it.copy(isSending = false) }
            }
        }
    }

    fun loadContext() {
        viewModelScope.launch {
            when (val r = repo.getContext()) {
                is Result.Success -> _state.update { it.copy(context = r.data) }
                is Result.Error   -> { /* sin procedimiento activo */ }
            }
        }
    }

    private fun loadPendingAlerts() {
        viewModelScope.launch {
            when (val r = repo.getClinicalAlerts()) {
                is Result.Success -> _state.update { it.copy(pendingAlerts = r.data.alerts) }
                is Result.Error   -> { /* sin alertas */ }
            }
        }
    }

    fun sendMessage(text: String, isChip: Boolean = false) {
        if (text.isBlank() || _state.value.isSending) return
        analytics.aiMessageSent(isChip)

        val userMsg = AiMessage(role = "user", content = text)
        _state.update { it.copy(
            messages = it.messages + userMsg,
            isSending = true,
            error = null,
        )}

        viewModelScope.launch {
            when (val r = repo.chat(text)) {
                is Result.Success -> {
                    val aiMsg = AiMessage(role = "assistant", content = r.data.reply)
                    _state.update { it.copy(
                        messages = it.messages + aiMsg,
                        isSending = false,
                    )}
                }
                is Result.Error -> {
                    _state.update { it.copy(
                        isSending = false,
                        error = r.message,
                    )}
                }
            }
        }
    }

    fun confirmMedication(medicamentoId: String) {
        viewModelScope.launch {
            when (val r = repo.confirmMedication(medicamentoId)) {
                is Result.Success -> {
                    _state.update { it.copy(
                        confirmedMedIds = it.confirmedMedIds + medicamentoId,
                        lastFeedbackPoints = 5,
                    )}
                    delay(3000)
                    _state.update { it.copy(lastFeedbackPoints = null) }
                }
                is Result.Error -> { /* silencioso */ }
            }
        }
    }

    fun clearError() = _state.update { it.copy(error = null) }

    fun submitFeedback(conversationId: String?, fueUtil: Boolean, rating: Int, comentario: String? = null) {
        viewModelScope.launch {
            val result = repo.submitFeedback(
                AiFeedbackRequest(
                    conversationId = conversationId,
                    rating = rating,
                    fueUtil = fueUtil,
                    comentario = comentario,
                )
            )
            if (result is Result.Success) {
                _state.update { it.copy(lastFeedbackPoints = result.data.puntosGanados) }
                delay(3000)
                _state.update { it.copy(lastFeedbackPoints = null) }
            }
        }
    }
}
