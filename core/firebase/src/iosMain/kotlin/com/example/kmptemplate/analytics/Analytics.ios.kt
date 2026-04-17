@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.example.kmptemplate.analytics

import cocoapods.FirebaseAnalytics.FIRAnalytics

actual class Analytics actual constructor() {

    actual fun logEvent(name: String, params: Map<String, String>) {
        @Suppress("UNCHECKED_CAST")
        FIRAnalytics.logEventWithName(name, parameters = params as Map<Any?, *>)
    }

    actual fun logScreen(screenName: String) {
        @Suppress("UNCHECKED_CAST")
        FIRAnalytics.logEventWithName(
            "screen_view",
            parameters = mapOf("screen_name" to screenName) as Map<Any?, *>
        )
    }
}

