package com.dentapp.app.ui.navigation

import androidx.compose.runtime.*
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
import com.dentapp.app.ui.onboarding.OnboardingDoctorScreen
import com.dentapp.app.ui.onboarding.OnboardingPatientScreen
import com.dentapp.app.ui.splash.SplashScreen
import com.dentapp.app.utils.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
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
    const val EXPEDIENTE          = "expediente"

    fun booking(doctorId: String, doctorName: String) =
        "booking/$doctorId/${URLEncoder.encode(doctorName, "UTF-8")}"
}

@Composable
fun DentAppNavGraph(
    navController: NavHostController,
    tokenStore: TokenStore,
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
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
                onOpenAiManager  = { navController.navigate(Routes.AI_MANAGER) },
                onOpenExpediente = { navController.navigate(Routes.EXPEDIENTE) },
                onOpenBooking    = { id, name -> navController.navigate(Routes.booking(id, name)) },
            )
        }

        composable(Routes.AI_MANAGER) {
            AiManagerScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.EXPEDIENTE) {
            ExpedienteScreen(onBack = { navController.popBackStack() })
        }

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
            )
        }
    }
}
