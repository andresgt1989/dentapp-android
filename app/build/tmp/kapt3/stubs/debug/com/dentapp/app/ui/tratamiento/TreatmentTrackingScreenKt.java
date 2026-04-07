package com.dentapp.app.ui.tratamiento;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.serialization.SerialName;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.json.JsonObject;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a4\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a8\u0006\u0013"}, d2 = {"TratamientoCard", "", "tratamiento", "Lcom/dentapp/app/ui/tratamiento/TratamientoDto;", "onClick", "Lkotlin/Function0;", "TreatmentTrackingScreen", "onBack", "onVerDetalle", "Lkotlin/Function1;", "viewModel", "Lcom/dentapp/app/ui/tratamiento/TreatmentTrackingViewModel;", "tratamientoColor", "Landroidx/compose/ui/graphics/Color;", "tipo", "", "(Ljava/lang/String;)J", "tratamientoIcon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "app_debug"})
public final class TreatmentTrackingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TreatmentTrackingScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.dentapp.app.ui.tratamiento.TratamientoDto, kotlin.Unit> onVerDetalle, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.tratamiento.TreatmentTrackingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TratamientoCard(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.tratamiento.TratamientoDto tratamiento, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.ui.graphics.vector.ImageVector tratamientoIcon(@org.jetbrains.annotations.NotNull()
    java.lang.String tipo) {
        return null;
    }
    
    public static final long tratamientoColor(@org.jetbrains.annotations.NotNull()
    java.lang.String tipo) {
        return 0L;
    }
}