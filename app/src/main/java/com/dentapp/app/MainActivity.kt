package com.dentapp.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dentapp.app.ui.navigation.DentAppNavGraph
import com.dentapp.app.ui.navigation.Routes
import com.dentapp.app.ui.theme.DentAppTheme
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var tokenStore: TokenStore

    private var navController: NavController? = null
    private var pendingDeepLink: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DentAppTheme {
                val nc = rememberNavController()
                navController = nc
                DentAppNavGraph(
                    navController = nc,
                    tokenStore = tokenStore,
                    onNavReady = {
                        pendingDeepLink?.let { handleDeepLink(it, nc) }
                        pendingDeepLink = null
                    },
                )
            }
        }
        handleDeepLink(intent, null)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val nc = navController
        if (nc != null) handleDeepLink(intent, nc)
        else pendingDeepLink = intent
    }

    private fun handleDeepLink(intent: Intent?, nc: NavController?) {
        val ruta = intent?.getStringExtra("deep_link_ruta") ?: return
        val target = when (ruta) {
            "home_patient"     -> Routes.HOME_PATIENT
            "home_doctor"      -> Routes.HOME_DOCTOR
            "ai_manager"       -> Routes.AI_MANAGER
            "expediente"       -> Routes.EXPEDIENTE
            "qr_screen"        -> Routes.GENERAR_QR
            "treatment_detail" -> Routes.TRATAMIENTOS
            else               -> null
        }
        if (target != null && nc != null) {
            nc.navigate(target) {
                launchSingleTop = true
            }
        } else if (target != null) {
            pendingDeepLink = intent
        }
    }
}
