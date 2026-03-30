package com.dentapp.app.data.repository

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiRepository @Inject constructor(private val api: ApiService) {

    suspend fun chat(message: String): Result<AiChatResponse> = runCatching {
        val res = api.aiChat(AiChatRequest(message))
        if (res.isSuccessful) res.body()!!
        else throw Exception(res.errorBody()?.string() ?: "Error ${res.code()}")
    }

    suspend fun getContext(): Result<AiContextResponse> = runCatching {
        val res = api.aiContext()
        if (res.isSuccessful) res.body()!!
        else throw Exception("Sin procedimiento activo")
    }

    suspend fun getHistory(): Result<AiHistoryResponse> = runCatching {
        val res = api.aiHistory()
        if (res.isSuccessful) res.body()!!
        else throw Exception("Error cargando historial")
    }
}
