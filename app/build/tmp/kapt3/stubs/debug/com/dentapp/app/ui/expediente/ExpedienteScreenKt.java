package com.dentapp.app.ui.expediente;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.dentapp.app.data.model.DentalRecordDto;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a*\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000b\u001a \u0010\f\u001a\u00020\u00012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0015\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010\u0016\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0017"}, d2 = {"DentalRecordCard", "", "record", "Lcom/dentapp/app/data/model/DentalRecordDto;", "DetailChip", "label", "", "value", "color", "Landroidx/compose/ui/graphics/Color;", "DetailChip-mxwnekA", "(Ljava/lang/String;Ljava/lang/String;J)V", "ExpedienteScreen", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/dentapp/app/ui/expediente/ExpedienteViewModel;", "mesAnioLabel", "month", "", "procedimientoColor", "procedimiento", "(Ljava/lang/String;)J", "app_debug"})
public final class ExpedienteScreenKt {
    
    private static final long procedimientoColor(java.lang.String procedimiento) {
        return 0L;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExpedienteScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.expediente.ExpedienteViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DentalRecordCard(com.dentapp.app.data.model.DentalRecordDto record) {
    }
    
    private static final java.lang.String mesAnioLabel(int month) {
        return null;
    }
}