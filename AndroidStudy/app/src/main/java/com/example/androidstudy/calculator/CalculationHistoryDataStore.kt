package com.example.androidstudy.calculator

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.historyDataStore by preferencesDataStore(name = "calculation_history")

@Singleton
class CalculationHistoryDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val HISTORY_KEY = stringPreferencesKey("history")

    val historyFlow: Flow<List<String>> = context.historyDataStore.data
        .map { preferences ->
            preferences[HISTORY_KEY]?.split(",")?.takeLast(20) ?: emptyList()
        }

    suspend fun addCalculation(calculation: String) {
        context.historyDataStore.edit { preferences ->
            val currentHistory = preferences[HISTORY_KEY]?.split(",") ?: emptyList()
            val updatedHistory = (currentHistory + calculation).takeLast(20)
            preferences[HISTORY_KEY] = updatedHistory.joinToString(",")
        }
    }

    suspend fun clearHistory() {
        context.historyDataStore.edit { preferences ->
            preferences[HISTORY_KEY] = ""
        }
    }
}
