package com.reacttest

import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.soloader.SoLoader
import com.facebook.react.defaults.DefaultReactNativeHost
import com.clevertap.react.CleverTapPackage
import com.clevertap.react.CleverTapApplication
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.CleverTapAPI.LogLevel
import com.clevertap.android.sdk.ActivityLifecycleCallback

class MainApplication : CleverTapApplication(), ReactApplication {

    override val reactNativeHost: ReactNativeHost = object : DefaultReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override fun getPackages(): MutableList<ReactPackage> {
            val packages = PackageList(this).packages
            packages.add(CleverTapPackage()) // keep if autolink fails
            return packages
        }

        override fun getJSMainModuleName(): String = "index"
    }

    override fun onCreate() {
        // CleverTap verbose logs for dev
        CleverTapAPI.setDebugLevel(LogLevel.VERBOSE)

        super.onCreate()

        // Register CleverTap lifecycle helper if required by the SDK
        try {
            ActivityLifecycleCallback.register(this)
        } catch (_: Throwable) {
            // ignore if not required or already registered
        }

        SoLoader.init(this, /* native exopackage */ false)
    }
}
