package com.example.androidstudy.alarm.preview

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import com.example.androidstudy.alarm.AlarmEditScreen
import com.example.androidstudy.alarm.DropdownSelector

@Preview(showBackground = true)
@Composable
fun Preview_AlarmEditScreen() {
    var timeType by remember { mutableStateOf("오전") }
    var hour by remember { mutableStateOf("7") }
    var minute by remember { mutableStateOf("30") }
    var label by remember { mutableStateOf("출근 준비") }
    var alarmMode by remember { mutableStateOf(2) }

    MaterialTheme {
        AlarmEditScreen(
            timeType = timeType,
            hour = hour,
            minute = minute,
            label = label,
            useVibration = true,
            onTimeTypeChange = { timeType = it },
            onHourChange = { hour = it },
            onMinuteChange = { minute = it },
            onLabelChange = { label = it },
            onUseSoundChange = {/* 프리뷰용 */   },
            onUseVibrationChange = {/* 프리뷰용 */  },
            useSound = true,
            onSave = { /* 프리뷰용 */ },
            onDelete = { /* 프리뷰용 */ }

        )
    }
}
