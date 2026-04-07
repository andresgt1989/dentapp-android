package com.dentapp.app.ui.perfil;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u001c\u0010\u0006\u001a\u0018\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\u0002\b\t\u00a2\u0006\u0002\b\nH\u0003\u001aJ\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00072\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u001a \u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007\u00a8\u0006\u0017"}, d2 = {"DatosCard", "", "titulo", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "DatosField", "value", "onValueChange", "label", "placeholder", "keyboardOptions", "Landroidx/compose/foundation/text/KeyboardOptions;", "MisDatosScreen", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/dentapp/app/ui/perfil/MisDatosViewModel;", "app_debug"})
public final class MisDatosScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MisDatosScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.perfil.MisDatosViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DatosCard(java.lang.String titulo, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DatosField(java.lang.String value, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String placeholder, androidx.compose.foundation.text.KeyboardOptions keyboardOptions) {
    }
}