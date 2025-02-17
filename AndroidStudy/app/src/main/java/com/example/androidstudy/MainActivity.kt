package com.example.androidstudy


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstudy.calculator.CalculatorActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "mainStart")
        setContent {

            val viewModel: MainViewModel = viewModel()

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            ) {
                val context = LocalContext.current

                //CounterApp(viewModel)
                NavigationButton(context = context, text = "계산기", destination = CalculatorActivity::class.java)


            }

        }
    }
}

@Composable
fun SimpleText(
    text: String,
    fontSize: TextUnit = 30.sp,
    color: Color = Color.Black
) {
    Text(
        text = text,
        style = TextStyle(fontSize = fontSize),
        color = color
    )
}

@Composable
fun SimpleButton(text: String) {
    Button(onClick = { }) {
        Text(text = text)
    }
}


@Composable
fun CounterApp(viewModle: MainViewModel) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        SimpleText(text = "카운트: ${viewModle.count}", fontSize = 24.sp)

        Button(onClick = { viewModle.increase() }) {
            SimpleText(text = "증가", fontSize = 16.sp, color = Color.Blue)
        }
    }
}

@Composable
fun NavigationButton(context: Context, text: String, destination: Class<*>) {
    Button(
        onClick = {
            val intent = Intent(context, destination)
            context.startActivity(intent)
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}


