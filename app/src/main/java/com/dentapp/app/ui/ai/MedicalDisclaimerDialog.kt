package com.dentapp.app.ui.ai

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MedicalDisclaimerDialog(onAccept: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},   // not dismissible — user must accept
        icon = {
            Text("\u2139\uFE0F", style = MaterialTheme.typography.headlineMedium)
        },
        title = {
            Text("Aviso importante", fontWeight = FontWeight.Bold)
        },
        text = {
            Text(
                "El AI Dental de DentApp es un asistente de orientación. La información proporcionada " +
                "NO reemplaza el diagnóstico, tratamiento o consejo de un profesional dental calificado.\n\n" +
                "Ante cualquier urgencia o síntoma grave, consulta a tu dentista o ve a urgencias."
            )
        },
        confirmButton = {
            Button(onClick = onAccept) { Text("Entendido") }
        },
    )
}
