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
    // Campos extendidos del perfil público
    @SerialName("rating_promedio") val ratingPromedio: Double? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    val city: String? = null,
    @SerialName("horario_inicio") val horarioInicio: String = "08:00",
    @SerialName("horario_fin") val horarioFin: String = "18:00",
    @SerialName("dias_laborables") val diasLaborables: List<Int> = listOf(1, 2, 3, 4, 5),
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
    val email: String? = null,
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

// ── HomeDoctorScreen ──────────────────────────────────────────────────────────

@Serializable
data class DoctorCitaDto(
    val id: String,
    @SerialName("scheduled_at") val scheduledAt: String,
    @SerialName("duration_minutes") val durationMinutes: Int = 30,
    val status: String,
    val notes: String? = null,
    @SerialName("patient_name") val patientName: String? = null,
    val tipo: String? = null,
    val precio: Int? = null,
    @SerialName("monto_clinica") val montoClinica: Int? = null,
    @SerialName("reserva_id") val reservaId: String? = null,
)

@Serializable
data class CitasHoyResponse(
    val citas: List<DoctorCitaDto>,
    @SerialName("ingresos_hoy") val ingresosHoy: Double = 0.0,
)

@Serializable
data class DoctorPacienteDto(
    val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("ultima_visita") val ultimaVisita: String? = null,
    @SerialName("total_citas") val totalCitas: Int = 0,
)

@Serializable
data class MisPacientesResponse(val pacientes: List<DoctorPacienteDto>)

@Serializable
data class PatientAlertDto(
    val id: String,
    @SerialName("patient_id") val patientId: String,
    @SerialName("patient_name") val patientName: String,
    val tipo: String,
    val prioridad: String,
    val mensaje: String,
    val visto: Boolean = false,
    @SerialName("dias_pendiente") val diasPendiente: Int = 0,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class PatientAlertsResponse(
    val alertas: List<PatientAlertDto>,
    @SerialName("no_vistas") val noVistas: Int = 0,
)

@Serializable
data class DoctorPayoutDto(
    val id: String,
    val amount: Double,
    val status: String,
    @SerialName("patient_name") val patientName: String? = null,
    val fecha: String? = null,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class IngresosResponse(
    @SerialName("total_mes") val totalMes: Double = 0.0,
    @SerialName("total_pendiente") val totalPendiente: Double = 0.0,
    val payouts: List<DoctorPayoutDto> = emptyList(),
)

@Serializable
data class HorarioDto(
    @SerialName("horario_inicio") val horarioInicio: String = "08:00",
    @SerialName("horario_fin") val horarioFin: String = "18:00",
    @SerialName("dias_laborables") val diasLaborables: List<Int> = listOf(1, 2, 3, 4, 5),
)

@Serializable
data class UpdateHorarioRequest(
    @SerialName("horario_inicio") val horarioInicio: String,
    @SerialName("horario_fin") val horarioFin: String,
    @SerialName("dias_laborables") val diasLaborables: List<Int>,
)

@Serializable
data class HorarioResponse(val horario: HorarioDto)

@Serializable
data class RequestPayoutResponse(
    val ok: Boolean,
    @SerialName("monto_solicitado") val montoSolicitado: Double = 0.0,
    val mensaje: String,
)

// ── Health Profile ────────────────────────────────────────────────────────────

@Serializable
data class HealthProfileDto(
    val id: String? = null,
    @SerialName("patient_id") val patientId: String? = null,
    // Embarazo
    @SerialName("pregnancy_status") val pregnancyStatus: String? = null, // "no"|"yes"|"unknown"
    val trimester: Int? = null,
    // Cardíaco
    @SerialName("cardiac_condition") val cardiacCondition: Boolean? = null,
    @SerialName("cardiac_detail") val cardiacDetail: String? = null,
    // Bifosfonatos
    @SerialName("takes_bisphosphonates") val takesBisphosphonates: Boolean? = null,
    @SerialName("bisphosphonate_name") val bisphosphonateName: String? = null,
    @SerialName("bisphosphonate_route") val bisphosphonateRoute: String? = null, // "oral"|"iv"
    // Renal / Hepático
    @SerialName("renal_insufficiency") val renalInsufficiency: Boolean? = null,
    @SerialName("hepatic_insufficiency") val hepaticInsufficiency: Boolean? = null,
    // Hematológico
    val hemophilia: Boolean? = null,
    val pacemaker: Boolean? = null,
    // Infeccioso / Oncológico
    @SerialName("hiv_status") val hivStatus: Boolean? = null,
    @SerialName("oncology_active") val oncologyActive: Boolean? = null,
    @SerialName("oncology_type") val oncologyType: String? = null,
    // Neurológico / GI
    val epilepsy: Boolean? = null,
    @SerialName("eating_disorder") val eatingDisorder: Boolean? = null,
    val gerd: Boolean? = null,
    val sjogren: Boolean? = null,
    // Medicamentos y hábitos
    @SerialName("systemic_meds") val systemicMeds: String? = null,
    @SerialName("brushing_freq") val brushingFreq: Int? = null,
    @SerialName("age_range") val ageRange: String? = null,
    @SerialName("tobacco_type") val tobaccoType: String? = null,
    @SerialName("tobacco_freq") val tobaccoFreq: String? = null,
    @SerialName("dental_anxiety") val dentalAnxiety: Boolean? = null,
    @SerialName("uses_floss") val usesFloss: Boolean? = null,
    @SerialName("uses_mouthwash") val usesMouthwash: Boolean? = null,
    // Meta
    @SerialName("onboarding_complete") val onboardingComplete: Boolean? = null,
    @SerialName("profile_completeness") val profileCompleteness: Int? = null,
)

@Serializable
data class HealthProfileResponse(
    val profile: HealthProfileDto? = null,
    val completeness: Int = 0,
)

// ── Historial Citas (Patient) ─────────────────────────────────────────────────

@Serializable
data class AppointmentCancelResponse(
    val id: String,
    val status: String,
    val message: String? = null,
)

// ── Notificaciones ────────────────────────────────────────────────────────────

@Serializable
data class NotificationDto(
    val id: String,
    val tipo: String,
    val mensaje: String,
    val visto: Boolean = false,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class NotificationsResponse(val notifications: List<NotificationDto>)

// ── Stripe Connect ────────────────────────────────────────────────────────────

@Serializable
data class StripeConnectCreateResponse(
    @SerialName("onboardingUrl") val onboardingUrl: String? = null,
    @SerialName("accountId") val accountId: String? = null,
    val status: String? = null,
    val message: String? = null,
)

@Serializable
data class StripeConnectStatusResponse(
    val status: String,                                // pending | pending_verification | active
    @SerialName("charges_enabled") val chargesEnabled: Boolean = false,
    @SerialName("payouts_enabled") val payoutsEnabled: Boolean = false,
    @SerialName("detailsSubmitted") val detailsSubmitted: Boolean = false,
)

// ── Onboarding conversacional ─────────────────────────────────────────────────

@Serializable
data class OnboardingRequest(
    val country: String? = null,
    @SerialName("last_visit") val lastVisit: String? = null,
    @SerialName("medical_conditions") val medicalConditions: String? = null,
    val role: String? = null,
)
