package com.dentapp.app.data.repository;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.AuthResponse;
import com.dentapp.app.data.model.GoogleAuthRequest;
import com.dentapp.app.data.model.LoginRequest;
import com.dentapp.app.data.model.RegisterRequest;
import com.dentapp.app.utils.TokenStore;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0086@\u00a2\u0006\u0002\u0010\rJ0\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/dentapp/app/data/repository/AuthRepository;", "", "api", "Lcom/dentapp/app/data/api/ApiService;", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "(Lcom/dentapp/app/data/api/ApiService;Lcom/dentapp/app/utils/TokenStore;)V", "googleAuth", "Lcom/dentapp/app/data/repository/Result;", "Lcom/dentapp/app/data/model/AuthResponse;", "idToken", "", "fcmToken", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "email", "password", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "request", "Lcom/dentapp/app/data/model/RegisterRequest;", "(Lcom/dentapp/app/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.utils.TokenStore tokenStore = null;
    
    @javax.inject.Inject()
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore tokenStore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.Nullable()
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AuthResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AuthResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object googleAuth(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken, @org.jetbrains.annotations.Nullable()
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AuthResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}