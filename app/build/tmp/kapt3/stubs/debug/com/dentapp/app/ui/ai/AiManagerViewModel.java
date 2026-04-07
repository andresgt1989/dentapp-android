package com.dentapp.app.ui.ai;

import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.model.*;
import com.dentapp.app.data.repository.AiRepository;
import com.dentapp.app.data.repository.Result;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\rJ\b\u0010\u0012\u001a\u00020\rH\u0002J\b\u0010\u0013\u001a\u00020\rH\u0002J\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0010J\b\u0010\u0016\u001a\u00020\rH\u0002J,\u0010\u0017\u001a\u00020\r2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0010R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001e"}, d2 = {"Lcom/dentapp/app/ui/ai/AiManagerViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/dentapp/app/data/repository/AiRepository;", "(Lcom/dentapp/app/data/repository/AiRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/ai/AiUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "confirmMedication", "medicamentoId", "", "loadContext", "loadHistory", "loadPendingAlerts", "sendMessage", "text", "startProactiveGreeting", "submitFeedback", "conversationId", "fueUtil", "", "rating", "", "comentario", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AiManagerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.repository.AiRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.ai.AiUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.ai.AiUiState> state = null;
    
    @javax.inject.Inject()
    public AiManagerViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.repository.AiRepository repo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.ai.AiUiState> getState() {
        return null;
    }
    
    private final void loadHistory() {
    }
    
    private final void startProactiveGreeting() {
    }
    
    public final void loadContext() {
    }
    
    private final void loadPendingAlerts() {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void confirmMedication(@org.jetbrains.annotations.NotNull()
    java.lang.String medicamentoId) {
    }
    
    public final void clearError() {
    }
    
    public final void submitFeedback(@org.jetbrains.annotations.Nullable()
    java.lang.String conversationId, boolean fueUtil, int rating, @org.jetbrains.annotations.Nullable()
    java.lang.String comentario) {
    }
}