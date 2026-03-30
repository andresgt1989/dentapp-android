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

@Serializable
data class GoogleAuthRequest(
    val token: String,
    @SerialName("fcmToken") val fcmToken: String? = null,
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

// ── Consultation Types ────────────────────────────────────────────────────────

@Serializable
data class ConsultationTypeDto(
    val id: String,
    val nombre: String,
    val descripcion: String? = null,
    val modalidad: String,  // "video" | "presencial" | "chat"
    @SerialName("duracion_min") val duracionMin: Int,
    @SerialName("precio_centavos") val precioCentavos: Int,
)

@Serializable
data class ConsultationTypesResponse(val types: List<ConsultationTypeDto>)

@Serializable
data class CreateVideoConsultaRequest(
    val doctorId: String,
    val consultationTypeId: String,
    val fecha: String,
    val hora: String,
)

@Serializable
data class VideoConsultaResponse(
    val reservaId: String,
    @SerialName("clientSecret") val clientSecret: String,
    val roomUrl: String? = null,
    val roomName: String? = null,
    val precio: Double,
)

// ── Dental Record / Expediente ────────────────────────────────────────────────

@Serializable
data class DentalRecordDto(
    val id: String,
    @SerialName("patient_id") val patientId: String,
    val fecha: String,
    val procedimiento: String,
    val descripcion: String? = null,
    val diente: String? = null,
    @SerialName("material_usado") val materialUsado: String? = null,
    val costo: Double? = null,
    @SerialName("doctor_name") val doctorName: String? = null,
    @SerialName("clinica_nombre") val clinicaNombre: String? = null,
)

@Serializable
data class DentalRecordsResponse(val records: List<DentalRecordDto>)

// ── AI Photo Analysis ─────────────────────────────────────────────────────────

@Serializable
data class AnalyzePhotoRequest(
    val imageBase64: String,
    val mimeType: String = "image/jpeg",
)

@Serializable
data class AnalyzePhotoResponse(
    val analysis: String,
    val disclaimer: Boolean = true,
)

// ── Urgencia ──────────────────────────────────────────────────────────────────

@Serializable
data class UrgenciaRequest(
    val descripcion: String,
    val lat: Double? = null,
    val lng: Double? = null,
)

@Serializable
data class UrgenciaResponse(
    val notificados: Int,
    @SerialName("consultation_id") val consultationId: String? = null,
)

// ── Loyalty ───────────────────────────────────────────────────────────────────

@Serializable
data class LoyaltyStatusResponse(
    val balance: Int,
    val nivel: String,
)
