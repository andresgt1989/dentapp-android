# Retrofit + Kotlinx Serialization
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.dentapp.app.data.model.** { *; }
-keep interface com.dentapp.app.data.api.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Stripe
-keep class com.stripe.android.** { *; }
