package com.example.androidstudy.alarm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class Alarm(
    val id: Int,
    val time: String,
    val label: String,
    val enabled: Boolean
)


@HiltViewModel
class AlarmListViewModel @Inject constructor() : ViewModel() {

    private val _alarmList = MutableStateFlow<List<Alarm>>(
        listOf(
            //목업 데이터
            Alarm(1, "07:30", "기상 알람", true),
            Alarm(2, "08:00", "회의 알람", false)
        )
    )
    val alarmList: StateFlow<List<Alarm>> = _alarmList

    fun toggleAlarm(id: Int) {
        val updatedList = _alarmList.value.map {
            if (it.id == id) it.copy(enabled = !it.enabled) else it
        }
        _alarmList.value = updatedList
    }

    fun addAlarm(time: String, label: String) {
        val nextId = (_alarmList.value.maxOfOrNull { it.id } ?: 0) + 1
        val newAlarm = Alarm(nextId, time, label, enabled = true)
        _alarmList.value = _alarmList.value + newAlarm
    }
}
