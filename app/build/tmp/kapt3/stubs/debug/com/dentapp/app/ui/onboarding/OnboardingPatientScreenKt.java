package com.dentapp.app.ui.onboarding;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a.\u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u001a:\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0006\u0010\u0012\u001a\u00020\u0013H\u0003\u001a$\u0010\u0014\u001a\u00020\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0017"}, d2 = {"ConversationalOnboardingStep", "", "viewModel", "Lcom/dentapp/app/ui/onboarding/OnboardingViewModel;", "onFinish", "Lkotlin/Function0;", "OnboardingHeader", "step", "", "onBack", "OnboardingPatientScreen", "onSuccess", "SecretaryStep", "inviteCode", "", "onCodeChange", "Lkotlin/Function1;", "onContinue", "isSaving", "", "WelcomeStep", "onStart", "onSkip", "app_debug"})
public final class OnboardingPatientScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void OnboardingPatientScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.onboarding.OnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OnboardingHeader(int step, kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WelcomeStep(kotlin.jvm.functions.Function0<kotlin.Unit> onStart, kotlin.jvm.functions.Function0<kotlin.Unit> onSkip) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ConversationalOnboardingStep(com.dentapp.app.ui.onboarding.OnboardingViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onFinish) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SecretaryStep(java.lang.String inviteCode, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCodeChange, kotlin.jvm.functions.Function0<kotlin.Unit> onContinue, boolean isSaving) {
    }
}