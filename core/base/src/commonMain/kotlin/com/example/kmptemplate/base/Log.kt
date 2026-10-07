package com.example.kmptemplate.base

enum class LogLevel { Debug, Info, Warn, Error }

/**
 * Minimal multiplatform logger. Deliberately small: a template logger that accumulates
 * features becomes a liability, so the only indirection is [platformLog].
 */
object Log {

    fun d(tag: String, message: String) = platformLog(LogLevel.Debug, tag, message, null)

    fun i(tag: String, message: String) = platformLog(LogLevel.Info, tag, message, null)

    fun w(tag: String, message: String, throwable: Throwable? = null) =
        platformLog(LogLevel.Warn, tag, message, throwable)

    fun e(tag: String, message: String, throwable: Throwable? = null) =
        platformLog(LogLevel.Error, tag, message, throwable)
}

internal expect fun platformLog(
    level: LogLevel,
    tag: String,
    message: String,
    throwable: Throwable?,
)
