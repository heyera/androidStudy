package com.example.androidstudy.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val settingViewModel: SettingViewModel = viewModel()

            NavHost(navController, startDestination = "main") {
                composable("main") { MainScreen(navController, settingViewModel) }
                composable("settings") {
                    SettingsScreen(
                        settingViewModel,
                        onBack = { navController.popBackStack() })
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, settingsViewModel: SettingViewModel) {
    val exampleSwitchState by settingsViewModel.exampleSwitch.collectAsState(initial = false)

    Scaffold(
        topBar = { TopAppBar(title = { Text("메인 화면") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "설정 상태: ${if (exampleSwitchState) "ON" else "OFF"}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("settings") }) {
                Text("설정 열기")
            }
        }
    }
}