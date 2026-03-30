package com.dentapp.app.data.model

import com.google.gson.annotations.SerializedName

data class AdminStatsResponse(
    @SerializedName("total_users") val totalUsers: Int = 0,
    @SerializedName("total_doctors") val totalDoctors: Int = 0,
    @SerializedName("total_patients") val totalPatients: Int = 0,
    @SerializedName("total_appointments") val totalAppointments: Int = 0,
    @SerializedName("total_revenue") val totalRevenue: Double = 0.0,
)
