package com.dentapp.app.ui.home;

import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.model.AppointmentDto;
import com.dentapp.app.data.model.ClinicalAlert;
import com.dentapp.app.data.model.DoctorDto;
import com.dentapp.app.data.model.UrgenciaRequest;
import com.dentapp.app.data.repository.DoctorRepository;
import com.dentapp.app.data.repository.Result;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.ui.tratamiento.TratamientoDto;
import com.dentapp.app.utils.TokenStore;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\b\u0010\u0013\u001a\u00020\u0011H\u0002J\u0006\u0010\u0014\u001a\u00020\u0011J\b\u0010\u0015\u001a\u00020\u0011H\u0002J\b\u0010\u0016\u001a\u00020\u0011H\u0002J\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/dentapp/app/ui/home/HomePatientViewModel;", "Landroidx/lifecycle/ViewModel;", "doctorRepository", "Lcom/dentapp/app/data/repository/DoctorRepository;", "api", "Lcom/dentapp/app/data/api/ApiService;", "tokenStore", "Lcom/dentapp/app/utils/TokenStore;", "(Lcom/dentapp/app/data/repository/DoctorRepository;Lcom/dentapp/app/data/api/ApiService;Lcom/dentapp/app/utils/TokenStore;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/home/HomePatientState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "loadAppointments", "loadClinicalAlerts", "loadDoctors", "loadPatientProfile", "loadTratamientos", "sendUrgencia", "descripcion", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomePatientViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.repository.DoctorRepository doctorRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.utils.TokenStore tokenStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.home.HomePatientState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.home.HomePatientState> state = null;
    
    @javax.inject.Inject()
    public HomePatientViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.repository.DoctorRepository doctorRepository, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.utils.TokenStore tokenStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.home.HomePatientState> getState() {
        return null;
    }
    
    public final void loadDoctors() {
    }
    
    public final void loadAppointments() {
    }
    
    private final void loadPatientProfile() {
    }
    
    private final void loadClinicalAlerts() {
    }
    
    private final void loadTratamientos() {
    }
    
    public final void sendUrgencia(@org.jetbrains.annotations.NotNull()
    java.lang.String descripcion) {
    }
    
    public final void clearError() {
    }
}