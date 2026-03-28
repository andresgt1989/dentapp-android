package com.dentapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dentapp.app.ui.navigation.DentAppNavGraph
import com.dentapp.app.ui.theme.DentAppTheme
import com.dentapp.app.utils.TokenStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var tokenStore: TokenStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DentAppTheme {
                val navController = rememberNavController()
                DentAppNavGraph(
                    navController = navController,
                    tokenStore = tokenStore,
                )
            }
        }
    }
}
