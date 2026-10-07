package com.example.kmptemplate.base

import platform.Foundation.NSLog

internal actual fun platformLog(
    level: LogLevel,
    tag: String,
    message: String,
    throwable: Throwable?,
) {
    val suffix = throwable?.let { "\n${it.stackTraceToString()}" } ?: ""
    NSLog("%s", "[${level.name.uppercase()}] $tag: $message$suffix")
}
