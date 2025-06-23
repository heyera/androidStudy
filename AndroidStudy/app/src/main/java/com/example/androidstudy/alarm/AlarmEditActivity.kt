package com.example.androidstudy.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AlarmEditViewModel = viewModel()

            val timeType by viewModel.timeType.collectAsState()
            val hour by viewModel.hour.collectAsState()
            val minute by viewModel.minute.collectAsState()
            val label by viewModel.label.collectAsState()
            val useSound by viewModel.useSound.collectAsState()
            val useVibration by viewModel.useVibration.collectAsState()

            AlarmEditScreen(
                timeType = timeType,
                hour = hour,
                minute = minute,
                label = label,
                useSound = useSound,
                useVibration = useVibration,
                onTimeTypeChange = viewModel::updateTimeType,
                onHourChange = viewModel::updateHour,
                onMinuteChange = viewModel::updateMinute,
                onLabelChange = viewModel::updateLabel,
                onUseSoundChange = viewModel::updateUseSound,
                onUseVibrationChange = viewModel::updateUseVibration,
                onSave = {
                    val resultIntent = viewModel.save()
                    setResult(RESULT_OK, resultIntent)
                    finish()
                },
                onDelete = { finish() }
            )
        }
    }
}
