package com.dentapp.app.data.repository;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.DoctorDto;
import com.dentapp.app.data.model.UpdateBankInfoRequest;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\u0006H\u0086@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/dentapp/app/data/repository/DoctorRepository;", "", "api", "Lcom/dentapp/app/data/api/ApiService;", "(Lcom/dentapp/app/data/api/ApiService;)V", "getDoctors", "Lcom/dentapp/app/data/repository/Result;", "", "Lcom/dentapp/app/data/model/DoctorDto;", "specialty", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyProfile", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBankInfo", "request", "Lcom/dentapp/app/data/model/UpdateBankInfoRequest;", "(Lcom/dentapp/app/data/model/UpdateBankInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfile", "fields", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DoctorRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    
    @javax.inject.Inject()
    public DoctorRepository(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDoctors(@org.jetbrains.annotations.Nullable()
    java.lang.String specialty, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<? extends java.util.List<com.dentapp.app.data.model.DoctorDto>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMyProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.DoctorDto>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> fields, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.DoctorDto>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateBankInfo(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.UpdateBankInfoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.DoctorDto>> $completion) {
        return null;
    }
}