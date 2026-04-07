package com.dentapp.app.ui.components;

import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.input.*;
import androidx.compose.ui.unit.Dp;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\u001a<\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\rH\u0007\u001a(\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a\u008b\u0001\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00072\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\u00132\u0006\u0010\u0014\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\r2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00072\u0015\b\u0002\u0010\u001c\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t\u00a2\u0006\u0002\b\u001dH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001aX\u0010 \u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010!\u001a\u00020\u00012\b\b\u0002\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020#2\u001c\u0010%\u001a\u0018\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050\u0013\u00a2\u0006\u0002\b\u001d\u00a2\u0006\u0002\b\'H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b(\u0010)\u001aB\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020,2\b\b\u0002\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u00020#2\b\b\u0002\u00100\u001a\u00020#2\b\b\u0002\u0010\"\u001a\u00020#H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b1\u00102\u001aV\u00103\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\"\u001a\u00020#2\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u001c\u0010%\u001a\u0018\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00050\u0013\u00a2\u0006\u0002\b\u001d\u00a2\u0006\u0002\b\'H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b4\u00105\u001a<\u00106\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\rH\u0007\u001a\"\u00107\u001a\u00020\u00052\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u0002092\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\"\u0011\u0010\u0000\u001a\u00020\u00018G\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006;"}, d2 = {"PrimaryGradient", "Landroidx/compose/ui/graphics/Brush;", "getPrimaryGradient", "()Landroidx/compose/ui/graphics/Brush;", "DentButton", "", "text", "", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "enabled", "", "isLoading", "DentOutlinedButton", "DentTextField", "value", "onValueChange", "Lkotlin/Function1;", "label", "isPassword", "keyboardType", "Landroidx/compose/ui/text/input/KeyboardType;", "imeAction", "Landroidx/compose/ui/text/input/ImeAction;", "isError", "errorMessage", "leadingIcon", "Landroidx/compose/runtime/Composable;", "DentTextField-P2Qh934", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Ljava/lang/String;Landroidx/compose/ui/Modifier;ZIIZLjava/lang/String;Lkotlin/jvm/functions/Function0;)V", "GradientCard", "gradient", "cornerRadius", "Landroidx/compose/ui/unit/Dp;", "padding", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Lkotlin/ExtensionFunctionType;", "GradientCard--JS8el8", "(Landroidx/compose/ui/Modifier;Landroidx/compose/ui/graphics/Brush;FFLkotlin/jvm/functions/Function1;)V", "IconBadge", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "tint", "Landroidx/compose/ui/graphics/Color;", "containerSize", "iconSize", "IconBadge-aBf7M2Q", "(Landroidx/compose/ui/graphics/vector/ImageVector;JFFF)V", "PremiumCard", "PremiumCard-ziNgDLE", "(Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", "PrimaryButton", "StepIndicator", "currentStep", "", "totalSteps", "app_debug"})
public final class DentComponentsKt {
    
    @androidx.compose.runtime.Composable()
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.ui.graphics.Brush getPrimaryGradient() {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PrimaryButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean enabled, boolean isLoading) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DentButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, boolean enabled, boolean isLoading) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DentOutlinedButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StepIndicator(int currentStep, int totalSteps, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}