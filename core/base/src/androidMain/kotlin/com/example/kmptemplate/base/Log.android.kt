package com.example.kmptemplate.base

import android.util.Log as AndroidLog

internal actual fun platformLog(
    level: LogLevel,
    tag: String,
    message: String,
    throwable: Throwable?,
) {
    when (level) {
        LogLevel.Debug -> AndroidLog.d(tag, message, throwable)
        LogLevel.Info -> AndroidLog.i(tag, message, throwable)
        LogLevel.Warn -> AndroidLog.w(tag, message, throwable)
        LogLevel.Error -> AndroidLog.e(tag, message, throwable)
    }
}
