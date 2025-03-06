package com.example.androidstudy.setting

import android.content.Context
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    val alarmSetting by settingsViewModel.alarmSetting.collectAsState(initial = 0)
    val appContext = LocalContext.current

    // 알람 권한 요청
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(appContext, "알람 권한이 필요합니다!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // 🔹 권한 요청 실행
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= 33) {
            //notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    Scaffold(
        topBar = { TopAppBar(title = { Text("메인 화면") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Button(onClick = { navController.navigate("settings") }) {
                Text("설정 열기")
            }
            Spacer(modifier = Modifier.height(32.dp))

            Text("스위치 설정 예제")
            Text(
                "설정 상태: ${if (exampleSwitchState) "ON" else "OFF"}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("알라 울리기 예제")
            Button(onClick = { triggerAlarm(appContext, alarmSetting) }) {
                Text(text = "알람 울리기")
            }
        }
    }


}

fun triggerAlarm(context: Context, setting: Int) {
    val vibrator = context.getSystemService(Vibrator::class.java)
    val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    val ringtone: Ringtone? = try {
        RingtoneManager.getRingtone(context, ringtoneUri)
    } catch (e: Exception) {
        null
    }

    ringtone?.stop()

    val mediaPlayer = MediaPlayer().apply {
        setDataSource(context, ringtoneUri)
        prepare()
    }
    val duration = mediaPlayer.duration // 알람 소리의 길이 가져오기 (밀리초 단위)
    mediaPlayer.release()

    when (setting) {
        0 -> { // 진동
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {    // api 25이하 에서는 다른 방식으로 동작
                vibrator?.vibrate(500)
            }
        }

        1 -> { // 알람음
            ringtone?.play()
            Handler(Looper.getMainLooper()).postDelayed({ ringtone?.stop()}, duration.toLong())
        }

        2 -> { // 둘 다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {    // api 25이하 에서는 다른 방식으로 동작
                vibrator?.vibrate(500)
            }
            ringtone?.play()
            Handler(Looper.getMainLooper()).postDelayed({ ringtone?.stop()}, duration.toLong())
        }
    }
}
