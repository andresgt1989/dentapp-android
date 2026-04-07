package com.dentapp.app.ui.home

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dentapp.app.data.model.*
import com.dentapp.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDoctorScreen(
    onLogout: () -> Unit,
    onVerExpedientePaciente: (String) -> Unit = {},
    viewModel: HomeDoctorViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Mostrar errores como snackbar
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }
    LaunchedEffect(state.payoutEnviado) {
        state.payoutEnviado?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissPayoutMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("DentApp Doctor", style = MaterialTheme.typography.titleMedium,
                            color = White, fontWeight = FontWeight.Bold)
                        Text("Dr. ${state.doctorName}", style = MaterialTheme.typography.bodySmall,
                            color = White.copy(alpha = 0.8f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DentBlue),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Outlined.Logout, "Salir", tint = White)
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(containerColor = White) {
                val tabs = listOf(
                    Triple(Icons.Outlined.Today, "Hoy", null),
                    Triple(Icons.Outlined.People, "Pacientes", null),
                    Triple(Icons.Outlined.CalendarMonth, "Agenda", null),
                    Triple(Icons.Outlined.AttachMoney, "Ingresos", null),
                    Triple(Icons.Outlined.Notifications, "Alertas",
                        if (state.alertasNoVistas > 0) state.alertasNoVistas else null),
                )
                tabs.forEachIndexed { idx, (icon, label, badge) ->
                    NavigationBarItem(
                        selected = state.selectedTab == idx,
                        onClick = { viewModel.selectTab(idx) },
                        icon = {
                            BadgedBox(badge = {
                                if (badge != null) {
                                    Badge { Text(badge.toString()) }
                                }
                            }) {
                                Icon(icon, label)
                            }
                        },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DentGray,
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (state.selectedTab) {
                0 -> TabHoy(state, onRefresh = { viewModel.loadCitasHoy() })
                1 -> TabPacientes(state, viewModel, onVerExpediente = onVerExpedientePaciente)
                2 -> TabAgenda(state, viewModel)
                3 -> TabIngresos(
                    state = state,
                    onSolicitarPago = { viewModel.solicitarPago() },
                    onStripeConnect = { _ ->
                        viewModel.iniciarStripeConnect { url ->
                            val customTab = CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .build()
                            customTab.launchUrl(context, Uri.parse(url))
                        }
                    },
                )
                4 -> TabAlertas(state, onVerPaciente = onVerExpedientePaciente)
            }
        }
    }
}

// ── TAB 1: HOY ───────────────────────────────────────────────────────────────

@Composable
private fun TabHoy(state: HomeDoctorUiState, onRefresh: () -> Unit) {
    val hoy = LocalDate.now()
    val fechaStr = hoy.format(DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", Locale("es")))
        .replaceFirstChar { it.uppercase() }

    if (state.isLoadingHoy) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = DentBlue)
        }
        return
    }

    // Alerta si hay cita en menos de 30 minutos
    val citaInminente = state.citasHoy.firstOrNull { cita ->
        if (cita.status !in listOf("pending", "confirmed")) return@firstOrNull false
        try {
            val hora = cita.scheduledAt.substringAfter("T").take(5)
            val citaTime = java.time.LocalTime.parse(hora)
            val diff = java.time.Duration.between(java.time.LocalTime.now(), citaTime).toMinutes()
            diff in 0..30
        } catch (_: Exception) { false }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Spacer(Modifier.height(8.dp)) }

        // Banner de fecha y bienvenida
        item {
            Surface(shape = RoundedCornerShape(20.dp), color = DentBlue,
                modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("¡Hola, Doctor!", style = MaterialTheme.typography.titleLarge,
                            color = White, fontWeight = FontWeight.Bold)
                        Text(fechaStr, style = MaterialTheme.typography.bodyMedium,
                            color = White.copy(alpha = 0.8f))
                    }
                    Text("👨‍⚕️", style = MaterialTheme.typography.headlineLarge)
                }
            }
        }

        // Alerta de cita inminente
        if (citaInminente != null) {
            item {
                Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFFFF3CD),
                    modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Outlined.AccessTime, null, tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(24.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Cita próxima", fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium, color = Color(0xFF92400E))
                            Text("${citaInminente.patientName ?: "Paciente"} — en menos de 30 minutos",
                                style = MaterialTheme.typography.bodySmall, color = Color(0xFF92400E))
                        }
                    }
                }
            }
        }

        // Stats del día
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCardDoctor(
                    label = "Citas hoy",
                    value = state.citasHoy.size.toString(),
                    icon = Icons.Outlined.CalendarToday,
                    modifier = Modifier.weight(1f),
                )
                StatCardDoctor(
                    label = "Ingresos hoy",
                    value = "$${"%.0f".format(state.ingresosHoy)}",
                    icon = Icons.Outlined.AttachMoney,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // Lista de citas
        if (state.citasHoy.isEmpty()) {
            item {
                EmptyState(
                    icon = "📅",
                    titulo = "Sin citas hoy",
                    subtitulo = "No tienes citas agendadas para hoy",
                )
            }
        } else {
            item {
                Text("Citas de hoy", style = MaterialTheme.typography.titleMedium,
                    color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
            }
            items(state.citasHoy) { cita ->
                CitaDoctorCard(cita = cita)
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun CitaDoctorCard(cita: DoctorCitaDto) {
    val horaStr = try {
        cita.scheduledAt.substringAfter("T").take(5)
    } catch (_: Exception) { "--:--" }

    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).background(DentBlueLight, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Person, null, tint = DentBlue)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(cita.patientName ?: "Paciente",
                    style = MaterialTheme.typography.titleSmall, color = DentTextPrimary,
                    fontWeight = FontWeight.SemiBold)
                Text(cita.tipo ?: "Consulta",
                    style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Schedule, null, tint = DentBlue,
                        modifier = Modifier.size(14.dp))
                    Text(horaStr, style = MaterialTheme.typography.bodySmall, color = DentBlue)
                    Text("· ${cita.durationMinutes} min",
                        style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                }
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusChipDoctor(cita.status)
                if (cita.status == "confirmed") {
                    Surface(shape = RoundedCornerShape(8.dp), color = DentBlue) {
                        Row(Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Outlined.VideoCall, null, tint = White,
                                modifier = Modifier.size(14.dp))
                            Text("Iniciar", style = MaterialTheme.typography.labelSmall, color = White)
                        }
                    }
                }
            }
        }
    }
}

// ── TAB 2: PACIENTES ─────────────────────────────────────────────────────────

@Composable
private fun TabPacientes(
    state: HomeDoctorUiState,
    viewModel: HomeDoctorViewModel,
    onVerExpediente: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        // Buscador
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.updateSearch(it) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = { Text("Buscar paciente...") },
            leadingIcon = { Icon(Icons.Outlined.Search, null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
        )

        if (state.isLoadingPacientes) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DentBlue)
            }
            return@Column
        }

        val filtrados = viewModel.pacientesFiltrados()

        if (filtrados.isEmpty()) {
            EmptyState(
                icon = "👥",
                titulo = if (state.searchQuery.isBlank()) "Sin pacientes" else "Sin resultados",
                subtitulo = if (state.searchQuery.isBlank())
                    "Aquí verás a todos tus pacientes atendidos"
                else "No encontramos pacientes con ese nombre",
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item { Spacer(Modifier.height(4.dp)) }
                item {
                    Text("${filtrados.size} pacientes",
                        style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
                }
                items(filtrados) { paciente ->
                    PacienteCard(paciente = paciente, onClick = { onVerExpediente(paciente.id) })
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun PacienteCard(paciente: DoctorPacienteDto, onClick: () -> Unit) {
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).background(DentBlueLight, CircleShape),
                contentAlignment = Alignment.Center) {
                Text(
                    paciente.fullName.firstOrNull()?.uppercase() ?: "P",
                    style = MaterialTheme.typography.titleMedium,
                    color = DentBlue, fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(paciente.fullName, style = MaterialTheme.typography.titleSmall,
                    color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
                Text("Última visita: ${paciente.ultimaVisita ?: "—"}",
                    style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${paciente.totalCitas}", style = MaterialTheme.typography.titleMedium,
                    color = DentBlue, fontWeight = FontWeight.Bold)
                Text("citas", style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Outlined.ChevronRight, null, tint = DentTextSecond)
        }
    }
}

// ── TAB 3: AGENDA ────────────────────────────────────────────────────────────

@Composable
private fun TabAgenda(state: HomeDoctorUiState, viewModel: HomeDoctorViewModel) {
    val diasSemana = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
    val horas = (8..18).map { h -> "%02d:00".format(h) }
    val today = LocalDate.now()

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(Modifier.height(4.dp))

        // Semana actual
        Text("Semana actual", style = MaterialTheme.typography.titleMedium,
            color = DentTextPrimary, fontWeight = FontWeight.SemiBold)

        // Grid semanal 7 días
        Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp) {
            Column(Modifier.padding(12.dp)) {
                // Cabecera días
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(40.dp))
                    diasSemana.forEachIndexed { idx, dia ->
                        val diaNum = idx + 1  // 1=Lunes
                        val esLaborable = state.diasLaborables.contains(diaNum)
                        val fechaDia = today.with(java.time.DayOfWeek.of(diaNum))
                        val esHoy = fechaDia == today
                        Box(
                            Modifier.weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(dia, style = MaterialTheme.typography.labelSmall,
                                    color = if (esHoy) DentBlue else if (esLaborable) DentTextPrimary else DentTextSecond)
                                Text(
                                    fechaDia.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (esHoy) White else DentTextSecond,
                                    modifier = if (esHoy) Modifier.background(DentBlue, CircleShape)
                                        .padding(horizontal = 4.dp, vertical = 2.dp) else Modifier,
                                )
                            }
                        }
                    }
                }

                Divider(Modifier.padding(vertical = 8.dp))

                // Filas por hora (8am-11am resumido)
                horas.take(4).forEach { hora ->
                    Row(Modifier.fillMaxWidth().height(32.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(hora, style = MaterialTheme.typography.labelSmall,
                            color = DentTextSecond, modifier = Modifier.width(40.dp))
                        diasSemana.indices.forEach { idx ->
                            val diaNum = idx + 1
                            val esLaborable = state.diasLaborables.contains(diaNum)
                            val citaEnSlot = state.citasHoy.firstOrNull { cita ->
                                try {
                                    val citaHora = cita.scheduledAt.substringAfter("T").take(5)
                                    citaHora.startsWith(hora.take(2)) &&
                                        LocalDate.parse(cita.scheduledAt.take(10)) ==
                                        today.with(java.time.DayOfWeek.of(diaNum))
                                } catch (_: Exception) { false }
                            }
                            Box(
                                Modifier.weight(1f).fillMaxHeight().padding(1.dp)
                                    .background(
                                        when {
                                            citaEnSlot != null -> DentBlue.copy(alpha = 0.8f)
                                            !esLaborable -> Color(0xFFF1F5F9)
                                            else -> Color.Transparent
                                        },
                                        RoundedCornerShape(4.dp),
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (citaEnSlot != null) {
                                    Text(citaEnSlot.patientName?.take(6) ?: "Cita",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = White, maxLines = 1)
                                }
                            }
                        }
                    }
                }

                Text("Ver agenda completa próximamente",
                    style = MaterialTheme.typography.bodySmall, color = DentTextSecond,
                    modifier = Modifier.padding(top = 8.dp))
            }
        }

        // Configurar horario
        Text("Mi horario de atención", style = MaterialTheme.typography.titleMedium,
            color = DentTextPrimary, fontWeight = FontWeight.SemiBold)

        Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Días laborables
                Text("Días de atención", style = MaterialTheme.typography.labelMedium,
                    color = DentTextSecond)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(1 to "L", 2 to "M", 3 to "X", 4 to "J", 5 to "V", 6 to "S", 7 to "D")
                        .forEach { (num, letra) ->
                            val activo = state.diasLaborables.contains(num)
                            FilterChip(
                                selected = activo,
                                onClick = { viewModel.toggleDiaLaborable(num) },
                                label = { Text(letra) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DentBlue,
                                    selectedLabelColor = White,
                                ),
                            )
                        }
                }

                // Horario inicio-fin
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = state.horarioInicio,
                        onValueChange = { viewModel.updateHorarioInicio(it) },
                        label = { Text("Inicio") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    )
                    OutlinedTextField(
                        value = state.horarioFin,
                        onValueChange = { viewModel.updateHorarioFin(it) },
                        label = { Text("Fin") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Outlined.Schedule, null) },
                    )
                }

                Button(
                    onClick = { viewModel.guardarHorario() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSavingHorario,
                    colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (state.isSavingHorario) {
                        CircularProgressIndicator(Modifier.size(20.dp), color = White, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Outlined.Save, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(if (state.horarioGuardado) "✓ Guardado" else "Guardar horario")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ── TAB 4: INGRESOS ──────────────────────────────────────────────────────────

@Composable
private fun TabIngresos(
    state: HomeDoctorUiState,
    onSolicitarPago: () -> Unit,
    onStripeConnect: (String) -> Unit,
) {
    if (state.isLoadingIngresos) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = DentBlue)
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item { Spacer(Modifier.height(8.dp)) }

        // Banner Stripe Connect si cuenta no está activa
        if (state.stripeStatus != "active") {
            item {
                val bgColor = if (state.stripeStatus == "pending_verification")
                    Color(0xFFFFF3CD) else Color(0xFFEFF6FF)
                val textColor = if (state.stripeStatus == "pending_verification")
                    Color(0xFF92400E) else DentBlue

                Surface(shape = RoundedCornerShape(16.dp), color = bgColor,
                    modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(
                                if (state.stripeStatus == "pending_verification")
                                    Icons.Outlined.HourglassEmpty else Icons.Outlined.CreditCard,
                                null, tint = textColor, modifier = Modifier.size(24.dp),
                            )
                            Text(
                                if (state.stripeStatus == "pending_verification")
                                    "Verificación en proceso" else "Conecta tu cuenta de pagos",
                                style = MaterialTheme.typography.titleSmall,
                                color = textColor, fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Text(
                            if (state.stripeStatus == "pending_verification")
                                "Stripe está revisando tu información. Recibirás una notificación cuando esté lista."
                            else "Activa tu cuenta para recibir pagos directamente de tus pacientes.",
                            style = MaterialTheme.typography.bodySmall, color = textColor,
                        )
                        if (state.stripeStatus != "pending_verification") {
                            Button(
                                onClick = { onStripeConnect("") },
                                enabled = !state.isLoadingStripe,
                                colors = ButtonDefaults.buttonColors(containerColor = DentBlue),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                if (state.isLoadingStripe) {
                                    CircularProgressIndicator(Modifier.size(18.dp), color = White, strokeWidth = 2.dp)
                                } else {
                                    Icon(Icons.Outlined.OpenInBrowser, null, Modifier.size(18.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("Conectar cuenta Stripe")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Cards de totales
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCardDoctor(
                    label = "Este mes",
                    value = "$${"%.2f".format(state.totalMes)}",
                    icon = Icons.Outlined.BarChart,
                    modifier = Modifier.weight(1f),
                )
                StatCardDoctor(
                    label = "Pendiente",
                    value = "$${"%.2f".format(state.totalPendiente)}",
                    icon = Icons.Outlined.AccountBalanceWallet,
                    modifier = Modifier.weight(1f),
                    highlightColor = if (state.totalPendiente > 0) DentGreen else null,
                )
            }
        }

        // Botón solicitar pago
        if (state.totalPendiente > 0) {
            item {
                Button(
                    onClick = onSolicitarPago,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSolicitandoPago,
                    colors = ButtonDefaults.buttonColors(containerColor = DentGreen),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (state.isSolicitandoPago) {
                        CircularProgressIndicator(Modifier.size(20.dp), color = White, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Outlined.Payments, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Solicitar pago — $${"%.2f".format(state.totalPendiente)}",
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Historial de pagos
        item {
            Text("Historial de pagos", style = MaterialTheme.typography.titleMedium,
                color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
        }

        if (state.payouts.isEmpty()) {
            item {
                EmptyState(icon = "💰", titulo = "Sin movimientos",
                    subtitulo = "No hay movimientos registrados este mes")
            }
        } else {
            items(state.payouts) { payout ->
                PayoutCard(payout)
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun PayoutCard(payout: DoctorPayoutDto) {
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).background(DentGreen.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.AttachMoney, null, tint = DentGreen)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(payout.patientName ?: "Consulta",
                    style = MaterialTheme.typography.titleSmall, color = DentTextPrimary)
                Text(payout.fecha ?: payout.createdAt.take(10),
                    style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("$${"%.2f".format(payout.amount)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = DentTextPrimary, fontWeight = FontWeight.Bold)
                PayoutStatusChip(payout.status)
            }
        }
    }
}

@Composable
private fun PayoutStatusChip(status: String) {
    val (label, color) = when (status) {
        "sent", "confirmed" -> "Enviado" to DentGreen
        "processing"        -> "En proceso" to Color(0xFFF59E0B)
        else                -> "Pendiente" to DentTextSecond
    }
    Surface(shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
        Text(label, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall, color = color)
    }
}

// ── TAB 5: ALERTAS DE PACIENTES ──────────────────────────────────────────────

@Composable
private fun TabAlertas(state: HomeDoctorUiState, onVerPaciente: (String) -> Unit) {
    if (state.isLoadingAlerts) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = DentBlue)
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item { Spacer(Modifier.height(8.dp)) }

        if (state.patientAlerts.isEmpty()) {
            item {
                Surface(shape = RoundedCornerShape(16.dp), color = DentBlue.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("🦷", style = MaterialTheme.typography.headlineLarge)
                        Text("Sin alertas activas",
                            style = MaterialTheme.typography.titleMedium, color = DentTextPrimary,
                            fontWeight = FontWeight.SemiBold)
                        Text("Cuando tus pacientes usen DentApp, verás sus alertas clínicas aquí.",
                            style = MaterialTheme.typography.bodySmall, color = DentTextSecond,
                            textAlign = TextAlign.Center)
                        Spacer(Modifier.height(4.dp))
                        Surface(shape = RoundedCornerShape(8.dp), color = DentBlue.copy(alpha = 0.1f)) {
                            Text("Comparte DentApp con tus pacientes para recibir sus alertas",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall, color = DentBlue,
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        } else {
            item {
                Text("${state.patientAlerts.size} alertas de tus pacientes",
                    style = MaterialTheme.typography.titleMedium,
                    color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
            }
            items(state.patientAlerts) { alerta ->
                AlertaPacienteCard(alerta = alerta, onClick = { onVerPaciente(alerta.patientId) })
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun AlertaPacienteCard(alerta: PatientAlertDto, onClick: () -> Unit) {
    val prioridadColor = when (alerta.prioridad) {
        "CRITICA" -> ErrorRed
        "ALTA"    -> Color(0xFFF59E0B)
        "MEDIA"   -> DentBlue
        else      -> DentTextSecond
    }

    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(Modifier.size(44.dp).background(prioridadColor.copy(alpha = 0.1f),
                RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(
                    if (alerta.prioridad == "CRITICA") Icons.Outlined.Warning else Icons.Outlined.Info,
                    null, tint = prioridadColor,
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(alerta.patientName, style = MaterialTheme.typography.titleSmall,
                        color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
                    Surface(shape = RoundedCornerShape(50), color = prioridadColor.copy(alpha = 0.12f)) {
                        Text(alerta.prioridad, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall, color = prioridadColor)
                    }
                }
                Text(alerta.mensaje, style = MaterialTheme.typography.bodySmall, color = DentTextSecond,
                    maxLines = 3, overflow = TextOverflow.Ellipsis)
                Text("Hace ${alerta.diasPendiente} días",
                    style = MaterialTheme.typography.labelSmall, color = DentTextSecond)
            }
            Icon(Icons.Outlined.ChevronRight, null, tint = DentTextSecond)
        }
    }
}

// ── Componentes reutilizables ─────────────────────────────────────────────────

@Composable
private fun StatCardDoctor(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    highlightColor: Color? = null,
) {
    val color = highlightColor ?: DentBlue
    Surface(shape = RoundedCornerShape(16.dp), shadowElevation = 1.dp, modifier = modifier) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = color, modifier = Modifier.size(28.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, color = color,
                fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.bodySmall, color = DentTextSecond)
        }
    }
}

@Composable
private fun StatusChipDoctor(status: String) {
    val (label, color) = when (status) {
        "confirmed"  -> "Confirmada" to DentGreen
        "pending"    -> "Pendiente"  to Color(0xFFF59E0B)
        "completed"  -> "Completada" to DentTextSecond
        "cancelled"  -> "Cancelada"  to ErrorRed
        "in_progress"-> "En curso"   to DentBlue
        else         -> status       to DentTextSecond
    }
    Surface(shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
        Text(label, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun EmptyState(icon: String, titulo: String, subtitulo: String) {
    Box(Modifier.fillMaxWidth().padding(vertical = 40.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(icon, style = MaterialTheme.typography.headlineLarge)
            Text(titulo, style = MaterialTheme.typography.titleMedium,
                color = DentTextPrimary, fontWeight = FontWeight.SemiBold)
            Text(subtitulo, style = MaterialTheme.typography.bodySmall,
                color = DentTextSecond, textAlign = TextAlign.Center)
        }
    }
}
