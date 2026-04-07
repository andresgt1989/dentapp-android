package com.dentapp.app.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.dentapp.app.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\fH\u0016J \u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\nH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0019"}, d2 = {"Lcom/dentapp/app/utils/DentAppFCMService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "getTokenStore", "()Lcom/dentapp/app/utils/TokenStore;", "setTokenStore", "(Lcom/dentapp/app/utils/TokenStore;)V", "buildIntent", "Landroid/content/Intent;", "ruta", "", "extras", "", "onMessageReceived", "", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "showNotification", "titulo", "cuerpo", "intent", "app_debug"})
public final class DentAppFCMService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject()
    public com.dentapp.app.utils.TokenStore tokenStore;
    
    public DentAppFCMService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.utils.TokenStore getTokenStore() {
        return null;
    }
    
    public final void setTokenStore(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore p0) {
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
    
    private final android.content.Intent buildIntent(java.lang.String ruta, java.util.Map<java.lang.String, java.lang.String> extras) {
        return null;
    }
    
    private final void showNotification(java.lang.String titulo, java.lang.String cuerpo, android.content.Intent intent) {
    }
}