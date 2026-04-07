# DentApp Android — Context for Claude Code

## Stack
Kotlin + Jetpack Compose + Hilt + Retrofit. Backend: Node.js + PostgreSQL at http://62.84.177.147:3000

## Architecture
- ui/home/HomePatientScreen.kt — main screen
- ui/ai/AiManagerScreen.kt — AI chat
- ui/profile/ProfilePatientScreen.kt — profile
- ui/doctors/DoctorsScreen.kt — doctors list
- ui/onboarding/OnboardingPatientScreen.kt — onboarding
- ui/theme/ — Colors.kt, Typography.kt, Shape.kt
- NavGraph.kt — all routes

## Design System (DO NOT CHANGE)
Primary: #00BFA5, Accent: #FF6B6B, Background: #F5F7FA
All cards: 20dp radius, soft shadow, no hard elevation
Animations: spring() only

## Push command (always use this)
git push https://andresgt1989:$(cat /tmp/tok)@github.com/andresgt1989/dentapp-android.git main && rm /tmp/tok

## Rules
- ALWAYS read the relevant .kt file before editing it
- NEVER change working screens to fix other screens
- Fix ONE thing at a time, compile-check mentally before committing
- After each task run: ./gradlew assembleDebug 2>&1 | tail -20 to verify no errors
