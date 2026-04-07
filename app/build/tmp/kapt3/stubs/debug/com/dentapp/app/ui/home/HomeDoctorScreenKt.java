package com.dentapp.app.ui.home;

import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.dentapp.app.data.model.*;
import com.dentapp.app.ui.theme.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000`\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0003\u001a \u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0003\u001a6\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u001e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0019H\u0003\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u000bH\u0003\u001a>\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0002\b$\u001a\u0010\u0010%\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u000bH\u0003\u001a\u0018\u0010&\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0012\u001a\u00020\u0013H\u0003\u001a$\u0010)\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u0011H\u0003\u001a\u001e\u0010+\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a2\u0010-\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u0011H\u0003\u001a,\u00100\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u0011H\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00062"}, d2 = {"AlertaPacienteCard", "", "alerta", "Lcom/dentapp/app/data/model/PatientAlertDto;", "onClick", "Lkotlin/Function0;", "CitaDoctorCard", "cita", "Lcom/dentapp/app/data/model/DoctorCitaDto;", "EmptyState", "icon", "", "titulo", "subtitulo", "HomeDoctorScreen", "onLogout", "onVerExpedientePaciente", "Lkotlin/Function1;", "viewModel", "Lcom/dentapp/app/ui/home/HomeDoctorViewModel;", "PacienteCard", "paciente", "Lcom/dentapp/app/data/model/DoctorPacienteDto;", "PayoutCard", "payout", "Lcom/dentapp/app/data/model/DoctorPayoutDto;", "PayoutStatusChip", "status", "StatCardDoctor", "label", "value", "Landroidx/compose/ui/graphics/vector/ImageVector;", "modifier", "Landroidx/compose/ui/Modifier;", "highlightColor", "Landroidx/compose/ui/graphics/Color;", "StatCardDoctor-6nskv5g", "StatusChipDoctor", "TabAgenda", "state", "Lcom/dentapp/app/ui/home/HomeDoctorUiState;", "TabAlertas", "onVerPaciente", "TabHoy", "onRefresh", "TabIngresos", "onSolicitarPago", "onStripeConnect", "TabPacientes", "onVerExpediente", "app_debug"})
public final class HomeDoctorScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeDoctorScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerExpedientePaciente, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.home.HomeDoctorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TabHoy(com.dentapp.app.ui.home.HomeDoctorUiState state, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CitaDoctorCard(com.dentapp.app.data.model.DoctorCitaDto cita) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TabPacientes(com.dentapp.app.ui.home.HomeDoctorUiState state, com.dentapp.app.ui.home.HomeDoctorViewModel viewModel, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerExpediente) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PacienteCard(com.dentapp.app.data.model.DoctorPacienteDto paciente, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TabAgenda(com.dentapp.app.ui.home.HomeDoctorUiState state, com.dentapp.app.ui.home.HomeDoctorViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TabIngresos(com.dentapp.app.ui.home.HomeDoctorUiState state, kotlin.jvm.functions.Function0<kotlin.Unit> onSolicitarPago, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStripeConnect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PayoutCard(com.dentapp.app.data.model.DoctorPayoutDto payout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PayoutStatusChip(java.lang.String status) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TabAlertas(com.dentapp.app.ui.home.HomeDoctorUiState state, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onVerPaciente) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AlertaPacienteCard(com.dentapp.app.data.model.PatientAlertDto alerta, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusChipDoctor(java.lang.String status) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(java.lang.String icon, java.lang.String titulo, java.lang.String subtitulo) {
    }
}