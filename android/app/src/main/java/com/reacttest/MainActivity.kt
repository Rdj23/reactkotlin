package com.reacttest

import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.clevertap.react.CleverTapRnAPI
import com.clevertap.android.sdk.CleverTapAPI

class MainActivity : ReactActivity() {

    override fun getMainComponentName(): String = "reacttest"

    override fun createReactActivityDelegate(): ReactActivityDelegate =
        DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inform CleverTap of initial deep link (v3.0.0+)
        CleverTapRnAPI.setInitialUri(intent?.data)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        try {
            intent?.extras?.let { extras ->
                CleverTapAPI.getDefaultInstance(this)?.pushNotificationClickedEvent(extras)
                Log.d("CleverTapEvent", "Notification click forwarded to CleverTap")
            }
        } catch (e: Exception) {
            Log.e("CleverTapEvent", "Failed to forward notification click: ${e.message}", e)
        }
    }
}
