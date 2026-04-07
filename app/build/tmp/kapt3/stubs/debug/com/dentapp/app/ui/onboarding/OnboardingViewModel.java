package com.dentapp.app.ui.onboarding;

import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.OnboardingRequest;
import com.dentapp.app.utils.TokenStore;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0012J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0012J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0012J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u0012R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/dentapp/app/ui/onboarding/OnboardingViewModel;", "Landroidx/lifecycle/ViewModel;", "api", "Lcom/dentapp/app/data/api/ApiService;", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "(Lcom/dentapp/app/data/api/ApiService;Lcom/dentapp/app/utils/TokenStore;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/onboarding/OnboardingUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "saveOnboarding", "", "setCountry", "country", "", "setInviteCode", "code", "setLastVisit", "visit", "setMedicalCondition", "cond", "setRole", "role", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class OnboardingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.utils.TokenStore tokenStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.onboarding.OnboardingUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.onboarding.OnboardingUiState> state = null;
    
    @javax.inject.Inject()
    public OnboardingViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore tokenStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.onboarding.OnboardingUiState> getState() {
        return null;
    }
    
    public final void setRole(@org.jetbrains.annotations.NotNull()
    java.lang.String role) {
    }
    
    public final void setCountry(@org.jetbrains.annotations.NotNull()
    java.lang.String country) {
    }
    
    public final void setLastVisit(@org.jetbrains.annotations.NotNull()
    java.lang.String visit) {
    }
    
    public final void setMedicalCondition(@org.jetbrains.annotations.NotNull()
    java.lang.String cond) {
    }
    
    public final void setInviteCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
    }
    
    /**
     * Fire-and-forget: saves onboarding data to TokenStore + API. Navigation is caller's responsibility.
     */
    public final void saveOnboarding() {
    }
}