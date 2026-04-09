# DentApp — Tasks

## En curso

### TASK-07: Push notifications automáticas (FCM)
- [ ] Backend: reminderService.js — cron 24h antes de cita
- [ ] Backend: daily-summary push via FCM
- [ ] Android: FCM token guardado en backend al login
- **Archivos**: backend/src/services/reminderService.js, AuthViewModel.kt

---

## Pendiente

### TASK-02: Fix BUG-07 — Horarios reales en BookingScreen
- [ ] BookingViewModel: llamar GET /api/doctors/{id} → extraer horarioInicio/horarioFin/diasLaborables
- [ ] Generar slots dinámicos entre horario de apertura y cierre (cada 30min)
- [ ] Filtrar días inhabilitados del doctor
- [ ] Reemplazar lista HORARIOS hardcodeada
- **Archivos**: BookingScreen.kt, BookingViewModel.kt, ApiService.kt (endpoint ya existe)
- **Impacto**: Elimina double-booking. Conversión directa en revenue.

---

## Pendiente

### TASK-03: Fix BUG-10 + BUG-11 — Rating y disponibilidad real en DoctorCard
- [ ] `doctor.ratingPromedio` en lugar de hash
- [ ] `doctor.horarioInicio` en lugar de slots pseudoaleatorios
- **Archivos**: HomePatientScreen.kt → DoctorCard()
- **Impacto**: Confianza en marketplace, conversión booking

### TASK-04: Fix BUG-08 — Persistir preferencias de notificaciones
- [ ] Usar DataStore local (no requiere backend)
- **Archivos**: NotificacionesScreen.kt + nuevo NotificacionesViewModel

### TASK-05: Puntos de entrada para pantallas huérfanas
- [ ] GenerarQR → botón en PrivacidadScreen (ya tiene sección de QR)
- [ ] XRayCapture → activar via FeatureFlag `xray_capture` cuando esté listo
- [ ] PrescriptionScanner → activar via FeatureFlag `ocr_prescriptions`

### TASK-06: Push notifications automáticas (FCM)
- [ ] Cron: enviar daily summary AI (endpoint ya existe: /api/ai/daily-summary)
- [ ] Cron: reminder 24h antes de cita (campo `reminder_24h_sent` ya existe)
- **Archivos**: backend/src/services/reminderService.js
- **Impacto**: +25% retención estimada

### TASK-07: Auto-trial al registro
- [ ] Llamar /api/subscriptions/start-trial al completar onboarding
- [ ] Mostrar banner "Trial Pro activo" en HomePatient
- **Impacto**: +30% conversión a plan pago

---

## Completado

- ✅ TASK-02 BUG-07: BookingScreen horarios dinámicos por doctor [2026-04-09]
- ✅ TASK-03 BUG-10/11: Rating real + slot real en DoctorCard [2026-04-09]
- ✅ TASK-04 BUG-08: NotificacionesScreen persiste en DataStore [2026-04-09]
- ✅ TASK-05 parcial: GenerarQR accesible desde PrivacidadScreen [2026-04-09]
- ✅ TASK-06: Auto-trial 14 días al registro + banner HomePatient [2026-04-09]

- ✅ BUG-06: Chips AI → sendMessage(chip, isChip=true) [2026-04-09]
- ✅ BUG-01: Bell notificaciones funcional [2026-04-09]
- ✅ BUG-02: Progreso tratamientos por fechas [2026-04-09]
- ✅ BUG-03: Loyalty points desde API [2026-04-09]
- ✅ BUG-05: SubscriptionScreen accesible desde PerfilTab [2026-04-09]
- ✅ BUG-04: Doctor expediente con patientId real [2026-04-09]
- ✅ ARCH-01: FeatureFlags system (backend ENV + Android singleton) [2026-04-09]
- ✅ ARCH-02: Analytics layer (17 eventos KPI, tabla PG, dashboard admin) [2026-04-09]
- ✅ ARCH-03: roadmap.md + kpis.md creados [2026-04-09]
