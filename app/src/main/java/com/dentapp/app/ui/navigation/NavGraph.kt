package com.dentapp.app.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.dentapp.app.ui.ai.AiManagerScreen
import com.dentapp.app.ui.auth.LoginScreen
import com.dentapp.app.ui.auth.RegisterScreen
import com.dentapp.app.ui.booking.BookingScreen
import com.dentapp.app.ui.expediente.ExpedienteScreen
import com.dentapp.app.ui.home.HomeDoctorScreen
import com.dentapp.app.ui.home.HomePatientScreen
import com.dentapp.app.ui.loyalty.LoyaltyScreen
import com.dentapp.app.ui.onboarding.OnboardingDoctorScreen
import com.dentapp.app.ui.onboarding.OnboardingPatientScreen
import com.dentapp.app.ui.perfil.HistorialCitasScreen
import com.dentapp.app.ui.perfil.HistorialMedicoScreen
import com.dentapp.app.ui.perfil.MisDatosScreen
import com.dentapp.app.ui.perfil.NotificacionesScreen
import com.dentapp.app.ui.perfil.PrivacidadScreen
import com.dentapp.app.ui.qr.GenerarQRScreen
import com.dentapp.app.ui.radiografia.XRayCaptureScreen
import com.dentapp.app.ui.receta.PrescriptionScannerScreen
import com.dentapp.app.ui.rx.RxPatientScreen
import com.dentapp.app.ui.splash.SplashScreen
import com.dentapp.app.ui.subscriptions.SubscriptionScreen
import com.dentapp.app.ui.tratamiento.TratamientoDto
import com.dentapp.app.ui.tratamiento.TreatmentDetailScreen
import com.dentapp.app.ui.tratamiento.TreatmentTrackingScreen
import com.dentapp.app.utils.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

object Routes {
    const val SPLASH              = "splash"
    const val LOGIN               = "login"
    const val REGISTER            = "register"
    const val ONBOARDING_PATIENT  = "onboarding_patient"
    const val ONBOARDING_DOCTOR   = "onboarding_doctor"
    const val HOME_PATIENT        = "home_patient"
    const val HOME_DOCTOR         = "home_doctor"
    const val AI_MANAGER          = "ai_manager"
    const val BOOKING             = "booking/{doctorId}/{doctorName}"
    const val EXPEDIENTE          = "expediente?patientId={patientId}"
    const val EXPEDIENTE_BASE     = "expediente"

    fun expediente(patientId: String? = null) =
        if (patientId != null) "expediente?patientId=$patientId" else "expediente"
    // ── Perfil del paciente ────────────────────────────────────────────────────
    const val HISTORIAL_MEDICO    = "historial_medico"
    const val MIS_DATOS           = "mis_datos"
    const val HISTORIAL_CITAS     = "historial_citas"
    const val NOTIFICACIONES      = "notificaciones"
    const val PRIVACIDAD          = "privacidad"
    // ── Tratamientos ──────────────────────────────────────────────────────────
    const val TRATAMIENTOS        = "tratamientos"
    const val TRATAMIENTO_DETALLE = "tratamiento_detalle/{tratamientoJson}"
    // ── Sprint 3 ──────────────────────────────────────────────────────────────
    const val GENERAR_QR          = "generar_qr"
    const val XRAY_CAPTURE        = "xray_capture"
    const val PRESCRIPTION_SCAN   = "prescription_scan"
    const val SUBSCRIPTION        = "subscription"
    const val LOYALTY             = "loyalty"
    const val RX_PATIENT          = "rx_patient"

    fun booking(doctorId: String, doctorName: String) =
        "booking/$doctorId/${URLEncoder.encode(doctorName, "UTF-8")}"

    fun tratamientoDetalle(tratamiento: TratamientoDto): String {
        val json = Json.encodeToString(tratamiento)
        return "tratamiento_detalle/${URLEncoder.encode(json, "UTF-8")}"
    }
}

@Composable
fun DentAppNavGraph(
    navController: NavHostController,
    tokenStore: TokenStore,
    onNavReady: (() -> Unit)? = null,
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            LaunchedEffect(Unit) { onNavReady?.invoke() }
            SplashScreen(
                onFinished = {
                    val token = runBlocking { tokenStore.token.firstOrNull() }
                    val role  = runBlocking { tokenStore.role.firstOrNull() }
                    val dest  = when {
                        !token.isNullOrBlank() && role == "doctor"  -> Routes.HOME_DOCTOR
                        !token.isNullOrBlank() && role == "patient" -> Routes.HOME_PATIENT
                        else -> Routes.LOGIN
                    }
                    navController.navigate(dest) { popUpTo(Routes.SPLASH) { inclusive = true } }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { role ->
                    val dest = if (role == "doctor") Routes.HOME_DOCTOR else Routes.HOME_PATIENT
                    navController.navigate(dest) { popUpTo(Routes.LOGIN) { inclusive = true } }
                },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToPatientOnboarding = { navController.navigate(Routes.ONBOARDING_PATIENT) },
                onNavigateToDoctorOnboarding  = { navController.navigate(Routes.ONBOARDING_DOCTOR) },
                onNavigateToLogin = { navController.popBackStack() },
            )
        }

        composable(Routes.ONBOARDING_PATIENT) {
            OnboardingPatientScreen(
                onSuccess = {
                    navController.navigate(Routes.HOME_PATIENT) { popUpTo(Routes.LOGIN) { inclusive = true } }
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.ONBOARDING_DOCTOR) {
            OnboardingDoctorScreen(
                onSuccess = {
                    navController.navigate(Routes.HOME_DOCTOR) { popUpTo(Routes.LOGIN) { inclusive = true } }
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.HOME_PATIENT) {
            HomePatientScreen(
                onLogout = {
                    runBlocking { tokenStore.clear() }
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                },
                onOpenAiManager       = { navController.navigate(Routes.AI_MANAGER) },
                onOpenExpediente      = { navController.navigate(Routes.expediente()) },
                onOpenBooking         = { id, name -> navController.navigate(Routes.booking(id, name)) },
                onOpenHistorialMedico = { navController.navigate(Routes.HISTORIAL_MEDICO) },
                onOpenMisDatos        = { navController.navigate(Routes.MIS_DATOS) },
                onOpenHistorialCitas  = { navController.navigate(Routes.HISTORIAL_CITAS) },
                onOpenNotificaciones  = { navController.navigate(Routes.NOTIFICACIONES) },
                onOpenPrivacidad      = { navController.navigate(Routes.PRIVACIDAD) },
                onOpenTratamientos    = { navController.navigate(Routes.TRATAMIENTOS) },
                onOpenRx              = { navController.navigate(Routes.RX_PATIENT) },
                onOpenLoyalty         = { navController.navigate(Routes.LOYALTY) },
                onOpenSubscription    = { navController.navigate(Routes.SUBSCRIPTION) },
            )
        }

        composable(Routes.AI_MANAGER) {
            AiManagerScreen(onBack = { navController.popBackStack() })
        }

        composable(
            Routes.EXPEDIENTE,
            arguments = listOf(navArgument("patientId") {
                type = NavType.StringType; nullable = true; defaultValue = null
            }),
        ) { backStack ->
            val patientId = backStack.arguments?.getString("patientId")
            ExpedienteScreen(
                onBack = { navController.popBackStack() },
                patientId = patientId,
            )
        }

        // ── Perfil del paciente ────────────────────────────────────────────────

        composable(Routes.HISTORIAL_MEDICO) {
            HistorialMedicoScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.MIS_DATOS) {
            MisDatosScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.HISTORIAL_CITAS) {
            HistorialCitasScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.NOTIFICACIONES) {
            NotificacionesScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.PRIVACIDAD) {
            PrivacidadScreen(
                onBack = { navController.popBackStack() },
                onAccountDeleted = {
                    runBlocking { tokenStore.clear() }
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                },
            )
        }

        // ── Tratamientos ──────────────────────────────────────────────────────

        composable(Routes.TRATAMIENTOS) {
            TreatmentTrackingScreen(
                onBack = { navController.popBackStack() },
                onVerDetalle = { t -> navController.navigate(Routes.tratamientoDetalle(t)) },
            )
        }

        composable(
            Routes.TRATAMIENTO_DETALLE,
            arguments = listOf(navArgument("tratamientoJson") { type = NavType.StringType }),
        ) { backStack ->
            val raw = backStack.arguments?.getString("tratamientoJson") ?: ""
            val decoded = URLDecoder.decode(raw, "UTF-8")
            val tratamiento = remember(decoded) {
                try { Json.decodeFromString<TratamientoDto>(decoded) }
                catch (_: Exception) { null }
            }
            if (tratamiento != null) {
                TreatmentDetailScreen(
                    tratamiento = tratamiento,
                    onBack = { navController.popBackStack() },
                )
            }
        }

        // ── Booking ───────────────────────────────────────────────────────────

        composable(
            Routes.BOOKING,
            arguments = listOf(
                navArgument("doctorId")   { type = NavType.StringType },
                navArgument("doctorName") { type = NavType.StringType },
            ),
        ) { backStack ->
            val rawName    = backStack.arguments?.getString("doctorName") ?: ""
            val doctorName = URLDecoder.decode(rawName, "UTF-8")
            BookingScreen(
                doctorId   = backStack.arguments?.getString("doctorId") ?: "",
                doctorName = doctorName,
                onBack     = { navController.popBackStack() },
                onBookingComplete = {
                    navController.navigate(Routes.HOME_PATIENT) {
                        popUpTo(Routes.HOME_PATIENT) { inclusive = false }
                    }
                },
            )
        }

        composable(Routes.HOME_DOCTOR) {
            HomeDoctorScreen(
                onLogout = {
                    runBlocking { tokenStore.clear() }
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                },
                onVerExpedientePaciente = { patientId ->
                    navController.navigate(Routes.expediente(patientId))
                },
            )
        }

        // ── Sprint 3 ──────────────────────────────────────────────────────────

        composable(Routes.GENERAR_QR) {
            GenerarQRScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.XRAY_CAPTURE) {
            XRayCaptureScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.PRESCRIPTION_SCAN) {
            PrescriptionScannerScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.SUBSCRIPTION) {
            SubscriptionScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.LOYALTY) {
            LoyaltyScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.RX_PATIENT) {
            RxPatientScreen(onBack = { navController.popBackStack() })
        }
    }
}
