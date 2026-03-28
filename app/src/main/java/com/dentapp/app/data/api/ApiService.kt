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
}
