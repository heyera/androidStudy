package com.example.androidstudy.lazyLists

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CardItem(
    val index: Int,
    val title: String,
    val content: String
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
                _items.value = stored
            }
        }
    }

    fun addItem(title: String, content: String) {
        val newIndex = _items.value.size
        val newItem = CardItem(index = newIndex, title = title, content = content)
        val updated = listOf(newItem) + _items.value
        _items.value = updated

        viewModelScope.launch {
            dataStore.saveItems(updated)
        }
    }
}
