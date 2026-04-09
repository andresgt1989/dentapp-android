package com.dentapp.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentapp.app.data.model.RegisterRequest
import com.dentapp.app.data.repository.AuthRepository
import com.dentapp.app.data.repository.Result
import com.dentapp.app.utils.Analytics
import com.dentapp.app.utils.FeatureFlagManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val role: String? = null,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val featureFlags: FeatureFlagManager,
    private val analytics: Analytics,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (!validate(email, password)) return
        viewModelScope.launch {
            _state.value = AuthUiState(isLoading = true)
            when (val r = repo.login(email.trim(), password.trim())) {
                is Result.Success -> {
                    onLoginSuccess()
                    _state.value = AuthUiState(success = true, role = r.data.user.role)
                }
                is Result.Error   -> _state.value = AuthUiState(error = r.message)
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.value = AuthUiState(isLoading = true)
            when (val r = repo.googleAuth(idToken)) {
                is Result.Success -> {
                    onLoginSuccess()
                    _state.value = AuthUiState(success = true, role = r.data.user.role)
                }
                is Result.Error   -> _state.value = AuthUiState(error = r.message)
            }
        }
    }

    fun registerPatient(
        email: String, password: String, fullName: String,
        phone: String, dateOfBirth: String,
    ) {
        viewModelScope.launch {
            _state.value = AuthUiState(isLoading = true)
            val req = RegisterRequest(
                email = email.trim(), password = password,
                role = "patient", fullName = fullName.trim(),
                phone = phone, dateOfBirth = dateOfBirth,
            )
            when (val r = repo.register(req)) {
                is Result.Success -> _state.value = AuthUiState(success = true, role = "patient")
                is Result.Error   -> _state.value = AuthUiState(error = r.message)
            }
        }
    }

    fun registerDoctor(
        email: String, password: String, fullName: String,
        phone: String, specialty: String, licenseNumber: String,
    ) {
        viewModelScope.launch {
            _state.value = AuthUiState(isLoading = true)
            val req = RegisterRequest(
                email = email.trim(), password = password,
                role = "doctor", fullName = fullName.trim(),
                phone = phone, specialty = specialty, licenseNumber = licenseNumber,
            )
            when (val r = repo.register(req)) {
                is Result.Success -> _state.value = AuthUiState(success = true, role = "doctor")
                is Result.Error   -> _state.value = AuthUiState(error = r.message)
            }
        }
    }

    private fun onLoginSuccess() {
        analytics.setAuthenticated(true)
        analytics.track("app_open")
        featureFlags.fetchForUser()
    }

    fun setError(msg: String) { _state.value = AuthUiState(error = msg) }

    fun clearError() { _state.value = _state.value.copy(error = null) }

    private fun validate(email: String, password: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (email.isBlank() || !emailRegex.matches(email.trim())) {
            _state.value = AuthUiState(error = "Email inválido")
            return false
        }
        if (password.trim().length < 6) {
            _state.value = AuthUiState(error = "La contraseña debe tener al menos 6 caracteres")
            return false
        }
        return true
    }
}
