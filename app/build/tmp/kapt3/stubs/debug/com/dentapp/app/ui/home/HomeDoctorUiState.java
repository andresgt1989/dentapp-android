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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b<\b\u0087\b\u0018\u00002\u00020\u0001B\u00ad\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\b\b\u0002\u0010\u0017\u001a\u00020\t\u0012\b\b\u0002\u0010\u0018\u001a\u00020\t\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u000e\u0012\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000b\u0012\b\b\u0002\u0010\u001d\u001a\u00020\t\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u001f\u001a\u00020\t\u0012\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u000b\u0012\b\b\u0002\u0010\"\u001a\u00020\u0003\u0012\b\b\u0002\u0010#\u001a\u00020\t\u0012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010%J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\t\u0010?\u001a\u00020\u0005H\u00c6\u0003J\t\u0010@\u001a\u00020\tH\u00c6\u0003J\t\u0010A\u001a\u00020\u0005H\u00c6\u0003J\t\u0010B\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\t\u0010D\u001a\u00020\tH\u00c6\u0003J\t\u0010E\u001a\u00020\tH\u00c6\u0003J\t\u0010F\u001a\u00020\u000eH\u00c6\u0003J\t\u0010G\u001a\u00020\u000eH\u00c6\u0003J\u000f\u0010H\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000bH\u00c6\u0003J\t\u0010I\u001a\u00020\u0005H\u00c6\u0003J\t\u0010J\u001a\u00020\tH\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010L\u001a\u00020\tH\u00c6\u0003J\u000f\u0010M\u001a\b\u0012\u0004\u0012\u00020!0\u000bH\u00c6\u0003J\t\u0010N\u001a\u00020\u0003H\u00c6\u0003J\t\u0010O\u001a\u00020\tH\u00c6\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010S\u001a\u00020\tH\u00c6\u0003J\u000f\u0010T\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003J\t\u0010U\u001a\u00020\u000eH\u00c6\u0003J\t\u0010V\u001a\u00020\tH\u00c6\u0003J\u000f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00110\u000bH\u00c6\u0003J\u00b1\u0002\u0010X\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\t2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00052\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000e2\u000e\b\u0002\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000b2\b\b\u0002\u0010\u001d\u001a\u00020\t2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u001f\u001a\u00020\t2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u000b2\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\t2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010Y\u001a\u00020\t2\b\u0010Z\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010[\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\\\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\"\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010)R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0013\u0010$\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010,R\u0011\u0010\u0015\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010,R\u0011\u0010\u0018\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010,R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010#\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u00100R\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u00100R\u0011\u0010\u001d\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u00100R\u0011\u0010\u0013\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u00100R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u00100R\u0011\u0010\u0017\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u00100R\u0011\u0010\u001f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u00100R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010)R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010)R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010,R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010)R\u0011\u0010\u0012\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010,R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\'R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010,R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010,R\u0011\u0010\u0019\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u00103R\u0011\u0010\u001a\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u00103\u00a8\u0006]"}, d2 = {"Lcom/dentapp/app/ui/home/HomeDoctorUiState;", "", "selectedTab", "", "doctorName", "", "stripeStatus", "stripeOnboardingUrl", "isLoadingStripe", "", "citasHoy", "", "Lcom/dentapp/app/data/model/DoctorCitaDto;", "ingresosHoy", "", "isLoadingHoy", "pacientes", "Lcom/dentapp/app/data/model/DoctorPacienteDto;", "searchQuery", "isLoadingPacientes", "horarioInicio", "horarioFin", "diasLaborables", "isSavingHorario", "horarioGuardado", "totalMes", "totalPendiente", "payouts", "Lcom/dentapp/app/data/model/DoctorPayoutDto;", "isLoadingIngresos", "payoutEnviado", "isSolicitandoPago", "patientAlerts", "Lcom/dentapp/app/data/model/PatientAlertDto;", "alertasNoVistas", "isLoadingAlerts", "error", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/util/List;DZLjava/util/List;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;ZZDDLjava/util/List;ZLjava/lang/String;ZLjava/util/List;IZLjava/lang/String;)V", "getAlertasNoVistas", "()I", "getCitasHoy", "()Ljava/util/List;", "getDiasLaborables", "getDoctorName", "()Ljava/lang/String;", "getError", "getHorarioFin", "getHorarioGuardado", "()Z", "getHorarioInicio", "getIngresosHoy", "()D", "getPacientes", "getPatientAlerts", "getPayoutEnviado", "getPayouts", "getSearchQuery", "getSelectedTab", "getStripeOnboardingUrl", "getStripeStatus", "getTotalMes", "getTotalPendiente", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class HomeDoctorUiState {
    private final int selectedTab = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String doctorName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String stripeStatus = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String stripeOnboardingUrl = null;
    private final boolean isLoadingStripe = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.DoctorCitaDto> citasHoy = null;
    private final double ingresosHoy = 0.0;
    private final boolean isLoadingHoy = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> pacientes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    private final boolean isLoadingPacientes = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String horarioInicio = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String horarioFin = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> diasLaborables = null;
    private final boolean isSavingHorario = false;
    private final boolean horarioGuardado = false;
    private final double totalMes = 0.0;
    private final double totalPendiente = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.DoctorPayoutDto> payouts = null;
    private final boolean isLoadingIngresos = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String payoutEnviado = null;
    private final boolean isSolicitandoPago = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.dentapp.app.data.model.PatientAlertDto> patientAlerts = null;
    private final int alertasNoVistas = 0;
    private final boolean isLoadingAlerts = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public HomeDoctorUiState(int selectedTab, @org.jetbrains.annotations.NotNull()
    java.lang.String doctorName, @org.jetbrains.annotations.NotNull()
    java.lang.String stripeStatus, @org.jetbrains.annotations.Nullable()
    java.lang.String stripeOnboardingUrl, boolean isLoadingStripe, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorCitaDto> citasHoy, double ingresosHoy, boolean isLoadingHoy, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> pacientes, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoadingPacientes, @org.jetbrains.annotations.NotNull()
    java.lang.String horarioInicio, @org.jetbrains.annotations.NotNull()
    java.lang.String horarioFin, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> diasLaborables, boolean isSavingHorario, boolean horarioGuardado, double totalMes, double totalPendiente, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorPayoutDto> payouts, boolean isLoadingIngresos, @org.jetbrains.annotations.Nullable()
    java.lang.String payoutEnviado, boolean isSolicitandoPago, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.PatientAlertDto> patientAlerts, int alertasNoVistas, boolean isLoadingAlerts, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    public final int getSelectedTab() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDoctorName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStripeStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getStripeOnboardingUrl() {
        return null;
    }
    
    public final boolean isLoadingStripe() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorCitaDto> getCitasHoy() {
        return null;
    }
    
    public final double getIngresosHoy() {
        return 0.0;
    }
    
    public final boolean isLoadingHoy() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> getPacientes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    public final boolean isLoadingPacientes() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHorarioInicio() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHorarioFin() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getDiasLaborables() {
        return null;
    }
    
    public final boolean isSavingHorario() {
        return false;
    }
    
    public final boolean getHorarioGuardado() {
        return false;
    }
    
    public final double getTotalMes() {
        return 0.0;
    }
    
    public final double getTotalPendiente() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorPayoutDto> getPayouts() {
        return null;
    }
    
    public final boolean isLoadingIngresos() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPayoutEnviado() {
        return null;
    }
    
    public final boolean isSolicitandoPago() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.PatientAlertDto> getPatientAlerts() {
        return null;
    }
    
    public final int getAlertasNoVistas() {
        return 0;
    }
    
    public final boolean isLoadingAlerts() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public HomeDoctorUiState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component14() {
        return null;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final double component17() {
        return 0.0;
    }
    
    public final double component18() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorPayoutDto> component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component20() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component21() {
        return null;
    }
    
    public final boolean component22() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.PatientAlertDto> component23() {
        return null;
    }
    
    public final int component24() {
        return 0;
    }
    
    public final boolean component25() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorCitaDto> component6() {
        return null;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.home.HomeDoctorUiState copy(int selectedTab, @org.jetbrains.annotations.NotNull()
    java.lang.String doctorName, @org.jetbrains.annotations.NotNull()
    java.lang.String stripeStatus, @org.jetbrains.annotations.Nullable()
    java.lang.String stripeOnboardingUrl, boolean isLoadingStripe, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorCitaDto> citasHoy, double ingresosHoy, boolean isLoadingHoy, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorPacienteDto> pacientes, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, boolean isLoadingPacientes, @org.jetbrains.annotations.NotNull()
    java.lang.String horarioInicio, @org.jetbrains.annotations.NotNull()
    java.lang.String horarioFin, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> diasLaborables, boolean isSavingHorario, boolean horarioGuardado, double totalMes, double totalPendiente, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.DoctorPayoutDto> payouts, boolean isLoadingIngresos, @org.jetbrains.annotations.Nullable()
    java.lang.String payoutEnviado, boolean isSolicitandoPago, @org.jetbrains.annotations.NotNull()
    java.util.List<com.dentapp.app.data.model.PatientAlertDto> patientAlerts, int alertasNoVistas, boolean isLoadingAlerts, @org.jetbrains.annotations.Nullable()
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