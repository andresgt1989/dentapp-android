package com.dentapp.app.ui.ai;

import android.content.Context;
import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.*;
import androidx.compose.foundation.shape.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.outlined.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.*;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.*;
import com.dentapp.app.data.model.*;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a:\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0003\u001a\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0003\u001a\b\u0010\u0013\u001a\u00020\u0004H\u0003\u001a$\u0010\u0014\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0003\u001a\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0003\u001a$\u0010\u001a\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\fH\u0003\u001a\b\u0010\u001c\u001a\u00020\u0004H\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"KEY_DISCLAIMER_ACCEPTED", "", "PREFS_AI", "AiManagerScreen", "", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/dentapp/app/ui/ai/AiManagerViewModel;", "ChatInput", "text", "onTextChange", "Lkotlin/Function1;", "isSending", "", "onSend", "ContextCard", "ctx", "Lcom/dentapp/app/data/model/AiContextResponse;", "EmptyState", "FeedbackRow", "onUtil", "onNoUtil", "MessageBubble", "msg", "Lcom/dentapp/app/data/model/AiMessage;", "QuickReplies", "onSelect", "TypingIndicator", "app_debug"})
public final class AiManagerScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_AI = "ai_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DISCLAIMER_ACCEPTED = "disclaimer_accepted";
    
    @androidx.compose.runtime.Composable()
    public static final void AiManagerScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.dentapp.app.ui.ai.AiManagerViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ContextCard(com.dentapp.app.data.model.AiContextResponse ctx) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MessageBubble(com.dentapp.app.data.model.AiMessage msg) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FeedbackRow(kotlin.jvm.functions.Function0<kotlin.Unit> onUtil, kotlin.jvm.functions.Function0<kotlin.Unit> onNoUtil) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TypingIndicator() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuickReplies(boolean isSending, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ChatInput(java.lang.String text, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTextChange, boolean isSending, kotlin.jvm.functions.Function0<kotlin.Unit> onSend) {
    }
}