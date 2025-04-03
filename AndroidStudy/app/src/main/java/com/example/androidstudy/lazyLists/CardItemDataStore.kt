package com.example.androidstudy.lazyLists

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.cardItemDataStore by preferencesDataStore("card_items")

class CardItemDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val gson = Gson()
    private val ITEMS_KEY = stringPreferencesKey("card_item_list")

    val itemsFlow: Flow<List<CardItem>> = context.cardItemDataStore.data
        .map { prefs ->
            prefs[ITEMS_KEY]?.let { json ->
                val type = object : TypeToken<List<CardItem>>() {}.type
                gson.fromJson(json, type)
            } ?: emptyList()
        }

    suspend fun saveItems(items: List<CardItem>) {
        val json = gson.toJson(items)
        context.cardItemDataStore.edit { prefs ->
            prefs[ITEMS_KEY] = json
        }
    }
}