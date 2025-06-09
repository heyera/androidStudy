package com.example.androidstudy.alarm.Preview

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.example.androidstudy.alarm.Alarm
import com.example.androidstudy.alarm.AlarmItem

@Preview(showBackground = true)
@Composable
fun Preview_AlarmListScreen() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AlarmListScreen(
                alarms = listOf(
                    Alarm(1, "06:30", "기상 알람", true),
                    Alarm(2, "07:00", "출근 준비", false),
                    Alarm(3, "08:15", "회의", true)
                ),
                onToggle = {},
                onAdd = {}
            )
        }
    }
}

@Composable
fun AlarmListScreen(
    alarms: List<Alarm>,
    onToggle: (Int) -> Unit,
    onAdd: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "알람 추가")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(alarms) { alarm ->
                AlarmItem(alarm = alarm, onToggle = { onToggle(alarm.id) })
            }
        }
    }
}
