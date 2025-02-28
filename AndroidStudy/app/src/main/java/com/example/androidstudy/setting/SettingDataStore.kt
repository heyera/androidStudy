package com.example.androidstudy.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "settings_prefs")

class SettingsDataStore(private val context: Context) {
    private val EXAMPLE_SWITCH = booleanPreferencesKey("example_switch")
    private val ALARM_SETTING = intPreferencesKey("alarm_setting")

    val exampleSwitchFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_SWITCH] ?: false
        }

    val alarmSettingFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[ALARM_SETTING] ?: 0  // 기본값: 0 (진동)
        }


    suspend fun saveExampleSwitchState(state: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[EXAMPLE_SWITCH] = state
        }
    }

    suspend fun saveAlarmSetting(option: Int) {
        context.dataStore.edit { preferences ->
            preferences[ALARM_SETTING] = option
        }
    }

}