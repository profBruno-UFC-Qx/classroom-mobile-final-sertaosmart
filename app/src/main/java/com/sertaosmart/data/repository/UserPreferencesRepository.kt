package com.sertaosmart.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Cria uma instância do DataStore para o app
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Repositório para ler e salvar as preferências do usuário.
 */
class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val SELECTED_STATION_CODE = stringPreferencesKey("selected_station_code")
    }

    val selectedStationCode: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PreferencesKeys.SELECTED_STATION_CODE] ?: "A301" } // "A301" como padrão

    suspend fun saveStationCode(code: String) {
        context.dataStore.edit { preferences -> preferences[PreferencesKeys.SELECTED_STATION_CODE] = code }
    }
}