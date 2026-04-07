package com.dentapp.app.ui.home;

import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.*;
import com.dentapp.app.utils.TokenStore;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u001a\u0010\u0013\u001a\u00020\u000f2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u000f0\u0015J\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fJ\b\u0010\u0019\u001a\u00020\u000fH\u0002J\b\u0010\u001a\u001a\u00020\u000fH\u0002J\u0006\u0010\u001b\u001a\u00020\u000fJ\u0006\u0010\u001c\u001a\u00020\u000fJ\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eJ\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\u000fJ\u000e\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\"J\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u0016J\u000e\u0010(\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u0016J\u000e\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/dentapp/app/ui/home/HomeDoctorViewModel;", "Landroidx/lifecycle/ViewModel;", "api", "Lcom/dentapp/app/data/api/ApiService;", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "(Lcom/dentapp/app/data/api/ApiService;Lcom/dentapp/app/utils/TokenStore;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/home/HomeDoctorUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "checkStripeStatus", "", "clearError", "dismissPayoutMessage", "guardarHorario", "iniciarStripeConnect", "onUrlReady", "Lkotlin/Function1;", "", "loadAlerts", "loadCitasHoy", "loadDoctorName", "loadHorario", "loadIngresos", "loadPacientes", "pacientesFiltrados", "", "Lcom/dentapp/app/data/model/DoctorPacienteDto;", "selectTab", "tab", "", "solicitarPago", "toggleDiaLaborable", "dia", "updateHorarioFin", "hora", "updateHorarioInicio", "updateSearch", "query", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeDoctorViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.utils.TokenStore tokenStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.home.HomeDoctorUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.home.HomeDoctorUiState> state = null;
    
    @javax.inject.Inject()
    public HomeDoctorViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore tokenStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.home.HomeDoctorUiState> getState() {
        return null;
    }
    
    private final void loadDoctorName() {
    }
    
    public final void selectTab(int tab) {
    }
    
    public final void loadCitasHoy() {
    }
    
    public final void loadPacientes() {
    }
    
    public final void updateSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> pacientesFiltrados() {
        return null;
    }
    
    private final void loadHorario() {
    }
    
    public final void toggleDiaLaborable(int dia) {
    }
    
    public final void updateHorarioInicio(@org.jetbrains.annotations.NotNull()
    java.lang.String hora) {
    }
    
    public final void updateHorarioFin(@org.jetbrains.annotations.NotNull()
    java.lang.String hora) {
    }
    
    public final void guardarHorario() {
    }
    
    public final void loadIngresos() {
    }
    
    public final void solicitarPago() {
    }
    
    public final void dismissPayoutMessage() {
    }
    
    public final void loadAlerts() {
    }
    
    public final void checkStripeStatus() {
    }
    
    public final void iniciarStripeConnect(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onUrlReady) {
    }
    
    public final void clearError() {
    }
}