package com.dentapp.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dentapp.app.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DentAppFCMService : FirebaseMessagingService() {

    @Inject lateinit var tokenStore: TokenStore

    override fun onNewToken(token: String) {
        // El token actualizado se envía al backend en el próximo login
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val tipo  = remoteMessage.data["tipo"] ?: ""
        val titulo = remoteMessage.notification?.title ?: "DentApp 🦷"
        val cuerpo = remoteMessage.notification?.body ?: return

        val intent = when (tipo) {
            "recordatorio_cita"      -> buildIntent("home_patient",    mapOf("tab" to "2"))
            "recordatorio_medicacion"-> buildIntent("ai_manager",      mapOf("expand_meds" to "true"))
            "solicitud_calificacion" -> buildIntent("rating",          mapOf("reserva_id" to (remoteMessage.data["reserva_id"] ?: "")))
            "corona_urgente"         -> buildIntent("treatment_detail",mapOf("tipo" to "endodoncia"))
            "perio_maintenance"      -> buildIntent("treatment_detail",mapOf("tipo" to "periodoncia"))
            "implant_checkin"        -> buildIntent("treatment_detail",mapOf("tipo" to "implante"))
            "aligner_uso"            -> buildIntent("treatment_detail",mapOf("tipo" to "ortodoncia"))
            "cancer_screening_urgent"-> buildIntent("ai_manager",      mapOf("precarga" to "Tengo una lesión en mi boca que no ha sanado en 3 semanas"))
            "mronj_warning"          -> buildIntent("ai_manager",      mapOf("precarga" to "Tomo bifosfonatos y tengo una cita dental próxima"))
            "cardiac_prophylaxis"    -> buildIntent("ai_manager",      mapOf("precarga" to "Tengo cardiopatía y mañana voy al dentista"))
            "smoking_implant_risk"   -> buildIntent("ai_manager",      mapOf("precarga" to "Tengo un implante dental y quiero dejar de fumar"))
            "connect_active"         -> buildIntent("home_doctor",     mapOf("tab" to "3"))
            "share_viewed"           -> buildIntent("qr_screen",       emptyMap())
            "reactivacion"           -> buildIntent("home_patient",    mapOf("tab" to "1"))
            "resumen_diario"         -> buildIntent("ai_manager",      emptyMap())
            "drug_alcohol"           -> buildIntent("ai_manager",      mapOf("precarga" to "Tengo una alerta sobre mi medicamento"))
            "drug_duration"          -> buildIntent("ai_manager",      mapOf("precarga" to "Tengo una alerta sobre la duración de mi medicamento"))
            "weekly_ai_report"       -> buildIntent("home_doctor",     mapOf("tab" to "4"))
            else                     -> buildIntent("home_patient",    emptyMap())
        }

        showNotification(titulo, cuerpo, intent)
    }

    private fun buildIntent(ruta: String, extras: Map<String, String>): Intent {
        return Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("deep_link_ruta", ruta)
            extras.forEach { (k, v) -> putExtra(k, v) }
        }
    }

    private fun showNotification(titulo: String, cuerpo: String, intent: Intent) {
        val channelId = "dentapp_channel"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "DentApp Notificaciones",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply { description = "Recordatorios, alertas clínicas y actualizaciones" }
        manager.createNotificationChannel(channel)

        val pendingIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(titulo)
            .setContentText(cuerpo)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        try {
            NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification)
        } catch (_: SecurityException) { /* permiso de notificación no concedido */ }
    }
}
