package com.dentapp.app.utils

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.FeatureFlags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton que mantiene los feature flags sincronizados con el backend.
 * - Defaults: todos los flags están en sus valores seguros (off para features inestables)
 * - Fetch en startup + refresh post-login
 * - Si el backend falla, usa defaults → nunca rompe el app
 */
@Singleton
class FeatureFlagManager @Inject constructor(
    private val api: ApiService,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var _flags = FeatureFlags()          // defaults safe
    val flags: FeatureFlags get() = _flags

    // Llamar en startup (sin auth) — flags base públicos
    fun fetchPublic() {
        scope.launch {
            try {
                val r = api.getFeatureFlags()
                if (r.isSuccessful) r.body()?.flags?.let { _flags = it }
            } catch (_: Exception) { /* usar defaults */ }
        }
    }

    // Llamar post-login — flags personalizados por usuario/plan
    fun fetchForUser() {
        scope.launch {
            try {
                val r = api.getMyFeatureFlags()
                if (r.isSuccessful) r.body()?.flags?.let { _flags = it }
            } catch (_: Exception) { /* mantener flags previos */ }
        }
    }

    fun isEnabled(flag: String): Boolean = when (flag) {
        "ai_chat"              -> _flags.aiChat
        "ai_photo_analysis"    -> _flags.aiPhotoAnalysis
        "ai_diagnosis"         -> _flags.aiDiagnosis
        "ai_daily_summary"     -> _flags.aiDailySummary
        "booking_enabled"      -> _flags.bookingEnabled
        "video_call"           -> _flags.videoCall
        "urgency_alert"        -> _flags.urgencyAlert
        "xray_capture"         -> _flags.xrayCapture
        "ocr_prescriptions"    -> _flags.ocrPrescriptions
        "qr_share"             -> _flags.qrShare
        "loyalty_program"      -> _flags.loyaltyProgram
        "subscriptions"        -> _flags.subscriptions
        "auto_trial"           -> _flags.autoTrial
        "push_daily_ai"        -> _flags.pushDailyAi
        "map_clinics"          -> _flags.mapClinics
        "stripe_connect"       -> _flags.stripeConnect
        "doctor_analytics"     -> _flags.doctorAnalytics
        else                   -> false
    }
}
