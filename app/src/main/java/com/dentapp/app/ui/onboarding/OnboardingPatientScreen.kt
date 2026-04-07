package com.dentapp.app.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dentapp.app.ui.components.DentButton
import com.dentapp.app.ui.components.DentTextField
import com.dentapp.app.ui.theme.*

// ─── Step constants ──────────────────────────────────────────────────────────
//  0 = role selection
//  1 = country
//  2 = last dentist visit
//  3 = medical condition
//  4 = final "Todo listo"
// -1 = secretary branch
// Progress bar: steps 0..3 show (step+1)/4

private data class StepState(val step: Int, val forward: Boolean = true)

@Composable
fun OnboardingPatientScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val vmState by viewModel.state.collectAsState()
    var stepState by remember { mutableStateOf(StepState(0)) }

    fun go(next: Int) {
        val current = stepState.step
        val forward = when {
            current == 0  && next == -1 -> true   // role → secretary = forward
            current == -1 && next == 0  -> false  // secretary → role = backward
            else -> next > current
        }
        stepState = StepState(next, forward)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (stepState.step != 4) {
            OnboardingHeader(
                step = stepState.step,
                onBack = {
                    when (stepState.step) {
                        0    -> onBack()
                        -1   -> go(0)
                        else -> go(stepState.step - 1)
                    }
                },
            )
        }

        AnimatedContent(
            targetState = stepState,
            transitionSpec = {
                if (targetState.forward)
                    slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
                else
                    slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            },
            label = "onboarding_step",
            modifier = Modifier.fillMaxSize(),
        ) { state ->
            when (state.step) {
                0 -> RoleStep { role ->
                    viewModel.setRole(role)
                    go(if (role == "secretary") -1 else 1)
                }
                -1 -> SecretaryStep(
                    inviteCode  = vmState.inviteCode,
                    onCodeChange = viewModel::setInviteCode,
                    onContinue  = { viewModel.saveOnboarding(); onSuccess() },
                    isSaving    = vmState.isSaving,
                )
                1 -> CountryStep(selected = vmState.country) { country ->
                    viewModel.setCountry(country)
                    go(2)
                }
                2 -> LastVisitStep(selected = vmState.lastVisit) { visit ->
                    viewModel.setLastVisit(visit)
                    go(3)
                }
                3 -> MedicalStep(selected = vmState.medicalCondition) { condition ->
                    viewModel.setMedicalCondition(condition)
                    viewModel.saveOnboarding()
                    go(4)
                }
                4 -> FinalStep(onExplore = onSuccess)
            }
        }
    }
}

// ─── Header with progress bar ─────────────────────────────────────────────────

@Composable
private fun OnboardingHeader(step: Int, onBack: () -> Unit) {
    val showProgress = step in 0..3
    val progressStep = step + 1   // 1-based

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp),
            ) {
                Text(
                    "← Atrás",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(Modifier.weight(1f))
            if (showProgress) {
                Text(
                    "$progressStep / 4",
                    style = MaterialTheme.typography.labelMedium,
                    color = DentTextSecond,
                )
            }
        }
        if (showProgress) {
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progressStep / 4f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = DentBlueLight,
            )
        }
    }
}

// ─── Step 0: Role selection ───────────────────────────────────────────────────

@Composable
private fun RoleStep(onSelect: (String) -> Unit) {
    QuestionPage(
        question = "¿Cómo quieres usar DentApp?",
        subtitle  = "Elige tu perfil para personalizar tu experiencia",
    ) {
        OptionCard(label = "👤  Paciente",                    selected = false) { onSelect("patient") }
        OptionCard(label = "🗂️  Soy secretaria / asistente", selected = false) { onSelect("secretary") }
    }
}

// ─── Step -1: Secretary branch ────────────────────────────────────────────────

@Composable
private fun SecretaryStep(
    inviteCode:   String,
    onCodeChange: (String) -> Unit,
    onContinue:   () -> Unit,
    isSaving:     Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "Modo secretaria / asistente",
                style = MaterialTheme.typography.headlineMedium,
                color = DentTextPrimary,
            )
            Text(
                "En este modo gestionarás citas y pacientes en nombre del médico. " +
                "Para vincularte, ingresa el código de invitación que te compartió tu doctor.",
                style = MaterialTheme.typography.bodyMedium,
                color = DentTextSecond,
            )
            Spacer(Modifier.height(8.dp))
            DentTextField(
                value         = inviteCode,
                onValueChange = onCodeChange,
                label         = "Código de invitación del doctor",
            )
        }
        Spacer(Modifier.height(16.dp))
        DentButton(
            text      = "Continuar",
            onClick   = onContinue,
            isLoading = isSaving,
            enabled   = inviteCode.isNotBlank(),
        )
        Spacer(Modifier.height(24.dp))
    }
}

// ─── Step 1: Country ──────────────────────────────────────────────────────────

@Composable
private fun CountryStep(selected: String, onSelect: (String) -> Unit) {
    QuestionPage(
        question = "¿En qué país estás?",
        subtitle  = "Adaptamos la app a tu región",
    ) {
        listOf(
            "Colombia"        to "CO",
            "Ecuador"         to "EC",
            "México"          to "MX",
            "Estados Unidos"  to "US",
            "Otro"            to "OT",
        ).forEach { (label, code) ->
            OptionCard(label, selected == code) { onSelect(code) }
        }
    }
}

// ─── Step 2: Last dentist visit ───────────────────────────────────────────────

@Composable
private fun LastVisitStep(selected: String, onSelect: (String) -> Unit) {
    QuestionPage(
        question = "¿Cuándo fue tu última visita al dentista?",
        subtitle  = "Esto nos ayuda a darte mejores recordatorios",
    ) {
        listOf(
            "Hace menos de 6 meses" to "less_6m",
            "6 – 12 meses"          to "6_12m",
            "Más de 1 año"          to "over_1y",
            "No recuerdo"           to "unknown",
        ).forEach { (label, value) ->
            OptionCard(label, selected == value) { onSelect(value) }
        }
    }
}

// ─── Step 3: Medical condition ────────────────────────────────────────────────

@Composable
private fun MedicalStep(selected: String, onSelect: (String) -> Unit) {
    QuestionPage(
        question = "¿Tienes alguna condición médica relevante?",
        subtitle  = "Para que tu dentista esté informado desde el inicio",
    ) {
        listOf(
            "No"                    to "none",
            "Diabetes"              to "diabetes",
            "Hipertensión"          to "hypertension",
            "Tomo anticoagulantes"  to "anticoagulants",
            "Otra"                  to "other",
        ).forEach { (label, value) ->
            OptionCard(label, selected == value) { onSelect(value) }
        }
    }
}

// ─── Step 4: Final screen ─────────────────────────────────────────────────────

@Composable
private fun FinalStep(onExplore: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("🦷", style = MaterialTheme.typography.displayLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Text(
            "Todo listo",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = DentTextPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Tu perfil está configurado. Ahora puedes explorar todas las funciones de DentApp.",
            style = MaterialTheme.typography.bodyMedium,
            color = DentTextSecond,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(48.dp))
        DentButton(text = "Explorar DentApp", onClick = onExplore)
    }
}

// ─── Shared reusable components ───────────────────────────────────────────────

@Composable
private fun QuestionPage(
    question: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            question,
            style = MaterialTheme.typography.headlineMedium,
            color = DentTextPrimary,
        )
        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = DentTextSecond,
        )
        Spacer(Modifier.height(4.dp))
        content()
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun OptionCard(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outlineVariant,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            ),
            color = if (selected) MaterialTheme.colorScheme.primary else DentTextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
        )
    }
}
