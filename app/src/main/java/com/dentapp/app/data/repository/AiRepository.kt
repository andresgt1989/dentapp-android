package com.dentapp.app.data.repository

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiRepository @Inject constructor(private val api: ApiService) {

    suspend fun chat(message: String): Result<AiChatResponse> {
        return try {
            val res = api.aiChat(AiChatRequest(message))
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error(res.errorBody()?.string() ?: "Error ${res.code()}")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Sin conexión")
        }
    }

    suspend fun getContext(): Result<AiContextResponse> {
        return try {
            val res = api.aiContext()
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Sin procedimiento activo")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Sin conexión")
        }
    }

    suspend fun getHistory(): Result<AiHistoryResponse> {
        return try {
            val res = api.aiHistory()
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Error cargando historial")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Sin conexión")
        }
    }
}
