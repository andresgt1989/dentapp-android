package com.dentapp.app.data.model

import com.google.gson.annotations.SerializedName

data class Plan(
    val id: String,
    val name: String,
    val price: Int,
    val interval: String? = null,
    val features: List<String>,
)

data class PlansResponse(
    val patient: List<Plan>,
    val doctor: List<Plan>,
)

data class SubscriptionStatusResponse(
    val plan: String,
    @SerializedName("subscription_status") val subscriptionStatus: String,
    @SerializedName("stripe_customer_id") val stripeCustomerId: String?,
)
