package com.dentapp.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
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
        // o inmediatamente si hay sesión activa
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: "DentApp"
        val body  = message.notification?.body  ?: return

        val channelId = "dentapp_reminders"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId, "Recordatorios DentApp",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply { description = "Recordatorios de citas y actualizaciones" }
        manager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            message.data.forEach { (k, v) -> putExtra(k, v) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
