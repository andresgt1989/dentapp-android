package com.dentapp.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.navigation.NavController;
import com.dentapp.app.ui.navigation.Routes;
import com.dentapp.app.utils.TokenStore;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00062\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0015"}, d2 = {"Lcom/dentapp/app/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "navController", "Landroidx/navigation/NavController;", "pendingDeepLink", "Landroid/content/Intent;", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "getTokenStore", "()Lcom/dentapp/app/utils/TokenStore;", "setTokenStore", "(Lcom/dentapp/app/utils/TokenStore;)V", "handleDeepLink", "", "intent", "nc", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.dentapp.app.utils.TokenStore tokenStore;
    @org.jetbrains.annotations.Nullable()
    private androidx.navigation.NavController navController;
    @org.jetbrains.annotations.Nullable()
    private android.content.Intent pendingDeepLink;
    
    public MainActivity() {
        super(0);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.utils.TokenStore getTokenStore() {
        return null;
    }
    
    public final void setTokenStore(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onNewIntent(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    private final void handleDeepLink(android.content.Intent intent, androidx.navigation.NavController nc) {
    }
}