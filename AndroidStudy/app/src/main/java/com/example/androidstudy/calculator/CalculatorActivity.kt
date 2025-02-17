package com.example.androidstudy.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.CalculatorViewModel

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val inputText by viewModel.inputText.collectAsState()
    val resultText by viewModel.resultText.collectAsState()

    val buttons = listOf(
        listOf("7", "8", "9", "지우기"),
        listOf("4", "5", "6", "/"),
        listOf("1", "2", "3", "*"),
        listOf("0", "C", "+", "-")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 결과 표시 창
        Text(
            text = if (resultText.isNotEmpty()) resultText else inputText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 버튼 레이아웃
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { button ->
                    Button(
                        onClick = { viewModel.handleInput(button) },
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (button in listOf("/", "*", "+", "-", "=")) Color.Gray else Color.DarkGray
                        )
                    ) {
                        Text(text = button, fontSize = 24.sp, color = Color.White)
                    }
                }
            }
        }

        // "=" 버튼 (결과 출력)
        Button(
            onClick = { viewModel.handleInput("=") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "=", fontSize = 24.sp, color = Color.White)
        }
    }
}
