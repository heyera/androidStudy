package com.example.androidstudy.alarm

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AlarmListScreen(viewModel: AlarmListViewModel) {
    val alarmList by viewModel.alarmList.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val time = result.data?.getStringExtra("time") ?: return@rememberLauncherForActivityResult
            val label = result.data?.getStringExtra("label") ?: ""
            viewModel.addAlarm(time, label)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch(Intent(context, AlarmEditActivity::class.java))
            }) {
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
            items(alarmList) { alarm ->
                AlarmItem(alarm = alarm, onToggle = {
                    viewModel.toggleAlarm(alarm.id)
                })
            }
        }
    }
}
@Composable
fun AlarmItem(alarm: Alarm, onToggle: () -> Unit) {
    val backgroundColor = if (alarm.enabled) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = alarm.time,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (alarm.enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = alarm.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (alarm.enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Switch(
                checked = alarm.enabled,
                onCheckedChange = { onToggle() }
            )
        }
    }
}
