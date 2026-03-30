package com.dentapp.app.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dentapp.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDoctorScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DentApp", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DentBlue,
                    titleContentColor = White),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Outlined.Logout, "Salir", tint = White)
                    }
                },
            )
        },
        bottomBar = { DoctorBottomBar() },
        containerColor = DentGray,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(Modifier.height(8.dp)) }
            item {
                WelcomeCard(
                    title = "Buenos días, Doctor",
                    subtitle = "Tienes citas pendientes hoy",
                    icon = "👨‍⚕️",
                )
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Hoy", "3", Icons.Outlined.CalendarToday, Modifier.weight(1f))
                    StatCard("Este mes", "24", Icons.Outlined.BarChart, Modifier.weight(1f))
                }
            }
            item {
                Text("Próximas citas", style = MaterialTheme.typography.titleMedium,
                    color = DentTextPrimary)
            }
            items(3) { i ->
                AppointmentCard(
                    patientName = "Paciente ${i + 1}",
                    time = "${9 + i}:00 AM",
                    specialty = "Revisión general",
                    status = if (i == 0) "confirmed" else "pending",
                )
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomePatientScreen(onLogout: () -> Unit, onOpenAiManager: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DentApp", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DentBlue,
                    titleContentColor = White),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Outlined.Logout, "Salir", tint = White)
                    }
                },
            )
        },
        bottomBar = { PatientBottomBar() },
        containerColor = DentGray,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(Modifier.height(8.dp)) }
            item {
                WelcomeCard(
                    title = "¡Hola!",
                    subtitle = "¿Cómo podemos ayudarte hoy?",
                    icon = "🦷",
                )
            }
            // ── Botón AI Manager ─────────────────────────────────────────────
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = androidx.compose.ui.graphics.Color(0xFF1E3A5F),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onOpenAiManager,
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text("🦷", style = MaterialTheme.typography.headlineMedium)
                        Column(Modifier.weight(1f)) {
                            Text("AI Manager Dental", style = MaterialTheme.typography.titleMedium, color = White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            Text("Día 7 de recuperación · Pregúntame lo que necesites", style = MaterialTheme.typography.bodySmall, color = White.copy(alpha = 0.75f))
                        }
                        Icon(Icons.Outlined.ChevronRight, null, tint = White)
                    }
                }
            }
            item {
                DentButton_Home("Agendar nueva cita", Icons.Outlined.Add)
            }
            item {
                Text("Mis próximas citas", style = MaterialTheme.typography.titleMedium,
                    color = DentTextPrimary)
            }
            items(2) { i ->
                AppointmentCard(
                    patientName = "Dr. García",
                    time = "${10 + i}:30 AM",
                    specialty = "Ortodoncia",
                    status = "confirmed",
                )
            }
            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

// ── Componentes internos ─────────────────────────────────────────────────────

@Composable
private fun WelcomeCard(title: String, subtitle: String, icon: String) {
    Surface(shape = RoundedCornerShape(20.dp), color = DentBlue,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleLarge, color = White)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium,
                    color = White.copy(alpha = 0.8f), modifier = Modifier.padding(top = 4.dp))
            }
            Text(icon, style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Composable
private fun StatCard(label: String, value: String,
                     icon: androidx.compose.ui.graphics.vector.ImageVector,
                     modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp, modifier = modifier) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = DentBlue, modifier = Modifier.size(28.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, color = DentBlue)
            Text(label, style = MaterialTheme.typography.bodyMedium, color = DentTextSecond)
        }
    }
}

@Composable
private fun AppointmentCard(patientName: String, time: String,
                             specialty: String, status: String) {
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).background(DentBlueLight, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Person, null, tint = DentBlue)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(patientName, style = MaterialTheme.typography.titleMedium, color = DentTextPrimary)
                Text(specialty, style = MaterialTheme.typography.bodyMedium, color = DentTextSecond)
                Text(time, style = MaterialTheme.typography.bodyMedium, color = DentBlue)
            }
            StatusChip(status)
        }
    }
}

@Composable
private fun StatusChip(status: String) {
    val (label, color) = when (status) {
        "confirmed" -> "Confirmada" to DentGreen
        "pending"   -> "Pendiente"  to MaterialTheme.colorScheme.secondary
        "cancelled" -> "Cancelada"  to ErrorRed
        else        -> status       to DentTextSecond
    }
    Surface(shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
        Text(label, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun DentButton_Home(text: String,
                             icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(shape = RoundedCornerShape(16.dp), color = DentBlue,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Icon(icon, null, tint = White)
            Spacer(Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.titleMedium, color = White)
        }
    }
}

@Composable
private fun DoctorBottomBar() {
    NavigationBar(containerColor = White) {
        NavigationBarItem(selected = true, onClick = {},
            icon = { Icon(Icons.Outlined.Home, null) }, label = { Text("Inicio") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.CalendarMonth, null) }, label = { Text("Citas") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.People, null) }, label = { Text("Pacientes") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
    }
}

@Composable
private fun PatientBottomBar() {
    NavigationBar(containerColor = White) {
        NavigationBarItem(selected = true, onClick = {},
            icon = { Icon(Icons.Outlined.Home, null) }, label = { Text("Inicio") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.CalendarMonth, null) }, label = { Text("Citas") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.Search, null) }, label = { Text("Doctores") })
        NavigationBarItem(selected = false, onClick = {},
            icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
    }
}
