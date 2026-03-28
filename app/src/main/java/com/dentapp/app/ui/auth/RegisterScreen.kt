package com.dentapp.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dentapp.app.ui.components.*
import com.dentapp.app.ui.theme.*

@Composable
fun RegisterScreen(
    onNavigateToPatientOnboarding: () -> Unit,
    onNavigateToDoctorOnboarding: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(DentGray),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(48.dp))

            Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium,
                color = DentTextPrimary)
            Text("¿Cómo quieres registrarte?",
                style = MaterialTheme.typography.bodyMedium,
                color = DentTextSecond,
                modifier = Modifier.padding(top = 6.dp))

            Spacer(Modifier.height(40.dp))

            // Tarjeta Paciente
            RoleCard(
                emoji = "🧑‍⚕️",
                title = "Soy Paciente",
                description = "Agenda citas, recibe recordatorios y paga de forma segura.",
                onClick = onNavigateToPatientOnboarding,
            )

            Spacer(Modifier.height(16.dp))

            // Tarjeta Doctor
            RoleCard(
                emoji = "👨‍⚕️",
                title = "Soy Doctor",
                description = "Gestiona tus citas, pacientes y recibe pagos directamente.",
                onClick = onNavigateToDoctorOnboarding,
            )

            Spacer(Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿Ya tienes cuenta? ", color = DentTextSecond,
                    style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onNavigateToLogin) {
                    Text("Iniciar sesión", color = DentBlue,
                        style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
private fun RoleCard(
    emoji: String,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(DentBlueLight, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(emoji, style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleLarge, color = DentTextPrimary)
                Text(description, style = MaterialTheme.typography.bodyMedium,
                    color = DentTextSecond, modifier = Modifier.padding(top = 4.dp))
            }
            Text("›", style = MaterialTheme.typography.headlineMedium, color = DentBlue)
        }
    }
}
