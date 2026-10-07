package com.example.kmptemplate.firebase.crashlytics

import com.example.kmptemplate.base.LogLevel
import com.example.kmptemplate.base.LogSink

/**
 * Crash reporting. Every method is a no-op when Firebase has not been configured, so the app
 * behaves normally without google-services.json / GoogleService-Info.plist present.
 */
expect class CrashReporter() {

    /** False when Firebase is absent or failed to initialise. */
    val isAvailable: Boolean

    /** Adds a breadcrumb shown alongside the next crash. */
    fun log(message: String)

    /** Reports a handled error without crashing. */
    fun recordException(throwable: Throwable)

    fun setUserId(userId: String?)
}

/**
 * Forwards warnings and errors to Crashlytics as breadcrumbs. Register during startup:
 * `Log.addSink(reporter.asLogSink())`.
 */
fun CrashReporter.asLogSink(): LogSink = LogSink { level, tag, message, throwable ->
    if (level == LogLevel.Warn || level == LogLevel.Error) {
        log("[$tag] $message")
        throwable?.let(::recordException)
    }
}
