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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001By\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u0006H\u00c6\u0003J\t\u0010#\u001a\u00020\u000fH\u00c6\u0003J\t\u0010$\u001a\u00020\u000fH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J}\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\'\u001a\u00020\u000f2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001aR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014\u00a8\u0006,"}, d2 = {"Lcom/dentapp/app/ui/home/HomePatientState;", "", "patientName", "", "email", "doctors", "", "Lcom/dentapp/app/data/model/DoctorDto;", "appointments", "Lcom/dentapp/app/data/model/AppointmentDto;", "criticalAlerts", "Lcom/dentapp/app/data/model/ClinicalAlert;", "tratamientos", "Lcom/dentapp/app/ui/tratamiento/TratamientoDto;", "isLoadingDoctors", "", "isLoadingAppointments", "error", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZZLjava/lang/String;)V", "getAppointments", "()Ljava/util/List;", "getCriticalAlerts", "getDoctors", "getEmail", "()Ljava/lang/String;", "getError", "()Z", "getPatientName", "getTratamientos", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class HomePatientState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String patientName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String email = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.DoctorDto> doctors = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.AppointmentDto> appointments = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.ClinicalAlert> criticalAlerts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.ui.tratamiento.TratamientoDto> tratamientos = null;
    private final boolean isLoadingDoctors = false;
    private final boolean isLoadingAppointments = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public HomePatientState(@org.jetbrains.annotations.NotNull()
    java.lang.String patientName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorDto> doctors, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.AppointmentDto> appointments, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.ClinicalAlert> criticalAlerts, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.ui.tratamiento.TratamientoDto> tratamientos, boolean isLoadingDoctors, boolean isLoadingAppointments, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPatientName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorDto> getDoctors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.AppointmentDto> getAppointments() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.ClinicalAlert> getCriticalAlerts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.ui.tratamiento.TratamientoDto> getTratamientos() {
        return null;
    }
    
    public final boolean isLoadingDoctors() {
        return false;
    }
    
    public final boolean isLoadingAppointments() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public HomePatientState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorDto> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.AppointmentDto> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.ClinicalAlert> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.ui.tratamiento.TratamientoDto> component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.home.HomePatientState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String patientName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorDto> doctors, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.AppointmentDto> appointments, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.ClinicalAlert> criticalAlerts, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.ui.tratamiento.TratamientoDto> tratamientos, boolean isLoadingDoctors, boolean isLoadingAppointments, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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