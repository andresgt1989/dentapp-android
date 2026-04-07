package com.dentapp.app.data.api;

import com.dentapp.app.data.model.*;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00aa\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001cH\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u001e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020 H\u00a7@\u00a2\u0006\u0002\u0010!J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u00032\b\b\u0001\u0010\u0005\u001a\u00020$H\u00a7@\u00a2\u0006\u0002\u0010%J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\b\b\u0001\u0010\u0005\u001a\u00020(H\u00a7@\u00a2\u0006\u0002\u0010)J\u001e\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00032\b\b\u0001\u0010\u0005\u001a\u00020,H\u00a7@\u00a2\u0006\u0002\u0010-J\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00032\b\b\u0001\u0010\u0005\u001a\u000202H\u00a7@\u00a2\u0006\u0002\u00103J\u0014\u00104\u001a\b\u0012\u0004\u0012\u0002050\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u00106\u001a\b\u0012\u0004\u0012\u0002070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u00108\u001a\b\u0012\u0004\u0012\u0002090\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020=0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015J4\u0010B\u001a\b\u0012\u0004\u0012\u00020C0\u00032\b\b\u0003\u0010D\u001a\u00020E2\b\b\u0003\u0010F\u001a\u00020E2\n\b\u0003\u0010G\u001a\u0004\u0018\u00010\u0014H\u00a7@\u00a2\u0006\u0002\u0010HJ\u0014\u0010I\u001a\b\u0012\u0004\u0012\u00020J0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010M\u001a\b\u0012\u0004\u0012\u00020N0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020P0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010S\u001a\b\u0012\u0004\u0012\u00020T0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010U\u001a\b\u0012\u0004\u0012\u00020V0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010W\u001a\b\u0012\u0004\u0012\u00020A0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010Z\u001a\b\u0012\u0004\u0012\u00020[0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\\\u001a\b\u0012\u0004\u0012\u00020?0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010]\u001a\b\u0012\u0004\u0012\u00020^0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010_\u001a\b\u0012\u0004\u0012\u00020`0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010a\u001a\b\u0012\u0004\u0012\u00020b0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010c\u001a\b\u0012\u0004\u0012\u00020d0\u00032\b\b\u0001\u0010\u0005\u001a\u00020eH\u00a7@\u00a2\u0006\u0002\u0010fJ\u001e\u0010g\u001a\b\u0012\u0004\u0012\u00020d0\u00032\b\b\u0001\u0010\u0005\u001a\u00020hH\u00a7@\u00a2\u0006\u0002\u0010iJ\u001e\u0010j\u001a\b\u0012\u0004\u0012\u00020d0\u00032\b\b\u0001\u0010\u0005\u001a\u00020kH\u00a7@\u00a2\u0006\u0002\u0010lJ\u0014\u0010m\u001a\b\u0012\u0004\u0012\u00020n0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010o\u001a\b\u0012\u0004\u0012\u00020p0\u00032\b\b\u0001\u0010\u0005\u001a\u00020qH\u00a7@\u00a2\u0006\u0002\u0010rJ\u001e\u0010s\u001a\b\u0012\u0004\u0012\u00020t0\u00032\b\b\u0001\u0010\u0005\u001a\u00020uH\u00a7@\u00a2\u0006\u0002\u0010vJ\u0014\u0010w\u001a\b\u0012\u0004\u0012\u00020x0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010y\u001a\b\u0012\u0004\u0012\u00020z0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010{\u001a\b\u0012\u0004\u0012\u00020|0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010}\u001a\b\u0012\u0004\u0012\u00020~0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ!\u0010\u007f\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010\u00032\t\b\u0001\u0010\u0005\u001a\u00030\u0081\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u0082\u0001J\"\u0010\u0083\u0001\u001a\t\u0012\u0005\u0012\u00030\u0084\u00010\u00032\t\b\u0001\u0010\u0005\u001a\u00030\u0085\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u0086\u0001J!\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u00020A0\u00032\t\b\u0001\u0010\u0005\u001a\u00030\u0088\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u0089\u0001J.\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020A0\u00032\u0016\b\u0001\u0010\u008b\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u008c\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u008d\u0001J.\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020p0\u00032\u0016\b\u0001\u0010\u008b\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u008c\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u008d\u0001J6\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u00020J0\u00032\u001e\b\u0001\u0010\u008b\u0001\u001a\u0017\u0012\u0004\u0012\u00020\u0014\u0012\f\u0012\n\u0018\u00010\u0001\u00a2\u0006\u0003\b\u0090\u00010\u008c\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u008d\u0001J\"\u0010\u0091\u0001\u001a\t\u0012\u0005\u0012\u00030\u0092\u00010\u00032\t\b\u0001\u0010\u0005\u001a\u00030\u0093\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u0094\u0001J.\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020Y0\u00032\u0016\b\u0001\u0010\u008b\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u008c\u0001H\u00a7@\u00a2\u0006\u0003\u0010\u008d\u0001\u00a8\u0006\u0096\u0001"}, d2 = {"Lcom/dentapp/app/data/api/ApiService;", "", "aiChat", "Lretrofit2/Response;", "Lcom/dentapp/app/data/model/AiChatResponse;", "request", "Lcom/dentapp/app/data/model/AiChatRequest;", "(Lcom/dentapp/app/data/model/AiChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aiContext", "Lcom/dentapp/app/data/model/AiContextResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aiHistory", "Lcom/dentapp/app/data/model/AiHistoryResponse;", "analyzePhoto", "Lcom/dentapp/app/data/model/AnalyzePhotoResponse;", "Lcom/dentapp/app/data/model/AnalyzePhotoRequest;", "(Lcom/dentapp/app/data/model/AnalyzePhotoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelAppointment", "Lcom/dentapp/app/data/model/AppointmentDto;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelPatientAppointment", "confirmMedication", "Lcom/dentapp/app/data/model/ConfirmMedicationResponse;", "Lcom/dentapp/app/data/model/ConfirmMedicationRequest;", "(Lcom/dentapp/app/data/model/ConfirmMedicationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createAppointment", "Lcom/dentapp/app/data/model/CreateAppointmentRequest;", "(Lcom/dentapp/app/data/model/CreateAppointmentRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPaymentIntent", "Lcom/dentapp/app/data/model/PaymentIntentResponse;", "Lcom/dentapp/app/data/model/CreatePaymentIntentRequest;", "(Lcom/dentapp/app/data/model/CreatePaymentIntentRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPrescription", "Lcom/dentapp/app/data/model/ManualPrescriptionResponse;", "Lcom/dentapp/app/data/model/ManualPrescriptionRequest;", "(Lcom/dentapp/app/data/model/ManualPrescriptionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createShareToken", "Lcom/dentapp/app/data/model/ShareTokenResponse;", "Lcom/dentapp/app/data/model/ShareTokenRequest;", "(Lcom/dentapp/app/data/model/ShareTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createVideoConsulta", "Lcom/dentapp/app/data/model/VideoConsultaResponse;", "Lcom/dentapp/app/data/model/CreateVideoConsultaRequest;", "(Lcom/dentapp/app/data/model/CreateVideoConsultaRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAccount", "", "escanearReceta", "Lcom/dentapp/app/data/model/RecetaOcrResponse;", "Lcom/dentapp/app/data/model/RecetaOcrRequest;", "(Lcom/dentapp/app/data/model/RecetaOcrRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAdminStats", "Lcom/dentapp/app/data/model/AdminStatsResponse;", "getCitasHoy", "Lcom/dentapp/app/data/model/CitasHoyResponse;", "getClinicalAlerts", "Lcom/dentapp/app/data/model/ClinicalAlertsResponse;", "getConsultationTypes", "Lcom/dentapp/app/data/model/ConsultationTypesResponse;", "getDentalRecords", "Lcom/dentapp/app/data/model/DentalRecordsResponse;", "getDoctorAppointments", "Lcom/dentapp/app/data/model/AppointmentsResponse;", "getDoctorById", "Lcom/dentapp/app/data/model/DoctorResponse;", "getDoctors", "Lcom/dentapp/app/data/model/DoctorsResponse;", "limit", "", "offset", "specialty", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHealthProfile", "Lcom/dentapp/app/data/model/HealthProfileResponse;", "getIngresos", "Lcom/dentapp/app/data/model/IngresosResponse;", "getLoyaltyBalance", "Lcom/dentapp/app/data/model/LoyaltyBalanceResponse;", "getLoyaltyHistory", "Lcom/dentapp/app/data/model/LoyaltyHistoryResponse;", "getMisPacientes", "Lcom/dentapp/app/data/model/MisPacientesResponse;", "getMisRecetas", "Lcom/dentapp/app/data/model/RecetasResponse;", "getMisShareTokens", "Lcom/dentapp/app/data/model/ShareTokensResponse;", "getMyDoctorProfile", "getMyPatientProfile", "Lcom/dentapp/app/data/model/PatientResponse;", "getPatientAlerts", "Lcom/dentapp/app/data/model/PatientAlertsResponse;", "getPatientAppointments", "getPlans", "Lcom/dentapp/app/data/model/PlansResponse;", "getSubscriptionStatus", "Lcom/dentapp/app/data/model/SubscriptionStatusResponse;", "getTratamientos", "Lcom/dentapp/app/ui/tratamiento/TratamientosResponse;", "googleAuth", "Lcom/dentapp/app/data/model/AuthResponse;", "Lcom/dentapp/app/data/model/GoogleAuthRequest;", "(Lcom/dentapp/app/data/model/GoogleAuthRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/dentapp/app/data/model/LoginRequest;", "(Lcom/dentapp/app/data/model/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/dentapp/app/data/model/RegisterRequest;", "(Lcom/dentapp/app/data/model/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestPayout", "Lcom/dentapp/app/data/model/RequestPayoutResponse;", "saveOnboarding", "Lcom/dentapp/app/data/model/MessageResponse;", "Lcom/dentapp/app/data/model/OnboardingRequest;", "(Lcom/dentapp/app/data/model/OnboardingRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendUrgencia", "Lcom/dentapp/app/data/model/UrgenciaResponse;", "Lcom/dentapp/app/data/model/UrgenciaRequest;", "(Lcom/dentapp/app/data/model/UrgenciaRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startConversation", "Lcom/dentapp/app/data/model/AiStartResponse;", "startTrial", "Lcom/dentapp/app/data/model/StartTrialResponse;", "stripeConnectCreate", "Lcom/dentapp/app/data/model/StripeConnectCreateResponse;", "stripeConnectStatus", "Lcom/dentapp/app/data/model/StripeConnectStatusResponse;", "subirRadiografia", "Lcom/dentapp/app/data/model/RadiografiaResponse;", "Lcom/dentapp/app/data/model/RadiografiaUploadRequest;", "(Lcom/dentapp/app/data/model/RadiografiaUploadRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitAiFeedback", "Lcom/dentapp/app/data/model/AiFeedbackResponse;", "Lcom/dentapp/app/data/model/AiFeedbackRequest;", "(Lcom/dentapp/app/data/model/AiFeedbackRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBankInfo", "Lcom/dentapp/app/data/model/UpdateBankInfoRequest;", "(Lcom/dentapp/app/data/model/UpdateBankInfoRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDoctorProfile", "body", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFcmToken", "updateHealthProfile", "Lkotlin/jvm/JvmSuppressWildcards;", "updateHorario", "Lcom/dentapp/app/data/model/HorarioResponse;", "Lcom/dentapp/app/data/model/UpdateHorarioRequest;", "(Lcom/dentapp/app/data/model/UpdateHorarioRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePatientProfile", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "api/auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.LoginRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/auth/google")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object googleAuth(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.GoogleAuthRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AuthResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/auth/fcm-token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateFcmToken(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.MessageResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDoctors(@retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @retrofit2.http.Query(value = "specialty")
    @org.jetbrains.annotations.Nullable()
    java.lang.String specialty, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DoctorsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDoctorById(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DoctorResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyDoctorProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DoctorResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/doctors/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateDoctorProfile(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DoctorResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/doctors/me/bank-info")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateBankInfo(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.UpdateBankInfoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DoctorResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/me/appointments")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDoctorAppointments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AppointmentsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/hoy")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCitasHoy(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.CitasHoyResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/mis-pacientes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMisPacientes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.MisPacientesResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/patient-alerts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPatientAlerts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.PatientAlertsResponse>> $completion);
    
    @retrofit2.http.PUT(value = "api/doctors/horario")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateHorario(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.UpdateHorarioRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.HorarioResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/doctors/ingresos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getIngresos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.IngresosResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/payments/connect/request-payout")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object requestPayout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.RequestPayoutResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/payments/connect/create")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stripeConnectCreate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.StripeConnectCreateResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/payments/connect/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stripeConnectStatus(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.StripeConnectStatusResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/patients/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyPatientProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.PatientResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/patients/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePatientProfile(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.PatientResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/patients/me/appointments")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPatientAppointments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AppointmentsResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/appointments")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createAppointment(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.CreateAppointmentRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AppointmentDto>> $completion);
    
    @retrofit2.http.PATCH(value = "api/appointments/{id}/cancel")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cancelAppointment(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AppointmentDto>> $completion);
    
    @retrofit2.http.POST(value = "api/payments/create-intent")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createPaymentIntent(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.CreatePaymentIntentRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.PaymentIntentResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/chat")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object aiChat(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.AiChatRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AiChatResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/ai/context")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object aiContext(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AiContextResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/ai/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object aiHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AiHistoryResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/start")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startConversation(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AiStartResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/subscriptions/plans")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlans(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.PlansResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/subscriptions/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSubscriptionStatus(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.SubscriptionStatusResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/admin/stats")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAdminStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AdminStatsResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "api/users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAccount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "api/consultas/tipos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getConsultationTypes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ConsultationTypesResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/consultas/video")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createVideoConsulta(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.CreateVideoConsultaRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.VideoConsultaResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/consultas/urgencia")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendUrgencia(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.UrgenciaRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.UrgenciaResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/patients/me/health-profile")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHealthProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.HealthProfileResponse>> $completion);
    
    @retrofit2.http.PUT(value = "api/patients/me/health-profile")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateHealthProfile(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.HealthProfileResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/appointments/{id}/cancel")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cancelPatientAppointment(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AppointmentDto>> $completion);
    
    @retrofit2.http.GET(value = "api/expediente")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDentalRecords(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.DentalRecordsResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/analyze-photo")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object analyzePhoto(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.AnalyzePhotoRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AnalyzePhotoResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/patients/me/tratamientos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTratamientos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.ui.tratamiento.TratamientosResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/patients/me/clinical-alerts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClinicalAlerts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ClinicalAlertsResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/confirm-medication")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object confirmMedication(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.ConfirmMedicationRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ConfirmMedicationResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/expediente/share-token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createShareToken(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.ShareTokenRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ShareTokenResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/expediente/share-tokens/mis")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMisShareTokens(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ShareTokensResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/analyze-photo")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object subirRadiografia(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.RadiografiaUploadRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.RadiografiaResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/recetas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object escanearReceta(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.RecetaOcrRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.RecetaOcrResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/recetas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMisRecetas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.RecetasResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/prescriptions")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createPrescription(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.ManualPrescriptionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.ManualPrescriptionResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/ai/feedback")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object submitAiFeedback(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.AiFeedbackRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.AiFeedbackResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/loyalty/balance")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLoyaltyBalance(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.LoyaltyBalanceResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/loyalty/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLoyaltyHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.LoyaltyHistoryResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/subscriptions/start-trial")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startTrial(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.StartTrialResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "api/users/onboarding")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveOnboarding(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.OnboardingRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.dentapp.app.data.model.MessageResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}