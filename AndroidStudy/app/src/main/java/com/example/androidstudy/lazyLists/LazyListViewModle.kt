package com.example.androidstudy.lazyLists

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CardItem(
    val index: Int,
    val title: String,
    val content: String
)

class LazyListsViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<CardItem>>(emptyList())
    val items: StateFlow<List<CardItem>> = _items

    fun addItem(title: String, content: String) {
        val currentList = _items.value
        val newIndex = currentList.size
        val newItem = CardItem(
            index = newIndex,
            title = title,
            content = content
        )
        _items.value = currentList + newItem
    }
}
