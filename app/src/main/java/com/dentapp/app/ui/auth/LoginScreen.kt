package com.dentapp.app.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.dentapp.app.R
import com.dentapp.app.ui.components.DentTextField
import com.dentapp.app.ui.theme.*

// ── Multilingual support ─────────────────────────────────────────────────────

private enum class AppLanguage(val code: String, val label: String) {
    ES("es", "ES"), EN("en", "EN"), PT("pt", "PT")
}

private data class Strings(
    val tagline: String,
    val subtitle: String,
    val trust: String,
    val googleBtn: String,
    val orDivider: String,
    val emailLabel: String,
    val passwordLabel: String,
    val loginBtn: String,
    val noAccount: String,
    val signUpLink: String,
    val errorEmailInvalid: String,
    val errorPasswordShort: String,
)

private val translations = mapOf(
    AppLanguage.ES to Strings(
        tagline          = "Tu dentista en minutos 🦷",
        subtitle         = "Agenda tu cita en menos de 60 segundos",
        trust            = "⭐ 4.9  ·  50K+ pacientes  ·  🔒 100% seguro",
        googleBtn        = "Continuar con Google",
        orDivider        = "— o ingresa con tu correo —",
        emailLabel       = "Correo electrónico",
        passwordLabel    = "Contraseña",
        loginBtn         = "Entrar",
        noAccount        = "¿No tienes cuenta? ",
        signUpLink       = "Regístrate gratis",
        errorEmailInvalid  = "Por favor ingresa un email válido",
        errorPasswordShort = "La contraseña debe tener al menos 6 caracteres",
    ),
    AppLanguage.EN to Strings(
        tagline          = "Your dentist in minutes 🦷",
        subtitle         = "Book an appointment in under 60 seconds",
        trust            = "⭐ 4.9  ·  50K+ patients  ·  🔒 100% secure",
        googleBtn        = "Continue with Google",
        orDivider        = "— or sign in with email —",
        emailLabel       = "Email address",
        passwordLabel    = "Password",
        loginBtn         = "Sign in",
        noAccount        = "Don't have an account? ",
        signUpLink       = "Sign up free",
        errorEmailInvalid  = "Please enter a valid email",
        errorPasswordShort = "Password must be at least 6 characters",
    ),
    AppLanguage.PT to Strings(
        tagline          = "Seu dentista em minutos 🦷",
        subtitle         = "Agende sua consulta em menos de 60 segundos",
        trust            = "⭐ 4.9  ·  50K+ pacientes  ·  🔒 100% seguro",
        googleBtn        = "Continuar com Google",
        orDivider        = "— ou entre com seu e-mail —",
        emailLabel       = "E-mail",
        passwordLabel    = "Senha",
        loginBtn         = "Entrar",
        noAccount        = "Não tem conta? ",
        signUpLink       = "Cadastre-se grátis",
        errorEmailInvalid  = "Por favor insira um e-mail válido",
        errorPasswordShort = "A senha deve ter pelo menos 6 caracteres",
    ),
)

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state   by viewModel.state.collectAsState()
    val context = LocalContext.current
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var lang     by remember { mutableStateOf(AppLanguage.ES) }
    val str      = translations[lang]!!

    // Google Sign-In launcher
    val webClientId = remember { try { context.getString(R.string.google_web_client_id) } catch (_: Exception) { "" } }
    val googleClient = remember(webClientId) {
        if (webClientId.isNotBlank() && !webClientId.startsWith("YOUR_")) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
            GoogleSignIn.getClient(context, gso)
        } else null
    }
    val googleLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val account = GoogleSignIn
                    .getSignedInAccountFromIntent(result.data)
                    .getResult(ApiException::class.java)
                account.idToken?.let { viewModel.signInWithGoogle(it) }
            } catch (e: ApiException) {
                viewModel.setError("Google Sign-In error: ${e.statusCode}")
            }
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) onLoginSuccess(state.role ?: "patient")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF1565C0),
                        0.42f to Color(0xFF0D47A1),
                        0.42f to Color(0xFFF0F4FF),
                        1f to Color(0xFFF0F4FF),
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            // ── Language toggle ─────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                AppLanguage.entries.forEach { l ->
                    val selected = lang == l
                    TextButton(
                        onClick = { lang = l },
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                    ) {
                        Text(
                            l.label,
                            color = if (selected) Color.White else Color.White.copy(0.55f),
                            fontSize = 13.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                }
            }

            // ── Hero section ────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(R.drawable.ic_logo),
                        contentDescription = "DentApp",
                        modifier = Modifier.size(56.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    str.tagline,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp),
                    lineHeight = 30.sp,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    str.subtitle,
                    color = Color.White.copy(alpha = 0.82f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 40.dp),
                )
                Spacer(Modifier.height(14.dp))
                // Trust badge
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = Color.White.copy(alpha = 0.18f),
                ) {
                    Text(
                        str.trust,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            // ── Form card ───────────────────────────────────────────────────
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFFF0F4FF),
                shadowElevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {

                    // ── Google Sign-In button ───────────────────────────────
                    OutlinedButton(
                        onClick = {
                            viewModel.clearError()
                            if (googleClient != null) {
                                googleLauncher.launch(googleClient.signInIntent)
                            } else {
                                viewModel.setError("Configura google_web_client_id en strings.xml")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1F1F1F),
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFFDADCE0)),
                        enabled = !state.isLoading,
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color(0xFF1565C0),
                                strokeWidth = 2.dp,
                            )
                        } else {
                            androidx.compose.foundation.Image(
                                painter = painterResource(R.drawable.ic_google),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                str.googleBtn,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1F1F1F),
                            )
                        }
                    }

                    // ── Divider ─────────────────────────────────────────────
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFD1D5DB))
                        Text(
                            "  ${str.orDivider}  ",
                            color = Color(0xFF9CA3AF),
                            fontSize = 12.sp,
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFD1D5DB))
                    }

                    // ── Email ───────────────────────────────────────────────
                    DentTextField(
                        value = email,
                        onValueChange = { email = it; viewModel.clearError() },
                        label = str.emailLabel,
                        keyboardType = KeyboardType.Email,
                        leadingIcon = { Icon(Icons.Outlined.Email, null, tint = Color(0xFF1565C0)) },
                    )

                    // ── Password ────────────────────────────────────────────
                    DentTextField(
                        value = password,
                        onValueChange = { password = it; viewModel.clearError() },
                        label = str.passwordLabel,
                        isPassword = true,
                        imeAction = ImeAction.Done,
                        leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = Color(0xFF1565C0)) },
                    )

                    // ── Error message ───────────────────────────────────────
                    AnimatedVisibility(
                        visible = !state.error.isNullOrBlank(),
                        enter = fadeIn() + expandVertically(),
                        exit  = fadeOut() + shrinkVertically(),
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFFEBEE),
                        ) {
                            Text(
                                state.error ?: "",
                                color = Color(0xFFD32F2F),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            )
                        }
                    }

                    // ── Login button — ALWAYS BLUE ──────────────────────────
                    Button(
                        onClick = {
                            viewModel.clearError()
                            when {
                                email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() ->
                                    viewModel.setError(str.errorEmailInvalid)
                                password.trim().length < 6 ->
                                    viewModel.setError(str.errorPasswordShort)
                                else -> viewModel.login(email.trim(), password.trim())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0),
                            disabledContainerColor = Color(0xFF1565C0),
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                        ),
                        enabled = !state.isLoading,
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text(
                                str.loginBtn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                            )
                        }
                    }

                    // ── Register link ───────────────────────────────────────
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = Color(0xFF6B7280))) {
                                    append(str.noAccount)
                                }
                                withStyle(
                                    SpanStyle(
                                        color = Color(0xFF1565C0),
                                        fontWeight = FontWeight.Bold,
                                    )
                                ) {
                                    append(str.signUpLink)
                                }
                            },
                            fontSize = 14.sp,
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}
