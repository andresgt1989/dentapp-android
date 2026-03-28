package com.dentapp.app.data.repository

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.AuthResponse
import com.dentapp.app.data.model.LoginRequest
import com.dentapp.app.data.model.RegisterRequest
import com.dentapp.app.utils.TokenStore
import javax.inject.Inject
import javax.inject.Singleton

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenStore: TokenStore,
) {
    suspend fun login(email: String, password: String, fcmToken: String? = null): Result<AuthResponse> {
        return try {
            val response = api.login(LoginRequest(email, password, fcmToken))
            if (response.isSuccessful) {
                val body = response.body()!!
                tokenStore.save(body.token, body.user.role, body.user.id)
                Result.Success(body)
            } else {
                Result.Error("Credenciales incorrectas")
            }
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                val body = response.body()!!
                tokenStore.save(body.token, body.user.role, body.user.id)
                Result.Success(body)
            } else {
                val code = response.code()
                Result.Error(if (code == 409) "El email ya está registrado" else "Error al registrar")
            }
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun logout() = tokenStore.clear()
}
