package com.dentapp.app.ui.perfil;

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
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a*\u0010\t\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00050\f0\u000bH\u0003\u001a.\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u00a8\u0006\u0012"}, d2 = {"PrivAction", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "titulo", "", "descripcion", "onClick", "Lkotlin/Function0;", "PrivCardInfo", "items", "", "Lkotlin/Pair;", "PrivacidadScreen", "onBack", "onAccountDeleted", "viewModel", "Lcom/dentapp/app/ui/perfil/PrivacidadViewModel;", "app_debug"})
public final class PrivacidadScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PrivacidadScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAccountDeleted, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.perfil.PrivacidadViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PrivCardInfo(java.lang.String titulo, java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> items) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PrivAction(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String titulo, java.lang.String descripcion, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}