package com.dentapp.app.ui.home;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.*;
import android.content.Intent;
import android.net.Uri;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.dentapp.app.data.model.AppointmentDto;
import com.dentapp.app.data.model.DoctorDto;
import com.dentapp.app.ui.tratamiento.TratamientoDto;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000x\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001aV\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a \u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u001e\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001ap\u0010\u0014\u001a\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f28\b\u0002\u0010\u0016\u001a2\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u00010\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u001e\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00182\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u00f8\u0001\u0010\u001f\u001a\u00020\u00012\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\f28\b\u0002\u0010\u0016\u001a2\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u00010\u00172\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010*\u001a\u00020+H\u0007\u001a\u00a6\u0001\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020.2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0001012\u000e\b\u0002\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u001a\b\u0002\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a8\u00102\u001a\u00020\u00012\u0006\u00103\u001a\u00020\u00182\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001aV\u00107\u001a\u00020\u00012\u0006\u00104\u001a\u0002052\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00182\u0006\u0010;\u001a\u00020\u00182\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u00182\b\b\u0002\u0010=\u001a\u000209H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b>\u0010?\u001a\u00a0\u0001\u0010@\u001a\u00020\u00012\u0006\u0010A\u001a\u00020\u00182\u0006\u0010B\u001a\u00020\u00182\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a$\u0010C\u001a\u00020\u00012\u0006\u0010D\u001a\u00020E2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020\u000101H\u0003\u001aB\u0010G\u001a\u00020\u00012\u0006\u00104\u001a\u0002052\u0006\u00103\u001a\u00020\u00182\u0006\u0010H\u001a\u0002092\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\bI\u0010J\u001a\u001e\u0010K\u001a\u00020\u00012\u0006\u0010L\u001a\u00020M2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006N"}, d2 = {"AppointmentCard", "", "appointment", "Lcom/dentapp/app/data/model/AppointmentDto;", "modifier", "Landroidx/compose/ui/Modifier;", "CitasTab", "appointments", "", "isLoading", "", "onRefresh", "Lkotlin/Function0;", "onSearchDoctors", "onOpenAiManager", "DoctorCard", "doctor", "Lcom/dentapp/app/data/model/DoctorDto;", "onClick", "DoctorMiniCard", "DoctoresTab", "doctors", "onOpenBooking", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "doctorId", "doctorName", "HeroChip", "text", "HomePatientScreen", "onLogout", "onOpenExpediente", "onOpenHistorialMedico", "onOpenMisDatos", "onOpenHistorialCitas", "onOpenNotificaciones", "onOpenPrivacidad", "onOpenTratamientos", "onOpenRx", "onOpenLoyalty", "viewModel", "Lcom/dentapp/app/ui/home/HomePatientViewModel;", "InicioTab", "state", "Lcom/dentapp/app/ui/home/HomePatientState;", "onOpenCitas", "onSendUrgencia", "Lkotlin/Function1;", "NavBarItem", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "selected", "PerfilMenuCard", "iconColor", "Landroidx/compose/ui/graphics/Color;", "titulo", "subtitulo", "badge", "badgeColor", "PerfilMenuCard-voDFQdg", "(Landroidx/compose/ui/graphics/vector/ImageVector;JLjava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;Ljava/lang/String;J)V", "PerfilTab", "patientName", "email", "PremiumNavBar", "selectedTab", "", "onTabSelected", "QuickActionCard", "color", "QuickActionCard-XO-JAsU", "(Landroidx/compose/ui/graphics/vector/ImageVector;Ljava/lang/String;JLkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;)V", "TreatmentMiniCard", "tratamiento", "Lcom/dentapp/app/ui/tratamiento/TratamientoDto;", "app_debug"})
public final class HomePatientScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HomePatientScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenAiManager, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenExpediente, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onOpenBooking, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenHistorialMedico, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenMisDatos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenHistorialCitas, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenNotificaciones, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenPrivacidad, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenTratamientos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenRx, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenLoyalty, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.home.HomePatientViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PremiumNavBar(int selectedTab, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onTabSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NavBarItem(java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InicioTab(com.dentapp.app.ui.home.HomePatientState state, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenAiManager, kotlin.jvm.functions.Function0<kotlin.Unit> onSearchDoctors, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenCitas, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenRx, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenLoyalty, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSendUrgencia, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenTratamientos, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onOpenBooking, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HeroChip(java.lang.String text, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TreatmentMiniCard(com.dentapp.app.ui.tratamiento.TratamientoDto tratamiento, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DoctorMiniCard(com.dentapp.app.data.model.DoctorDto doctor, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DoctoresTab(java.util.List<com.dentapp.app.data.model.DoctorDto> doctors, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onOpenBooking, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CitasTab(java.util.List<com.dentapp.app.data.model.AppointmentDto> appointments, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function0<kotlin.Unit> onSearchDoctors, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenAiManager, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PerfilTab(java.lang.String patientName, java.lang.String email, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenExpediente, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenHistorialMedico, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenMisDatos, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenHistorialCitas, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenNotificaciones, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenPrivacidad, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenTratamientos, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DoctorCard(com.dentapp.app.data.model.DoctorDto doctor, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppointmentCard(com.dentapp.app.data.model.AppointmentDto appointment, androidx.compose.ui.Modifier modifier) {
    }
}