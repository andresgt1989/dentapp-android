package com.dentapp.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.OnboardingRequest
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val role: String = "",
    val country: String = "",
    val lastVisit: String = "",
    val medicalCondition: String = "",
    val inviteCode: String = "",
    val isSaving: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenStore: TokenStore,
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingUiState())
    val state = _state.asStateFlow()

    fun setRole(role: String)                = _state.update { it.copy(role = role) }
    fun setCountry(country: String)          = _state.update { it.copy(country = country) }
    fun setLastVisit(visit: String)          = _state.update { it.copy(lastVisit = visit) }
    fun setMedicalCondition(cond: String)    = _state.update { it.copy(medicalCondition = cond) }
    fun setInviteCode(code: String)          = _state.update { it.copy(inviteCode = code) }

    /** Fire-and-forget: saves onboarding data + activates 14-day Pro trial. Navigation is caller's responsibility. */
    fun saveOnboarding() {
        val s = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null) }
            try {
                if (s.country.isNotBlank()) tokenStore.saveCountry(s.country)
                api.saveOnboarding(
                    OnboardingRequest(
                        country           = s.country.ifBlank { null },
                        lastVisit         = s.lastVisit.ifBlank { null },
                        medicalConditions = s.medicalCondition.ifBlank { null },
                        role              = s.role.ifBlank { null },
                    )
                )
                // Auto-activate 14-day Pro trial (fire & forget — no bloquea navegación)
                try { api.startTrial() } catch (_: Exception) {}
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }
}
