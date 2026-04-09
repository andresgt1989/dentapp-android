package com.dentapp.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlagsResponse(
    val success: Boolean,
    val flags: FeatureFlags,
    val plan: String = "free",
    val version: String = "1.0.0",
)

@Serializable
data class FeatureFlags(
    // AI
    @SerialName("ai_chat")           val aiChat: Boolean = true,
    @SerialName("ai_photo_analysis") val aiPhotoAnalysis: Boolean = true,
    @SerialName("ai_diagnosis")      val aiDiagnosis: Boolean = false,
    @SerialName("ai_daily_summary")  val aiDailySummary: Boolean = true,
    // Booking
    @SerialName("booking_enabled")   val bookingEnabled: Boolean = true,
    @SerialName("video_call")        val videoCall: Boolean = false,
    @SerialName("urgency_alert")     val urgencyAlert: Boolean = true,
    // Paciente
    @SerialName("xray_capture")      val xrayCapture: Boolean = false,
    @SerialName("ocr_prescriptions") val ocrPrescriptions: Boolean = false,
    @SerialName("qr_share")          val qrShare: Boolean = true,
    @SerialName("loyalty_program")   val loyaltyProgram: Boolean = true,
    // Monetización
    @SerialName("subscriptions")     val subscriptions: Boolean = true,
    @SerialName("auto_trial")        val autoTrial: Boolean = true,
    // Push
    @SerialName("push_daily_ai")     val pushDailyAi: Boolean = true,
    @SerialName("push_appointment_24h") val pushAppointment24h: Boolean = true,
    // Mapa
    @SerialName("map_clinics")       val mapClinics: Boolean = false,
    // Doctor
    @SerialName("stripe_connect")    val stripeConnect: Boolean = true,
    @SerialName("doctor_analytics")  val doctorAnalytics: Boolean = false,
)

@Serializable
data class AnalyticsEvent(
    val event: String,
    val screen: String? = null,
    val properties: Map<String, String> = emptyMap(),
    @SerialName("app_version") val appVersion: String? = null,
)
