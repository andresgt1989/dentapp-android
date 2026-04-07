package com.dentapp.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── QR / Share token ─────────────────────────────────────────────────────────

@Serializable
data class ShareTokenRequest(
    val duracion: String, // "1h" | "24h" | "7d" | "forever"
)

@Serializable
data class ShareTokenResponse(
    val token: String,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("shareUrl") val shareUrl: String,
)

@Serializable
data class ShareTokensResponse(
    val tokens: List<ShareTokenDto>,
)

@Serializable
data class ShareTokenDto(
    val id: String,
    val token: String,
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("views_count") val viewsCount: Int = 0,
    @SerialName("created_at") val createdAt: String,
)

// ── Radiografía ───────────────────────────────────────────────────────────────

@Serializable
data class RadiografiaUploadRequest(
    @SerialName("imagenBase64") val imagenBase64: String,
    @SerialName("mimeType") val mimeType: String = "image/jpeg",
)

@Serializable
data class RadiografiaResponse(
    val ok: Boolean,
    val tipo: String? = null,
    val zona: String? = null,
    val calidad: String? = null,
    @SerialName("razon_calidad") val razonCalidad: String? = null,
    val id: String? = null,
)

// ── Receta OCR ────────────────────────────────────────────────────────────────

@Serializable
data class RecetaOcrRequest(
    @SerialName("imagenBase64") val imagenBase64: String,
    @SerialName("mimeType") val mimeType: String = "image/jpeg",
)

@Serializable
data class MedicamentoOcr(
    val nombre: String,
    val dosis: String? = null,
    @SerialName("frecuencia_horas") val frecuenciaHoras: Int = 8,
    @SerialName("duracion_dias") val duracionDias: Int = 5,
    val instrucciones: String? = null,
    @SerialName("alertas_criticas") val alertasCriticas: List<String> = emptyList(),
    @SerialName("tracking_id") val trackingId: String? = null,
)

@Serializable
data class RecetaOcrResponse(
    val ok: Boolean,
    @SerialName("receta_id") val recetaId: String? = null,
    val medicamentos: List<MedicamentoOcr> = emptyList(),
    @SerialName("indicaciones_especiales") val indicacionesEspeciales: String? = null,
    @SerialName("tipo_procedimiento") val tipoProcedimiento: String? = null,
    @SerialName("recordatorios_activados") val recordatoriosActivados: Int = 0,
)

@Serializable
data class RecetasResponse(val recetas: List<RecetaDto>)

@Serializable
data class RecetaDto(
    val id: String,
    val medico: String? = null,
    @SerialName("fecha_receta") val fechaReceta: String? = null,
    @SerialName("tipo_procedimiento") val tipoProcedimiento: String? = null,
    val medicamentos: List<MedicamentoOcr> = emptyList(),
    @SerialName("created_at") val createdAt: String,
)

// ── AI Feedback ───────────────────────────────────────────────────────────────

@Serializable
data class AiFeedbackRequest(
    @SerialName("conversation_id") val conversationId: String? = null,
    val rating: Int,
    @SerialName("fue_util") val fueUtil: Boolean,
    @SerialName("accion_tomada") val accionTomada: String? = null,
    val comentario: String? = null,
)

@Serializable
data class AiFeedbackResponse(
    val ok: Boolean,
    @SerialName("puntos_ganados") val puntosGanados: Int = 0,
)

// ── Loyalty ───────────────────────────────────────────────────────────────────

@Serializable
data class LoyaltyBalanceResponse(
    val puntos: Int,
    val nivel: String,
    @SerialName("nivel_nombre") val nivelNombre: String,
    @SerialName("siguiente_nivel") val siguienteNivel: String? = null,
    @SerialName("puntos_para_siguiente") val puntosParaSiguiente: Int = 0,
    @SerialName("progreso_pct") val progresoPct: Int = 0,
    @SerialName("codigo_referido") val codigoReferido: String? = null,
)

@Serializable
data class LoyaltyHistoryResponse(val history: List<LoyaltyTransactionDto>)

@Serializable
data class LoyaltyTransactionDto(
    val puntos: Int,
    val tipo: String,
    val descripcion: String? = null,
    @SerialName("created_at") val createdAt: String,
)

// ── Subscription Trial ────────────────────────────────────────────────────────

@Serializable
data class StartTrialResponse(
    val ok: Boolean,
    @SerialName("trial_ends_at") val trialEndsAt: String,
    val plan: String,
)
