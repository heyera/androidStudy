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
import androidx.compose.foundation.shape.RoundedCornerShape
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
        DisplayScreen(inputText)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonGrid(viewModel)
    }
}

@Composable
fun DisplayScreen(inputText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = inputText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun ButtonGrid(viewModel: CalculatorViewModel) {
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
