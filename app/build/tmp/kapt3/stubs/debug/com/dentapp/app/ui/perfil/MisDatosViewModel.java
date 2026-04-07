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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u001f\u0010\u0010\u001a\u00020\r2\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\u0002\b\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/dentapp/app/ui/perfil/MisDatosViewModel;", "Landroidx/lifecycle/ViewModel;", "api", "Lcom/dentapp/app/data/api/ApiService;", "(Lcom/dentapp/app/data/api/ApiService;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/perfil/MisDatosState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "cargar", "", "clearMessages", "guardar", "updateForm", "block", "Lkotlin/Function1;", "Lcom/dentapp/app/ui/perfil/MisDatosForm;", "Lkotlin/ExtensionFunctionType;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MisDatosViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.perfil.MisDatosState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.perfil.MisDatosState> state = null;
    
    @javax.inject.Inject()
    public MisDatosViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.perfil.MisDatosState> getState() {
        return null;
    }
    
    private final void cargar() {
    }
    
    public final void updateForm(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.dentapp.app.ui.perfil.MisDatosForm, com.dentapp.app.ui.perfil.MisDatosForm> block) {
    }
    
    public final void guardar() {
    }
    
    public final void clearMessages() {
    }
}