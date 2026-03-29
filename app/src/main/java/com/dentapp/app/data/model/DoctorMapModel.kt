package com.dentapp.app.data.model

import com.google.android.gms.maps.model.LatLng

/** Doctor con coordenadas para mostrar en el mapa. */
data class DoctorMapItem(
    val id: String,
    val fullName: String,
    val specialty: String,
    val consultationFee: Double,
    val rating: Float = 4.5f,
    val reviewCount: Int = 0,
    val distance: String = "",       // "1.2 km"
    val address: String = "",
    val position: LatLng,
    val isAvailable: Boolean = true,
    val nextSlot: String = "",       // "Hoy 3:00 PM"
)

data class TimeSlot(
    val id: String,
    val time: String,          // "09:00 AM"
    val isAvailable: Boolean,
    val dateTime: String,      // ISO para enviar a la API
)

data class AppointmentConfirmation(
    val doctor: DoctorMapItem,
    val slot: TimeSlot,
    val date: String,          // "Lunes 14 de abril"
    val fee: Double,
    val notes: String = "",
)
