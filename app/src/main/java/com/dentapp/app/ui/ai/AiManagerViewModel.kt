package com.dentapp.app.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.model.*
import com.dentapp.app.data.repository.AiRepository
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
            repo.getHistory()
                .onSuccess { res -> _state.update { it.copy(messages = res.messages, isLoading = false) } }
                .onFailure { _state.update { it.copy(isLoading = false) } }
        }
    }

    fun loadContext() {
        viewModelScope.launch {
            repo.getContext()
                .onSuccess { ctx -> _state.update { it.copy(context = ctx) } }
                .onFailure { /* sin procedimiento activo — no mostrar error */ }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank() || _state.value.isSending) return

        // Agregar el mensaje del usuario optimistamente
        val userMsg = AiMessage(role = "user", content = text)
        _state.update { it.copy(
            messages = it.messages + userMsg,
            isSending = true,
            error = null,
        )}

        viewModelScope.launch {
            repo.chat(text)
                .onSuccess { res ->
                    val aiMsg = AiMessage(role = "assistant", content = res.reply)
                    _state.update { it.copy(
                        messages = it.messages + aiMsg,
                        isSending = false,
                    )}
                }
                .onFailure { e ->
                    _state.update { it.copy(
                        isSending = false,
                        error = e.message ?: "Error al enviar",
                    )}
                }
        }
    }

    fun clearError() = _state.update { it.copy(error = null) }
}
