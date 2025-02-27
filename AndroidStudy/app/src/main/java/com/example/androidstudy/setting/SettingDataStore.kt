package com.example.androidstudy.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "settings_prefs")

class SettingsDataStore(private val context: Context) {
    private val EXAMPLE_SWITCH = booleanPreferencesKey("example_switch")

    val exampleSwitchFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_SWITCH] ?: false
        }

    suspend fun saveExampleSwitchState(state: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[EXAMPLE_SWITCH] = state
        }
    }

}