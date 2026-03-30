package com.dentapp.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── AI Manager ───────────────────────────────────────────────────────────────

@Serializable
data class AiChatRequest(val message: String)

@Serializable
data class AiChatResponse(
    val reply: String,
    @SerialName("diasPostCirugia") val diasPostCirugia: Int = 0,
    @SerialName("tokensUsed") val tokensUsed: Int = 0,
)

@Serializable
data class AiContextResponse(
    @SerialName("diasPostCirugia") val diasPostCirugia: Int,
    val paciente: PacienteInfo,
    val medicacion: List<MedicacionStatus>,
    val perfil: PerfilSalud,
)

@Serializable
data class PacienteInfo(val nombre: String)

@Serializable
data class MedicacionStatus(
    val medicamento: String,
    val dosis: String,
    @SerialName("frecuencia_hrs") val frecuenciaHrs: Int,
    @SerialName("dias_total") val diasTotal: Int,
    @SerialName("adherencia_pct") val adherenciaPct: Int,
    val completado: Boolean,
)

@Serializable
data class PerfilSalud(
    val tabaco: String? = null,
    val frecTabaco: String? = null,
    val enciaSensible: Boolean = false,
    val usaHiloDental: Boolean = false,
    val usaEnjuague: Boolean = false,
)

@Serializable
data class AiHistoryResponse(val messages: List<AiMessage>)

@Serializable
data class AiMessage(
    val role: String,   // "user" | "assistant"
    val content: String,
    @SerialName("created_at") val createdAt: String = "",
)
