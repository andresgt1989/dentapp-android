package com.dentapp.app.data.repository;

import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.*;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010\r\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00062\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/dentapp/app/data/repository/AiRepository;", "", "api", "Lcom/dentapp/app/data/api/ApiService;", "(Lcom/dentapp/app/data/api/ApiService;)V", "chat", "Lcom/dentapp/app/data/repository/Result;", "Lcom/dentapp/app/data/model/AiChatResponse;", "message", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "confirmMedication", "Lcom/dentapp/app/data/model/ConfirmMedicationResponse;", "medicamentoId", "getClinicalAlerts", "Lcom/dentapp/app/data/model/ClinicalAlertsResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getContext", "Lcom/dentapp/app/data/model/AiContextResponse;", "getHistory", "Lcom/dentapp/app/data/model/AiHistoryResponse;", "startConversation", "Lcom/dentapp/app/data/model/AiStartResponse;", "submitFeedback", "Lcom/dentapp/app/data/model/AiFeedbackResponse;", "request", "Lcom/dentapp/app/data/model/AiFeedbackRequest;", "(Lcom/dentapp/app/data/model/AiFeedbackRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AiRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    
    @javax.inject.Inject()
    public AiRepository(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object chat(@org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AiChatResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getContext(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AiContextResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AiHistoryResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startConversation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AiStartResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getClinicalAlerts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.ClinicalAlertsResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object confirmMedication(@org.jetbrains.annotations.NotNull()
    java.lang.String medicamentoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.ConfirmMedicationResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object submitFeedback(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.AiFeedbackRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.dentapp.app.data.repository.Result<com.dentapp.app.data.model.AiFeedbackResponse>> $completion) {
        return null;
    }
}