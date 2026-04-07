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
            else Result.Error("Error al enviar mensaje. Intenta de nuevo.")
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun getContext(): Result<AiContextResponse> {
        return try {
            val res = api.aiContext()
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Error al cargar datos. Intenta de nuevo.")
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun getHistory(): Result<AiHistoryResponse> {
        return try {
            val res = api.aiHistory()
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Error al cargar datos. Intenta de nuevo.")
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun startConversation(): Result<AiStartResponse> {
        return try {
            val res = api.startConversation()
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Error al cargar datos. Intenta de nuevo.")
        } catch (e: Exception) {
            Result.Error("Sin conexión. Verifica tu internet.")
        }
    }

    suspend fun submitFeedback(request: AiFeedbackRequest): Result<AiFeedbackResponse> {
        return try {
            val res = api.submitAiFeedback(request)
            if (res.isSuccessful) Result.Success(res.body()!!)
            else Result.Error("Error al enviar feedback.")
        } catch (e: Exception) {
            Result.Error("Sin conexión.")
        }
    }
}
