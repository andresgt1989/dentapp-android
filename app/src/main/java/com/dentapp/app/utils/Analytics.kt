package com.dentapp.app.utils

import com.dentapp.app.data.api.ApiService
import com.dentapp.app.data.model.AnalyticsEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fire-and-forget analytics. Nunca lanza excepciones ni bloquea el hilo principal.
 * Uso: analytics.track("booking_completed", screen = "BookingScreen", props = mapOf("amount" to "500"))
 */
@Singleton
class Analytics @Inject constructor(
    private val api: ApiService,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var isAuthenticated = false

    fun setAuthenticated(auth: Boolean) { isAuthenticated = auth }

    fun track(
        event: String,
        screen: String? = null,
        properties: Map<String, String> = emptyMap(),
    ) {
        if (!isAuthenticated) return   // No trackear sin sesión
        scope.launch {
            try {
                api.trackEvent(AnalyticsEvent(event = event, screen = screen, properties = properties))
            } catch (_: Exception) { /* silencioso — analytics nunca rompe el app */ }
        }
    }

    // Shortcuts para eventos críticos de KPIs
    fun screenView(screen: String) = track("screen_view", screen = screen)
    fun aiSessionStarted() = track("ai_session_started", screen = "AiManagerScreen")
    fun aiMessageSent(isChip: Boolean) = track("ai_message_sent", properties = mapOf("is_chip" to isChip.toString()))
    fun bookingStarted(doctorId: String) = track("booking_started", properties = mapOf("doctor_id" to doctorId))
    fun bookingCompleted(amountCents: Int) = track("booking_completed", properties = mapOf("amount_cents" to amountCents.toString()))
    fun subscriptionScreenViewed() = track("subscription_screen_viewed", screen = "SubscriptionScreen")
    fun subscriptionPlanSelected(plan: String) = track("subscription_plan_selected", properties = mapOf("plan" to plan))
    fun onboardingCompleted() = track("onboarding_completed")
}
