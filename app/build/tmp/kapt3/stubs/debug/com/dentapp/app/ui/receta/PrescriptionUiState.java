package com.dentapp.app.ui.receta;

import android.graphics.Bitmap;
import android.util.Base64;
import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.MedicamentoOcr;
import com.dentapp.app.data.model.RecetaOcrRequest;
import com.dentapp.app.data.model.RecetaOcrResponse;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.ByteArrayOutputStream;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001a\b\u0087\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\fH\u00c6\u0003JW\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010\"\u001a\u00020\u00072\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020\u0003H\u00d6\u0001J\t\u0010%\u001a\u00020\fH\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\r\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014\u00a8\u0006&"}, d2 = {"Lcom/dentapp/app/ui/receta/PrescriptionUiState;", "", "paso", "", "capturedBitmap", "Landroid/graphics/Bitmap;", "disclaimerAceptado", "", "isLoading", "resultado", "Lcom/dentapp/app/data/model/RecetaOcrResponse;", "error", "", "snackbarMessage", "(ILandroid/graphics/Bitmap;ZZLcom/dentapp/app/data/model/RecetaOcrResponse;Ljava/lang/String;Ljava/lang/String;)V", "getCapturedBitmap", "()Landroid/graphics/Bitmap;", "getDisclaimerAceptado", "()Z", "getError", "()Ljava/lang/String;", "getPaso", "()I", "getResultado", "()Lcom/dentapp/app/data/model/RecetaOcrResponse;", "getSnackbarMessage", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class PrescriptionUiState {
    private final int paso = 0;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.Bitmap capturedBitmap = null;
    private final boolean disclaimerAceptado = false;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final com.dentapp.app.data.model.RecetaOcrResponse resultado = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String snackbarMessage = null;
    
    public PrescriptionUiState(int paso, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap capturedBitmap, boolean disclaimerAceptado, boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.data.model.RecetaOcrResponse resultado, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String snackbarMessage) {
        super();
    }
    
    public final int getPaso() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getCapturedBitmap() {
        return null;
    }
    
    public final boolean getDisclaimerAceptado() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.data.model.RecetaOcrResponse getResultado() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSnackbarMessage() {
        return null;
    }
    
    public PrescriptionUiState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.dentapp.app.data.model.RecetaOcrResponse component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.receta.PrescriptionUiState copy(int paso, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap capturedBitmap, boolean disclaimerAceptado, boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.dentapp.app.data.model.RecetaOcrResponse resultado, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String snackbarMessage) {
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