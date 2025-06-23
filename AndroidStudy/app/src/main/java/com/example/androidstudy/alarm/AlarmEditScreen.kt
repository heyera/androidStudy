package com.example.androidstudy.alarm

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AlarmEditScreen(
    timeType: String,
    hour: String,
    minute: String,
    label: String,
    useVibration: Boolean,
    useSound: Boolean,
    onTimeTypeChange: (String) -> Unit,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onLabelChange: (String) -> Unit,
    onUseVibrationChange: (Boolean) -> Unit,
    onUseSoundChange: (Boolean) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {

            //시간 설정
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("시간 설정", style = MaterialTheme.typography.titleMedium)

                    val focusManager = LocalFocusManager.current
                    val minuteFocusRequester = remember { FocusRequester() }

                    val displayHour = hour.ifBlank { "6" }
                    val displayMinute = minute.ifBlank { "00" }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DropdownSelector("오전/오후", listOf("오전", "오후"), timeType, onTimeTypeChange)

                        Spacer(modifier = Modifier.width(8.dp))


                        val context = LocalContext.current

                        OutlinedTextField(
                            value = hour,
                            onValueChange = {
                                if (it.all { ch -> ch.isDigit() } && it.length <= 2) {
                                    val num = it.toIntOrNull()
                                    if (num != null && num > 12) {
                                        Toast.makeText(context, "올바르지 않은 값입니다", Toast.LENGTH_SHORT).show()
                                        Log.d("test","입력값 오류")
                                    } else {
                                        onHourChange(it)
                                    }
                                }
                            },
                            label = { Text("시") },
                            placeholder = { Text("6") },
                            modifier = Modifier.width(100.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { minuteFocusRequester.requestFocus() }
                            )
                        )




                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            value = displayMinute,
                            onValueChange = {
                                if (it.all { ch -> ch.isDigit() } && it.length <= 2) {
                                    onMinuteChange(it)
                                }
                            },
                            label = { Text("분") },
                            modifier = Modifier
                                .width(100.dp)
                                .focusRequester(minuteFocusRequester),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            )
                        )

                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //알람 이름
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("알람 이름", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = label,
                        onValueChange = onLabelChange,
                        label = { Text("예: 기상 알람") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //알림 방식
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("알림 방식", style = MaterialTheme.typography.titleMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = useVibration, onCheckedChange = onUseVibrationChange)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("진동")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = useSound, onCheckedChange = onUseSoundChange)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("소리")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //저장 및 삭제 버튼
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("삭제", color = MaterialTheme.colorScheme.onError)
                }

                Button(onClick = onSave) {
                    Text("저장")
                }
            }
        }
    }
}


@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selected: String,
    onSelectedChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            modifier = Modifier.width(100.dp),
            readOnly = true,
            label = { Text(label) }, // 여기로 label 이동
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "드롭다운 열기")
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelectedChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


