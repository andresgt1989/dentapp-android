package com.dentapp.app.ui.perfil;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000X\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a^\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\n2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a \u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0007\u001aF\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\n2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0003\u001a6\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001e2\u001c\u0010\u001f\u001a\u0018\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b!\u00a2\u0006\u0002\b\"H\u0003\u001a6\u0010#\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0003\u00a8\u0006$"}, d2 = {"AiCompletnessBadge", "", "completeness", "", "BifosfonatosRow", "checked", "", "onChecked", "Lkotlin/Function1;", "name", "", "onNameChange", "route", "onRouteChange", "HistorialMedicoScreen", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/dentapp/app/ui/perfil/HistorialMedicoViewModel;", "LabeledDropdown", "label", "options", "", "selected", "onSelect", "modifier", "Landroidx/compose/ui/Modifier;", "SeccionCard", "titulo", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SwitchField", "app_debug"})
public final class HistorialMedicoScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HistorialMedicoScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.perfil.HistorialMedicoViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AiCompletnessBadge(int completeness) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SeccionCard(java.lang.String titulo, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SwitchField(java.lang.String label, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onChecked, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void LabeledDropdown(java.lang.String label, java.util.List<java.lang.String> options, java.lang.String selected, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BifosfonatosRow(boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onChecked, java.lang.String name, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, java.lang.String route, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRouteChange) {
    }
}