package com.dentapp.app.ui.navigation;

import androidx.compose.runtime.*;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.NavType;
import androidx.navigation.compose.*;
import com.dentapp.app.ui.tratamiento.TratamientoDto;
import com.dentapp.app.utils.TokenStore;
import java.net.URLDecoder;
import java.net.URLEncoder;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J\u000e\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/dentapp/app/ui/navigation/Routes;", "", "()V", "AI_MANAGER", "", "BOOKING", "EXPEDIENTE", "GENERAR_QR", "HISTORIAL_CITAS", "HISTORIAL_MEDICO", "HOME_DOCTOR", "HOME_PATIENT", "LOGIN", "LOYALTY", "MIS_DATOS", "NOTIFICACIONES", "ONBOARDING_DOCTOR", "ONBOARDING_PATIENT", "PRESCRIPTION_SCAN", "PRIVACIDAD", "REGISTER", "RX_PATIENT", "SPLASH", "SUBSCRIPTION", "TRATAMIENTOS", "TRATAMIENTO_DETALLE", "XRAY_CAPTURE", "booking", "doctorId", "doctorName", "tratamientoDetalle", "tratamiento", "Lcom/dentapp/app/ui/tratamiento/TratamientoDto;", "app_debug"})
public final class Routes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SPLASH = "splash";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "login";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REGISTER = "register";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ONBOARDING_PATIENT = "onboarding_patient";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ONBOARDING_DOCTOR = "onboarding_doctor";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HOME_PATIENT = "home_patient";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HOME_DOCTOR = "home_doctor";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String AI_MANAGER = "ai_manager";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BOOKING = "booking/{doctorId}/{doctorName}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXPEDIENTE = "expediente";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HISTORIAL_MEDICO = "historial_medico";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String MIS_DATOS = "mis_datos";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String HISTORIAL_CITAS = "historial_citas";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICACIONES = "notificaciones";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRIVACIDAD = "privacidad";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TRATAMIENTOS = "tratamientos";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TRATAMIENTO_DETALLE = "tratamiento_detalle/{tratamientoJson}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GENERAR_QR = "generar_qr";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String XRAY_CAPTURE = "xray_capture";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PRESCRIPTION_SCAN = "prescription_scan";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SUBSCRIPTION = "subscription";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOYALTY = "loyalty";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RX_PATIENT = "rx_patient";
    @org.jetbrains.annotations.NotNull()
    public static final com.dentapp.app.ui.navigation.Routes INSTANCE = null;
    
    private Routes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String booking(@org.jetbrains.annotations.NotNull()
    java.lang.String doctorId, @org.jetbrains.annotations.NotNull()
    java.lang.String doctorName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String tratamientoDetalle(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.tratamiento.TratamientoDto tratamiento) {
        return null;
    }
}