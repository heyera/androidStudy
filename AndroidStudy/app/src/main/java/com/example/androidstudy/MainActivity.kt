package com.example.androidstudy


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstudy.calculator.CalculatorActivity
import com.example.androidstudy.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "mainStart")
        setContent {

            Surface(
                modifier = Modifier
                    .fillMaxSize(), // 화면을 가득 채우는 Surface
                color = Color.Gray
            ) {
                val context = LocalContext.current

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    // 가로 중앙 정렬
                    horizontalAlignment = Alignment.CenterHorizontally,
                    // 위쪽 정렬
                    verticalArrangement = Arrangement.Top
                ) {
                    NavigationButton(
                        context = context,
                        text = "계산기",
                        destination = CalculatorActivity::class.java
                    )
                    NavigationButton(
                        context = context,
                        text = "설정 화면",
                        destination = SettingActivity::class.java
                    )
                }
            }

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
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Text(text = text)
    }
}


