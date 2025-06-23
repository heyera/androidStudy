package com.example.androidstudy.alarm

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmEditViewModel @Inject constructor() : ViewModel() {
    private val _timeType = MutableStateFlow("오전")
    val timeType: StateFlow<String> = _timeType

    private val _hour = MutableStateFlow("6")
    val hour: StateFlow<String> = _hour

    private val _minute = MutableStateFlow("00")
    val minute: StateFlow<String> = _minute

    private val _label = MutableStateFlow("")
    val label: StateFlow<String> = _label

    private val _useSound = MutableStateFlow(true)
    val useSound: StateFlow<Boolean> = _useSound

    private val _useVibration = MutableStateFlow(true)
    val useVibration: StateFlow<Boolean> = _useVibration


    fun updateTimeType(value: String) {
        _timeType.value = value
    }

    fun updateHour(value: String) {
        _hour.value = value
    }

    fun updateMinute(value: String) {
        _minute.value = value
    }

    fun updateLabel(value: String) {
        _label.value = value
    }

    fun updateUseSound(value: Boolean) {
        _useSound.value = value
    }

    fun updateUseVibration(value: Boolean) {
        _useVibration.value = value
    }


    fun save(): Intent {
        val validHour = hour.value.toIntOrNull()?.takeIf { it in 1..12 } ?: 6
        val validMinute = minute.value.toIntOrNull()?.takeIf { it in 0..59 } ?: 0

        val hour24 = when {
            timeType.value == "오전" && validHour == 12 -> 0
            timeType.value == "오후" && validHour != 12 -> validHour + 12
            else -> validHour
        }

        val time = "%02d:%02d".format(hour24, validMinute)

        Log.d("AlarmSave", """
        [알람 저장 정보]
        ▸ 시간   : ${timeType.value} $validHour:$validMinute (24시: $hour24)
        ▸ 라벨   : ${label.value}
        ▸ 소리   : ${useSound.value}
        ▸ 진동   : ${useVibration.value}
    """.trimIndent())

        return Intent().apply {
            putExtra("time", time)
            putExtra("label", label.value)
            putExtra("useSound", useSound.value)
            putExtra("useVibration", useVibration.value)
        }
    }
}