package com.dentapp.app.ui.ai;

import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.model.*;
import com.dentapp.app.data.repository.AiRepository;
import com.dentapp.app.data.repository.Result;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\b\u001d\b\u0087\b\u0018\u00002\u00020\u0001Bm\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\u0002\u0010\u0012J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\nH\u00c6\u0003J\t\u0010$\u001a\u00020\nH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u0010\u0010&\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u00c6\u0003Jv\u0010(\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u00c6\u0001\u00a2\u0006\u0002\u0010)J\u0013\u0010*\u001a\u00020\n2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020\u000fH\u00d6\u0001J\t\u0010-\u001a\u00020\rH\u00d6\u0001R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0019R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001e\u00a8\u0006."}, d2 = {"Lcom/dentapp/app/ui/ai/AiUiState;", "", "messages", "", "Lcom/dentapp/app/data/model/AiMessage;", "context", "Lcom/dentapp/app/data/model/AiContextResponse;", "pendingAlerts", "Lcom/dentapp/app/data/model/ClinicalAlert;", "isLoading", "", "isSending", "error", "", "lastFeedbackPoints", "", "confirmedMedIds", "", "(Ljava/util/List;Lcom/dentapp/app/data/model/AiContextResponse;Ljava/util/List;ZZLjava/lang/String;Ljava/lang/Integer;Ljava/util/Set;)V", "getConfirmedMedIds", "()Ljava/util/Set;", "getContext", "()Lcom/dentapp/app/data/model/AiContextResponse;", "getError", "()Ljava/lang/String;", "()Z", "getLastFeedbackPoints", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMessages", "()Ljava/util/List;", "getPendingAlerts", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/util/List;Lcom/dentapp/app/data/model/AiContextResponse;Ljava/util/List;ZZLjava/lang/String;Ljava/lang/Integer;Ljava/util/Set;)Lcom/dentapp/app/ui/ai/AiUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class AiUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.AiMessage> messages = null;
    @org.jetbrains.annotations.Nullable()
    private final com.dentapp.app.data.model.AiContextResponse context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.ClinicalAlert> pendingAlerts = null;
    private final boolean isLoading = false;
    private final boolean isSending = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer lastFeedbackPoints = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> confirmedMedIds = null;
    
    public AiUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.AiMessage> messages, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.data.model.AiContextResponse context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.ClinicalAlert> pendingAlerts, boolean isLoading, boolean isSending, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer lastFeedbackPoints, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> confirmedMedIds) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.AiMessage> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.data.model.AiContextResponse getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.ClinicalAlert> getPendingAlerts() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isSending() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getLastFeedbackPoints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getConfirmedMedIds() {
        return null;
    }
    
    public AiUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.AiMessage> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.data.model.AiContextResponse component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.ClinicalAlert> component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.ai.AiUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.AiMessage> messages, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.data.model.AiContextResponse context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.ClinicalAlert> pendingAlerts, boolean isLoading, boolean isSending, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.Integer lastFeedbackPoints, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> confirmedMedIds) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}