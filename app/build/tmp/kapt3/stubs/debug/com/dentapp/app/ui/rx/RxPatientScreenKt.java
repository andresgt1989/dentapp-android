package com.dentapp.app.ui.rx;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModel;
import com.dentapp.app.data.api.ApiService;
import com.dentapp.app.data.model.ManualPrescriptionRequest;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001aK\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032#\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\r\u00a2\u0006\u0002\b\u000e\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a \u0010\u0010\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u00a8\u0006\u0017"}, d2 = {"CameraTab", "", "photoTaken", "", "photoUri", "Landroid/net/Uri;", "onLaunchCamera", "Lkotlin/Function0;", "ManualFormTab", "form", "Lcom/dentapp/app/ui/rx/RxForm;", "saving", "onUpdateForm", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "onSave", "RxPatientScreen", "onBack", "viewModel", "Lcom/dentapp/app/ui/rx/RxPatientViewModel;", "createImageUri", "context", "Landroid/content/Context;", "app_debug"})
public final class RxPatientScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RxPatientScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.rx.RxPatientViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CameraTab(boolean photoTaken, android.net.Uri photoUri, kotlin.jvm.functions.Function0<kotlin.Unit> onLaunchCamera) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ManualFormTab(com.dentapp.app.ui.rx.RxForm form, boolean saving, kotlin.jvm.functions.Function1<? super kotlin.jvm.functions.Function1<? super com.dentapp.app.ui.rx.RxForm, com.dentapp.app.ui.rx.RxForm>, kotlin.Unit> onUpdateForm, kotlin.jvm.functions.Function0<kotlin.Unit> onSave) {
    }
    
    private static final android.net.Uri createImageUri(android.content.Context context) {
        return null;
    }
}