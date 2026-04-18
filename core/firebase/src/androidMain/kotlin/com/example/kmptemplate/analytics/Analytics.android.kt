package com.example.kmptemplate.analytics

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

actual class Analytics actual constructor() {

    private val analytics = Firebase.analytics

    actual fun logEvent(name: String, params: Map<String, String>) {
        val bundle = Bundle().apply { params.forEach { (k, v) -> putString(k, v) } }
        analytics.logEvent(name, bundle)
    }

    actual fun logScreen(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}

