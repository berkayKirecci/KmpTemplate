package com.example.kmptemplate.analytics

expect class Analytics() {
    fun logEvent(name: String, params: Map<String, String> = emptyMap())
    fun logScreen(screenName: String)
}

