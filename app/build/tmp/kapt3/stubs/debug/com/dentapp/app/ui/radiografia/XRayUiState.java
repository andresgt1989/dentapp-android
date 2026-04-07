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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0018\b\u0087\b\u0018\u00002\u00020\u0001BM\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0004\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0004H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\rH\u00c6\u0003JQ\u0010 \u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010!\u001a\u00020\u00042\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\rH\u00d6\u0001J\t\u0010$\u001a\u00020\u000bH\u00d6\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0015R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006%"}, d2 = {"Lcom/dentapp/app/ui/radiografia/XRayUiState;", "", "condicionesOk", "", "", "capturedBitmap", "Landroid/graphics/Bitmap;", "isAnalyzing", "resultado", "Lcom/dentapp/app/ui/radiografia/XRayResultado;", "error", "", "paso", "", "(Ljava/util/List;Landroid/graphics/Bitmap;ZLcom/dentapp/app/ui/radiografia/XRayResultado;Ljava/lang/String;I)V", "getCapturedBitmap", "()Landroid/graphics/Bitmap;", "getCondicionesOk", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "()Z", "getPaso", "()I", "getResultado", "()Lcom/dentapp/app/ui/radiografia/XRayResultado;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class XRayUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Boolean> condicionesOk = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.Bitmap capturedBitmap = null;
    private final boolean isAnalyzing = false;
    @org.jetbrains.annotations.Nullable()
    private final com.dentapp.app.ui.radiografia.XRayResultado resultado = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final int paso = 0;
    
    public XRayUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Boolean> condicionesOk, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap capturedBitmap, boolean isAnalyzing, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.ui.radiografia.XRayResultado resultado, @org.jetbrains.annotations.Nullable()
    java.lang.String error, int paso) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Boolean> getCondicionesOk() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getCapturedBitmap() {
        return null;
    }
    
    public final boolean isAnalyzing() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.ui.radiografia.XRayResultado getResultado() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final int getPaso() {
        return 0;
    }
    
    public XRayUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Boolean> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.ui.radiografia.XRayResultado component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.radiografia.XRayUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Boolean> condicionesOk, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap capturedBitmap, boolean isAnalyzing, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.ui.radiografia.XRayResultado resultado, @org.jetbrains.annotations.Nullable()
    java.lang.String error, int paso) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}