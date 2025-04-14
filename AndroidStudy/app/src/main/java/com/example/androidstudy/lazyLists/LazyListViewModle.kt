package com.example.androidstudy.lazyLists

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class CardItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)

@HiltViewModel
class LazyListsViewModel @Inject constructor(
    private val dataStore: CardItemDataStore
) : ViewModel() {


    private val _items = MutableStateFlow<List<CardItem>>(emptyList())
    val items: StateFlow<List<CardItem>> = _items

    init {
        viewModelScope.launch {
            dataStore.itemsFlow.collect { stored ->
                _items.value = stored.sortedByDescending { it.createdAt }
            }
        }
    }

    fun addItem(title: String, content: String) {
        val newItem = CardItem(title = title, content = content)
        val updated = listOf(newItem) + _items.value

        _items.value = updated.sortedByDescending { it.createdAt }

        viewModelScope.launch {
            dataStore.saveItems(updated)
        }
    }

    fun updateItem(id: String, newTitle: String, newContent: String) {
        val updated = _items.value.map {
            if (it.id == id) it.copy(title = newTitle, content = newContent)
            else it
        }
        _items.value = updated
        viewModelScope.launch {
            dataStore.saveItems(updated)
        }
    }

    fun deleteItem(id: String) {
        val updated = _items.value.filterNot { it.id == id }
        _items.value = updated
        viewModelScope.launch {
            dataStore.saveItems(updated)
        }
    }
}
