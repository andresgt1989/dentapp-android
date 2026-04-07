package com.dentapp.app.ui.auth;

import android.app.Activity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.animation.*;
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.dentapp.app.R;
import com.dentapp.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\'\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001Be\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\u0081\u0001\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020.H\u00d6\u0001J\t\u0010/\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u00060"}, d2 = {"Lcom/dentapp/app/ui/auth/Strings;", "", "tagline", "", "subtitle", "trust", "googleBtn", "orDivider", "emailLabel", "passwordLabel", "loginBtn", "noAccount", "signUpLink", "errorEmailInvalid", "errorPasswordShort", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEmailLabel", "()Ljava/lang/String;", "getErrorEmailInvalid", "getErrorPasswordShort", "getGoogleBtn", "getLoginBtn", "getNoAccount", "getOrDivider", "getPasswordLabel", "getSignUpLink", "getSubtitle", "getTagline", "getTrust", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
final class Strings {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tagline = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String subtitle = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String trust = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String googleBtn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String orDivider = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String emailLabel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String passwordLabel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String loginBtn = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String noAccount = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String signUpLink = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String errorEmailInvalid = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String errorPasswordShort = null;
    
    public Strings(@org.jetbrains.annotations.NotNull()
    java.lang.String tagline, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    java.lang.String trust, @org.jetbrains.annotations.NotNull()
    java.lang.String googleBtn, @org.jetbrains.annotations.NotNull()
    java.lang.String orDivider, @org.jetbrains.annotations.NotNull()
    java.lang.String emailLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String passwordLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String loginBtn, @org.jetbrains.annotations.NotNull()
    java.lang.String noAccount, @org.jetbrains.annotations.NotNull()
    java.lang.String signUpLink, @org.jetbrains.annotations.NotNull()
    java.lang.String errorEmailInvalid, @org.jetbrains.annotations.NotNull()
    java.lang.String errorPasswordShort) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTagline() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubtitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTrust() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getGoogleBtn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOrDivider() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmailLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPasswordLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLoginBtn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNoAccount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSignUpLink() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getErrorEmailInvalid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getErrorPasswordShort() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.ui.auth.Strings copy(@org.jetbrains.annotations.NotNull()
    java.lang.String tagline, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    java.lang.String trust, @org.jetbrains.annotations.NotNull()
    java.lang.String googleBtn, @org.jetbrains.annotations.NotNull()
    java.lang.String orDivider, @org.jetbrains.annotations.NotNull()
    java.lang.String emailLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String passwordLabel, @org.jetbrains.annotations.NotNull()
    java.lang.String loginBtn, @org.jetbrains.annotations.NotNull()
    java.lang.String noAccount, @org.jetbrains.annotations.NotNull()
    java.lang.String signUpLink, @org.jetbrains.annotations.NotNull()
    java.lang.String errorEmailInvalid, @org.jetbrains.annotations.NotNull()
    java.lang.String errorPasswordShort) {
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