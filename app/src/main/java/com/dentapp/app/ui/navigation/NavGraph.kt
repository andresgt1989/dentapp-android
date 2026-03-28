package com.dentapp.app.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.dentapp.app.ui.auth.LoginScreen
import com.dentapp.app.ui.auth.RegisterScreen
import com.dentapp.app.ui.home.HomeDoctorScreen
import com.dentapp.app.ui.home.HomePatientScreen
import com.dentapp.app.ui.onboarding.OnboardingDoctorScreen
import com.dentapp.app.ui.onboarding.OnboardingPatientScreen
import com.dentapp.app.ui.splash.SplashScreen
import com.dentapp.app.utils.TokenStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object Routes {
    const val SPLASH              = "splash"
    const val LOGIN               = "login"
    const val REGISTER            = "register"
    const val ONBOARDING_PATIENT  = "onboarding_patient"
    const val ONBOARDING_DOCTOR   = "onboarding_doctor"
    const val HOME_PATIENT        = "home_patient"
    const val HOME_DOCTOR         = "home_doctor"
}

@Composable
fun DentAppNavGraph(
    navController: NavHostController,
    tokenStore: TokenStore,
) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        // ── Splash ────────────────────────────────────────────────────────────
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
                    navController.navigate(dest) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // ── Auth ──────────────────────────────────────────────────────────────
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { role ->
                    val dest = if (role == "doctor") Routes.HOME_DOCTOR else Routes.HOME_PATIENT
                    navController.navigate(dest) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
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

        // ── Onboarding ────────────────────────────────────────────────────────
        composable(Routes.ONBOARDING_PATIENT) {
            OnboardingPatientScreen(
                onSuccess = {
                    navController.navigate(Routes.HOME_PATIENT) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.ONBOARDING_DOCTOR) {
            OnboardingDoctorScreen(
                onSuccess = {
                    navController.navigate(Routes.HOME_DOCTOR) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }

        // ── Home ──────────────────────────────────────────────────────────────
        composable(Routes.HOME_PATIENT) {
            HomePatientScreen(
                onLogout = {
                    runBlocking { tokenStore.clear() }
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.HOME_DOCTOR) {
            HomeDoctorScreen(
                onLogout = {
                    runBlocking { tokenStore.clear() }
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    }
}
