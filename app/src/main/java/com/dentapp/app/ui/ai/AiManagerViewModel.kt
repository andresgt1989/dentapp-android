package com.dentapp.app.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.model.*
import com.dentapp.app.data.repository.AiRepository
import com.dentapp.app.data.repository.Result
import kotlinx.coroutines.delay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiUiState(
    val messages: List<AiMessage> = emptyList(),
    val context: AiContextResponse? = null,
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val error: String? = null,
    val lastFeedbackPoints: Int? = null,
)

@HiltViewModel
class AiManagerViewModel @Inject constructor(
    private val repo: AiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AiUiState())
    val state: StateFlow<AiUiState> = _state.asStateFlow()

    init {
        loadHistory()
        loadContext()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val r = repo.getHistory()) {
                is Result.Success -> _state.update { it.copy(messages = r.data.messages, isLoading = false) }
                is Result.Error   -> _state.update { it.copy(isLoading = false) }
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

    fun sendMessage(text: String) {
        if (text.isBlank() || _state.value.isSending) return

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
