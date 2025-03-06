package com.example.androidstudy.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: CalculatorViewModel = viewModel()
            CalculatorScreen(viewModel)
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val inputText = viewModel.inputText.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayScreen(inputText, viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonGrid(viewModel)
    }
}

@Composable
fun DisplayScreen(inputText: String, viewModel: CalculatorViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val historyList by viewModel.history.collectAsState()
    var showHistory by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽 아이콘 버튼
            IconButton(
                onClick = { showHistory = !showHistory },
                modifier = Modifier.size(48.dp) // 아이콘 크기 조정
            ) {
                Icon(
                    imageVector = if (!showHistory) Icons.Outlined.Info else Icons.Outlined.Close,
                    contentDescription = "히스토리 보기"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            // 오른쪽 입력된 숫자 표시
            Text(
                text = inputText,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        if (showHistory) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .weight(1f) // 비율 조정
            ) {

                Text(
                    text = "계산 기록",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(historyList) { historyItem ->
                        Text(
                            text = historyItem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                Button(
                    onClick = { coroutineScope.launch { viewModel.clearHistory() } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "기록 삭제", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ButtonGrid(viewModel: CalculatorViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val buttons = listOf(
        listOf("7", "8", "9", "<-"),
        listOf("4", "5", "6", "/"),
        listOf("1", "2", "3", "*"),
        listOf("0", "C", "+", "-")
    )

    Column(
        modifier = Modifier.fillMaxSize()
    )
    {


        LazyColumn(modifier = Modifier.weight(1f)) {
            items(buttons) { row ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(row) { button ->
                        OperatorButton(button, viewModel)
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.handleInput("=")
                Log.d("test", "= 누름, inputText: ${viewModel.inputText.value}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "=", fontSize = 24.sp, color = Color.White)
        }
    }
}

@Composable
fun OperatorButton(buttonText: String, viewModel: CalculatorViewModel) {
    Button(
        onClick = { viewModel.handleInput(buttonText) },
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (buttonText in listOf("/", "*", "+", "-", "="))
                Color.Gray
            else Color.DarkGray
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 24.sp,
            color = Color.White,
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}
