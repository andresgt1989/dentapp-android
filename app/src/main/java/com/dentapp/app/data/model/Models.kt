package com.dentapp.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── Auth ────────────────────────────────────────────────────────────────────

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String,           // "doctor" | "patient"
    @SerialName("fullName") val fullName: String,
    val phone: String? = null,
    val specialty: String? = null,
    @SerialName("licenseNumber") val licenseNumber: String? = null,
    @SerialName("dateOfBirth") val dateOfBirth: String? = null,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    @SerialName("fcmToken") val fcmToken: String? = null,
)

@Serializable
data class AuthResponse(
    val token: String,
    val user: UserDto,
    val profile: ProfileDto? = null,
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val role: String,
)

@Serializable
data class ProfileDto(
    val id: String,
    @SerialName("full_name") val fullName: String? = null,
    val specialty: String? = null,
    @SerialName("license_number") val licenseNumber: String? = null,
    val phone: String? = null,
)

// ── Doctor ───────────────────────────────────────────────────────────────────

@Serializable
data class DoctorDto(
    val id: String,
    @SerialName("full_name") val fullName: String,
    val specialty: String,
    val bio: String? = null,
    val phone: String? = null,
    @SerialName("consultation_fee") val consultationFee: Double = 0.0,
    @SerialName("is_available") val isAvailable: Boolean = true,
    @SerialName("bank_name") val bankName: String? = null,
    @SerialName("bank_account") val bankAccount: String? = null,
    @SerialName("wise_email") val wiseEmail: String? = null,
)

@Serializable
data class DoctorsResponse(val doctors: List<DoctorDto>)

@Serializable
data class DoctorResponse(val doctor: DoctorDto)

@Serializable
data class UpdateBankInfoRequest(
    @SerialName("bank_name") val bankName: String,
    @SerialName("bank_account") val bankAccount: String,
    @SerialName("bank_account_type") val bankAccountType: String = "corriente",
    @SerialName("bank_id_type") val bankIdType: String = "cedula",
    @SerialName("bank_id_number") val bankIdNumber: String,
    @SerialName("wise_email") val wiseEmail: String? = null,
)

// ── Patient ───────────────────────────────────────────────────────────────────

@Serializable
data class PatientDto(
    val id: String,
    @SerialName("full_name") val fullName: String,
    val phone: String? = null,
    @SerialName("date_of_birth") val dateOfBirth: String? = null,
    val address: String? = null,
    @SerialName("emergency_contact") val emergencyContact: String? = null,
    @SerialName("emergency_phone") val emergencyPhone: String? = null,
    @SerialName("medical_notes") val medicalNotes: String? = null,
)

@Serializable
data class PatientResponse(val patient: PatientDto)

// ── Appointment ───────────────────────────────────────────────────────────────

@Serializable
data class AppointmentDto(
    val id: String,
    @SerialName("scheduled_at") val scheduledAt: String,
    @SerialName("duration_minutes") val durationMinutes: Int = 30,
    val status: String,
    val notes: String? = null,
    @SerialName("doctor_name") val doctorName: String? = null,
    @SerialName("patient_name") val patientName: String? = null,
    val specialty: String? = null,
)

@Serializable
data class AppointmentsResponse(val appointments: List<AppointmentDto>)

@Serializable
data class CreateAppointmentRequest(
    @SerialName("doctorId") val doctorId: String,
    @SerialName("scheduledAt") val scheduledAt: String,
    @SerialName("durationMinutes") val durationMinutes: Int = 30,
    val notes: String? = null,
)

// ── Payment ───────────────────────────────────────────────────────────────────

@Serializable
data class CreatePaymentIntentRequest(
    @SerialName("appointmentId") val appointmentId: String,
)

@Serializable
data class PaymentIntentResponse(
    @SerialName("clientSecret") val clientSecret: String,
    @SerialName("paymentId") val paymentId: String,
    @SerialName("amountTotal") val amountTotal: Double,
    val split: SplitDto,
)

@Serializable
data class SplitDto(
    val doctor: Double,
    val platform: Double,
)

// ── Generic ───────────────────────────────────────────────────────────────────

@Serializable
data class MessageResponse(val message: String)

@Serializable
data class ErrorResponse(val error: String)
