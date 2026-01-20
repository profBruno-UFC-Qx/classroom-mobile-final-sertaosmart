package com.sertaosmart.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val SELECTED_STATION_CODE = stringPreferencesKey("selected_station_code")
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    val selectedStationCode: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.SELECTED_STATION_CODE] ?: "A301" }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.IS_DARK_THEME] ?: false }

    suspend fun saveStationCode(code: String) {
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.SELECTED_STATION_CODE] = code }
    }

    suspend fun saveThemePreference(isDark: Boolean) {
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.IS_DARK_THEME] = isDark }
    }
}