package com.dentapp.app

import android.app.Application
import com.dentapp.app.utils.FeatureFlagManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DentApp : Application() {

    @Inject lateinit var featureFlags: FeatureFlagManager

    override fun onCreate() {
        super.onCreate()
        // Fetch public flags en startup — no requiere login
        featureFlags.fetchPublic()
    }
}
