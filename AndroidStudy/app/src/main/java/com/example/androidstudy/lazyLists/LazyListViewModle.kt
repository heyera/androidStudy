package com.example.androidstudy.lazyLists

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CardItem(val title: String, val content: String)

class LazyListsViewModel : ViewModel() {

    private val _items = MutableStateFlow(
        List(20) { index -> CardItem(title = "제목 #$index", content = "${index}의 내용입니다.") }
    )
    val items: StateFlow<List<CardItem>> = _items
}
