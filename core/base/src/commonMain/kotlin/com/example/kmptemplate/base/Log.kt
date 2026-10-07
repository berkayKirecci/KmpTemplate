package com.example.kmptemplate.base

enum class LogLevel { Debug, Info, Warn, Error }

/**
 * Minimal multiplatform logger. Deliberately small: a template logger that accumulates
 * features becomes a liability, so the only indirection is [platformLog].
 */
object Log {

    private val sinks = mutableListOf<LogSink>()

    /**
     * Registers an additional destination, e.g. crash-report breadcrumbs.
     *
     * core:base cannot depend on core:firebase, so the crash reporter attaches itself here
     * instead of Log reaching outwards. Call during startup, before other threads log.
     */
    fun addSink(sink: LogSink) {
        sinks += sink
    }

    fun d(tag: String, message: String) = dispatch(LogLevel.Debug, tag, message, null)

    fun i(tag: String, message: String) = dispatch(LogLevel.Info, tag, message, null)

    fun w(tag: String, message: String, throwable: Throwable? = null) =
        dispatch(LogLevel.Warn, tag, message, throwable)

    fun e(tag: String, message: String, throwable: Throwable? = null) =
        dispatch(LogLevel.Error, tag, message, throwable)

    private fun dispatch(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        platformLog(level, tag, message, throwable)
        sinks.forEach { it.onLog(level, tag, message, throwable) }
    }
}

/** An extra destination for log output. */
fun interface LogSink {
    fun onLog(level: LogLevel, tag: String, message: String, throwable: Throwable?)
}

internal expect fun platformLog(
    level: LogLevel,
    tag: String,
    message: String,
    throwable: Throwable?,
)
