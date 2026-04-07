package com.dentapp.app.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("dentapp_prefs")

@Singleton
class TokenStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val KEY_TOKEN   = stringPreferencesKey("jwt_token")
    private val KEY_ROLE    = stringPreferencesKey("user_role")
    private val KEY_USER_ID = stringPreferencesKey("user_id")
    private val KEY_COUNTRY = stringPreferencesKey("user_country")

    val token: Flow<String?> = context.dataStore.data.map { it[KEY_TOKEN] }
    val role: Flow<String?>  = context.dataStore.data.map { it[KEY_ROLE] }
    val userId: Flow<String?> = context.dataStore.data.map { it[KEY_USER_ID] }
    val country: Flow<String?> = context.dataStore.data.map { it[KEY_COUNTRY] }

    suspend fun save(token: String, role: String, userId: String) {
        context.dataStore.edit {
            it[KEY_TOKEN]   = token
            it[KEY_ROLE]    = role
            it[KEY_USER_ID] = userId
        }
    }

    suspend fun saveCountry(country: String) {
        context.dataStore.edit { it[KEY_COUNTRY] = country }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
