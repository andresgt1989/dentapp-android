package com.dentapp.app.ui.radiografia;

import android.graphics.Bitmap;
import android.util.Base64;
import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.RadiografiaUploadRequest;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.ByteArrayOutputStream;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\rJ\u0006\u0010\u0014\u001a\u00020\rJ\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/dentapp/app/ui/radiografia/XRayCaptureViewModel;", "Landroidx/lifecycle/ViewModel;", "api", "Lcom/dentapp/app/data/api/ApiService;", "(Lcom/dentapp/app/data/api/ApiService;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/dentapp/app/ui/radiografia/XRayUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "analizarImagen", "", "bitmap", "Landroid/graphics/Bitmap;", "bitmapToBase64", "", "capturarImagen", "irACamara", "reintentar", "todasCondicionesOk", "", "toggleCondicion", "index", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class XRayCaptureViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.dentapp.app.data.api.ApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.dentapp.app.ui.radiografia.XRayUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.radiografia.XRayUiState> state = null;
    
    @javax.inject.Inject()
    public XRayCaptureViewModel(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.api.ApiService api) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.dentapp.app.ui.radiografia.XRayUiState> getState() {
        return null;
    }
    
    public final void toggleCondicion(int index) {
    }
    
    public final boolean todasCondicionesOk() {
        return false;
    }
    
    public final void irACamara() {
    }
    
    public final void capturarImagen(@org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap) {
    }
    
    private final void analizarImagen(android.graphics.Bitmap bitmap) {
    }
    
    public final void reintentar() {
    }
    
    private final java.lang.String bitmapToBase64(android.graphics.Bitmap bitmap) {
        return null;
    }
}