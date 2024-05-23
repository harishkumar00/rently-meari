package com.rentlymeari

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.meari.sdk.BuildConfig
import com.meari.sdk.MeariSdk
import com.meari.sdk.MeariUser
import com.meari.sdk.bean.CameraInfo
import com.ppstrong.ppsplayer.meariLog
import com.rentlymeari.dashboard.NavController
import com.rentlymeari.meari.Meari
import com.rentlymeari.meari.MeariMQTTCallback
import com.rentlymeari.ui.theme.MeariTheme
import kotlinx.coroutines.delay

class MeariActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val deviceId = intent.getStringExtra("deviceId")

    MeariSdk.init(this, MeariMQTTCallback())
    if (BuildConfig.DEBUG) {
      meariLog.createlibrarylog()
      meariLog.getInstance().setlevel(0)
      MeariSdk.getInstance().isDebug = true
    }

    // Connect Meari MQTT service
    MeariUser.getInstance().connectMqttServer(application)

    setContent {

      val cameraInfo = remember {
        mutableStateOf<CameraInfo?>(null)
      }

      LaunchedEffect(Unit) {
        val doorbells = Meari.loginAndFetchDoorbells()
        delay(10000)
        cameraInfo.value = doorbells?.doorBells?.find {
          it.deviceID == deviceId
        }
      }

      MeariTheme {
        NavController(
          cameraInfo = cameraInfo
        )
      }
    }
  }
}
