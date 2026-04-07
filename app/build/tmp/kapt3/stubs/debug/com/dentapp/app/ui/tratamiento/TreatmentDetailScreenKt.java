package com.dentapp.app.ui.tratamiento;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a(\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\b\u0010\n\u001a\u0004\u0018\u00010\u0007H\u0003\u001a2\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u000eH\u0003\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0018\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\u0007H\u0003\u001a\u0018\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H\u0003\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u001e\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0003\u001a*\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\"\u001a\u001e\u0010#\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00032\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010%H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006&"}, d2 = {"BlanqueamientoDetailCard", "", "t", "Lcom/dentapp/app/ui/tratamiento/TratamientoDto;", "EndodonciaDetailCard", "EtapasCard", "titulo", "", "fases", "", "faseActual", "FaseRow", "label", "completada", "", "actual", "pendiente", "esCritica", "GenericDetailCard", "ImplanteDetailCard", "InfoChip", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "InfoRow", "value", "OrtodonciaDetailCard", "PeriodonciDetailCard", "TipsCard", "tips", "TratHeaderCard", "tratamiento", "color", "Landroidx/compose/ui/graphics/Color;", "TratHeaderCard-mxwnekA", "(Lcom/dentapp/app/ui/tratamiento/TratamientoDto;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "TreatmentDetailScreen", "onBack", "Lkotlin/Function0;", "app_debug"})
public final class TreatmentDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TreatmentDetailScreen(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.tratamiento.TratamientoDto tratamiento, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoChip(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EndodonciaDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FaseRow(java.lang.String label, boolean completada, boolean actual, boolean pendiente, boolean esCritica) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OrtodonciaDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ImplanteDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PeriodonciDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BlanqueamientoDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void GenericDetailCard(com.dentapp.app.ui.tratamiento.TratamientoDto t) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EtapasCard(java.lang.String titulo, java.util.List<java.lang.String> fases, java.lang.String faseActual) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TipsCard(java.lang.String titulo, java.util.List<java.lang.String> tips) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoRow(java.lang.String label, java.lang.String value) {
    }
}