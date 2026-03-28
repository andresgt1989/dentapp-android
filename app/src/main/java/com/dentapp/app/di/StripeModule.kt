package com.dentapp.app.di

import android.content.Context
import com.stripe.android.PaymentConfiguration
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

// Stripe se inicializa desde Application usando tu publishable key
// Reemplaza pk_live_... con tu Stripe Publishable Key (distinta de la secret key)
@Singleton
class StripeInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun init(publishableKey: String) {
        PaymentConfiguration.init(context, publishableKey)
    }
}
