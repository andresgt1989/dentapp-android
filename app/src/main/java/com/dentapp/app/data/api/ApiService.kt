package com.dentapp.app.data.api

import com.dentapp.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ── Auth ──────────────────────────────────────────────────────────────────
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/google")
    suspend fun googleAuth(@Body request: GoogleAuthRequest): Response<AuthResponse>

    @PATCH("api/auth/fcm-token")
    suspend fun updateFcmToken(@Body body: Map<String, String>): Response<MessageResponse>

    // ── Doctors ───────────────────────────────────────────────────────────────
    @GET("api/doctors")
    suspend fun getDoctors(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("specialty") specialty: String? = null,
    ): Response<DoctorsResponse>

    @GET("api/doctors/{id}")
    suspend fun getDoctorById(@Path("id") id: String): Response<DoctorResponse>

    @GET("api/doctors/me")
    suspend fun getMyDoctorProfile(): Response<DoctorResponse>

    @PATCH("api/doctors/me")
    suspend fun updateDoctorProfile(@Body body: Map<String, String>): Response<DoctorResponse>

    @PATCH("api/doctors/me/bank-info")
    suspend fun updateBankInfo(@Body request: UpdateBankInfoRequest): Response<DoctorResponse>

    @GET("api/doctors/me/appointments")
    suspend fun getDoctorAppointments(): Response<AppointmentsResponse>

    @GET("api/doctors/hoy")
    suspend fun getCitasHoy(): Response<CitasHoyResponse>

    @GET("api/doctors/mis-pacientes")
    suspend fun getMisPacientes(): Response<MisPacientesResponse>

    @GET("api/doctors/patient-alerts")
    suspend fun getPatientAlerts(): Response<PatientAlertsResponse>

    @PUT("api/doctors/horario")
    suspend fun updateHorario(@Body request: UpdateHorarioRequest): Response<HorarioResponse>

    @GET("api/doctors/ingresos")
    suspend fun getIngresos(): Response<IngresosResponse>

    @POST("api/payments/connect/request-payout")
    suspend fun requestPayout(): Response<RequestPayoutResponse>

    @POST("api/payments/connect/create")
    suspend fun stripeConnectCreate(): Response<StripeConnectCreateResponse>

    @GET("api/payments/connect/status")
    suspend fun stripeConnectStatus(): Response<StripeConnectStatusResponse>

    // ── Patients ──────────────────────────────────────────────────────────────
    @GET("api/patients/me")
    suspend fun getMyPatientProfile(): Response<PatientResponse>

    @PATCH("api/patients/me")
    suspend fun updatePatientProfile(@Body body: Map<String, String>): Response<PatientResponse>

    @GET("api/patients/me/appointments")
    suspend fun getPatientAppointments(): Response<AppointmentsResponse>

    // ── Appointments ──────────────────────────────────────────────────────────
    @POST("api/appointments")
    suspend fun createAppointment(@Body request: CreateAppointmentRequest): Response<AppointmentDto>

    @PATCH("api/appointments/{id}/cancel")
    suspend fun cancelAppointment(@Path("id") id: String): Response<AppointmentDto>

    // ── Payments ──────────────────────────────────────────────────────────────
    @POST("api/payments/create-intent")
    suspend fun createPaymentIntent(@Body request: CreatePaymentIntentRequest): Response<PaymentIntentResponse>

    // ── AI Manager ────────────────────────────────────────────────────────────
    @POST("api/ai/chat")
    suspend fun aiChat(@Body request: AiChatRequest): Response<AiChatResponse>

    @GET("api/ai/context")
    suspend fun aiContext(): Response<AiContextResponse>

    @GET("api/ai/history")
    suspend fun aiHistory(): Response<AiHistoryResponse>

    @POST("api/ai/start")
    suspend fun startConversation(): Response<AiStartResponse>

    // ── Subscriptions ─────────────────────────────────────────────────────────
    @GET("api/subscriptions/plans")
    suspend fun getPlans(): Response<PlansResponse>

    @GET("api/subscriptions/status")
    suspend fun getSubscriptionStatus(): Response<SubscriptionStatusResponse>

    // ── Admin ─────────────────────────────────────────────────────────────────
    @GET("api/admin/stats")
    suspend fun getAdminStats(): Response<AdminStatsResponse>

    // ── Account ───────────────────────────────────────────────────────────────
    @DELETE("api/users/me")
    suspend fun deleteAccount(): Response<Unit>

    // ── Consultation Types ────────────────────────────────────────────────────
    @GET("api/consultas/tipos")
    suspend fun getConsultationTypes(): Response<ConsultationTypesResponse>

    @POST("api/consultas/video")
    suspend fun createVideoConsulta(@Body request: CreateVideoConsultaRequest): Response<VideoConsultaResponse>

    @POST("api/consultas/urgencia")
    suspend fun sendUrgencia(@Body request: UrgenciaRequest): Response<UrgenciaResponse>

    // ── Health Profile ────────────────────────────────────────────────────────
    @GET("api/patients/me/health-profile")
    suspend fun getHealthProfile(): Response<HealthProfileResponse>

    @PUT("api/patients/me/health-profile")
    suspend fun updateHealthProfile(@Body body: Map<String, @JvmSuppressWildcards Any?>): Response<HealthProfileResponse>

    // ── Patient Appointments (cancel) ─────────────────────────────────────────
    @PATCH("api/appointments/{id}/cancel")
    suspend fun cancelPatientAppointment(@Path("id") id: String): Response<AppointmentDto>

    // ── Dental Records / Expediente ───────────────────────────────────────────
    @GET("api/expediente")
    suspend fun getDentalRecords(): Response<DentalRecordsResponse>

    @GET("api/expediente/paciente/{patientId}")
    suspend fun getDentalRecordsByPatient(@Path("patientId") patientId: String): Response<DentalRecordsResponse>

    // ── AI Photo Analysis ─────────────────────────────────────────────────────
    @POST("api/ai/analyze-photo")
    suspend fun analyzePhoto(@Body request: AnalyzePhotoRequest): Response<AnalyzePhotoResponse>

    // ── Tratamientos ──────────────────────────────────────────────────────────
    @GET("api/patients/me/tratamientos")
    suspend fun getTratamientos(): Response<com.dentapp.app.ui.tratamiento.TratamientosResponse>

    // ── Alertas clínicas ──────────────────────────────────────────────────────
    @GET("api/patients/me/clinical-alerts")
    suspend fun getClinicalAlerts(): Response<ClinicalAlertsResponse>

    // ── Confirmar medicamento ─────────────────────────────────────────────────
    @POST("api/ai/confirm-medication")
    suspend fun confirmMedication(@Body request: ConfirmMedicationRequest): Response<ConfirmMedicationResponse>

    // ── QR / Share token ──────────────────────────────────────────────────────
    @POST("api/expediente/share-token")
    suspend fun createShareToken(@Body request: ShareTokenRequest): Response<ShareTokenResponse>

    @GET("api/expediente/share-tokens/mis")
    suspend fun getMisShareTokens(): Response<ShareTokensResponse>

    // ── Radiografías ──────────────────────────────────────────────────────────
    @POST("api/ai/analyze-photo")
    suspend fun subirRadiografia(@Body request: RadiografiaUploadRequest): Response<RadiografiaResponse>

    // ── Recetas OCR ───────────────────────────────────────────────────────────
    @POST("api/recetas")
    suspend fun escanearReceta(@Body request: RecetaOcrRequest): Response<RecetaOcrResponse>

    @GET("api/recetas")
    suspend fun getMisRecetas(): Response<RecetasResponse>

    // ── Manual Prescriptions ──────────────────────────────────────────────────
    @POST("api/prescriptions")
    suspend fun createPrescription(@Body request: ManualPrescriptionRequest): Response<ManualPrescriptionResponse>

    // ── AI Feedback ───────────────────────────────────────────────────────────
    @POST("api/ai/feedback")
    suspend fun submitAiFeedback(@Body request: AiFeedbackRequest): Response<AiFeedbackResponse>

    // ── Loyalty ───────────────────────────────────────────────────────────────
    @GET("api/loyalty/balance")
    suspend fun getLoyaltyBalance(): Response<LoyaltyBalanceResponse>

    @GET("api/loyalty/history")
    suspend fun getLoyaltyHistory(): Response<LoyaltyHistoryResponse>

    // ── Subscription trial ────────────────────────────────────────────────────
    @POST("api/subscriptions/start-trial")
    suspend fun startTrial(): Response<StartTrialResponse>

    // ── Onboarding conversacional ─────────────────────────────────────────────
    @PATCH("api/users/onboarding")
    suspend fun saveOnboarding(@Body request: OnboardingRequest): Response<MessageResponse>

    // ── Feature flags ─────────────────────────────────────────────────────────
    @GET("api/config/features")
    suspend fun getFeatureFlags(): Response<FeatureFlagsResponse>

    @GET("api/config/features/me")
    suspend fun getMyFeatureFlags(): Response<FeatureFlagsResponse>

    // ── Analytics ────────────────────────────────────────────────────────────
    @POST("api/analytics/event")
    suspend fun trackEvent(@Body event: AnalyticsEvent): Response<MessageResponse>
}
