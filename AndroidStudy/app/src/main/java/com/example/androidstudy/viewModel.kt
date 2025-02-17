package com.example.androidstudy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var count by mutableStateOf(0)
        private set

    fun increase() {
        count++
    }
}