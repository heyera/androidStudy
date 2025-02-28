package com.example.androidstudy.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingDataStore: SettingsDataStore
) : ViewModel() {

    val exampleSwitch = settingDataStore.exampleSwitchFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val alarmSetting = settingDataStore.alarmSettingFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun saveExampleSwitch(state: Boolean) {
        viewModelScope.launch {
            settingDataStore.saveExampleSwitchState(state)
        }
    }

    fun saveAlarmSetting(option: Int) {
        viewModelScope.launch {
            settingDataStore.saveAlarmSetting(option)
        }
    }
}