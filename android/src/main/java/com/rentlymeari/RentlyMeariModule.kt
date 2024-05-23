package com.rentlymeari

import android.content.Intent
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap

class RentlyMeariModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun openLivePreview(params: ReadableMap) {
    Log.i("Meari", params.toString())

    val intent = Intent(reactContext, MeariActivity::class.java)

    if (intent.resolveActivity(reactContext.packageManager) != null) {
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      intent.putExtra("deviceId", params.getString("deviceId"))

      reactContext.startActivity(intent)
      Log.i("Rently", "Starting Meari Activity")
    }
  }

  companion object {
    const val NAME = "RentlyMeariModule"
  }
}
