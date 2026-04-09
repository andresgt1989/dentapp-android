# DentApp Android — Project State

## Última actualización: 2026-04-09 09:00

## Commits recientes (Android)
| Hash | Descripción |
|------|-------------|
| `412f5e0` | feat(TASK-07): registro FCM token al login para push notifications |
| `ecc4d00` | feat(TASK-06): auto-trial 14 días al registro + banner en HomePatient |
| `35955b2` | fix: horarios reales booking + rating + notificaciones persistentes + QR |
| `5ad880c` | feat: feature flags + analytics + BUG-04 |
| `981f22f` | fix: 5 bugs críticos de activación |

## Commits recientes (Backend)
| Hash | Descripción |
|------|-------------|
| `48e2940` | feat: PUT /api/users/fcm-token endpoint para push notifications |

## Build
- **Último**: ✅ BUILD SUCCESSFUL (33.9 MB APK, 2026-04-09 08:36)
- **Branch**: main | **Push**: ✅ Al día

---

## Estado de pantallas

| Pantalla | Estado | Notas |
|----------|--------|-------|
| HomePatientScreen | ✅ | Rating real + banner trial Pro |
| AiManagerScreen | ✅ | Chips + analytics |
| BookingScreen | ✅ | Horarios dinámicos por doctor |
| ExpedienteScreen | ✅ | Soporta patientId (doctor → paciente) |
| NotificacionesScreen | ✅ | Persistente en DataStore |
| PrivacidadScreen | ✅ | Conectado a GenerarQRScreen |
| SubscriptionScreen | ✅ | Accesible desde PerfilTab |
| GenerarQRScreen | ✅ | Accesible desde Privacidad |
| OnboardingPatientScreen | ✅ | Llama start-trial al completar |
| XRayCaptureScreen | ⚠️ | Flag OFF — activar con FLAG_XRAY_CAPTURE=true |
| PrescriptionScannerScreen | ⚠️ | Flag OFF — activar con FLAG_OCR_PRESCRIPTIONS=true |
| HomeDoctorScreen | ✅ | Expediente del paciente correcto |
| Resto | ✅ | Sin issues conocidos |

## Arquitectura instalada
- ✅ FeatureFlags: 17 flags por ENV, sin redeploy
- ✅ Analytics: fire-and-forget, DAU/KPIs en PostgreSQL
- ✅ DataStore: preferencias de notificación persistentes
- ✅ FCM Push: token guardado al login, reminderService activo (crons 15min/30min/hourly/daily)
- ✅ Subscriptions: auto-trial 14 días al completar onboarding

## Bugs pendientes
| ID | Prioridad | Descripción |
|----|-----------|-------------|
| BUG-09 | 🟡 | STRIPE_CONNECT_WEBHOOK_SECRET sin configurar |

## Tareas completadas
- ✅ TASK-02 BUG-07: BookingScreen horarios dinámicos por doctor
- ✅ TASK-03 BUG-10/11: Rating real + slot real en DoctorCard
- ✅ TASK-04 BUG-08: NotificacionesScreen persiste en DataStore
- ✅ TASK-05 parcial: GenerarQR accesible desde PrivacidadScreen
- ✅ TASK-06: Auto-trial 14 días al registro + banner HomePatient
- ✅ TASK-07: FCM token al login + reminderService crons + PUT /api/users/fcm-token
- ✅ BUG-06: Chips AI → sendMessage(chip, isChip=true)
- ✅ BUG-01: Bell notificaciones funcional
- ✅ BUG-02: Progreso tratamientos por fechas
- ✅ BUG-03: Loyalty points desde API
- ✅ BUG-05: SubscriptionScreen accesible desde PerfilTab
- ✅ BUG-04: Doctor expediente con patientId real
- ✅ ARCH-01: FeatureFlags system (backend ENV + Android singleton)
- ✅ ARCH-02: Analytics layer (17 eventos KPI, tabla PG, dashboard admin)
- ✅ ARCH-03: roadmap.md + kpis.md creados

## Siguiente paso
No hay tareas pendientes en el backlog. Proponer nuevas iniciativas o esperar input del equipo.
