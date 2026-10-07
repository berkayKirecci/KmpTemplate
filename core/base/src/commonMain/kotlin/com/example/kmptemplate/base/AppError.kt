package com.example.kmptemplate.base

/**
 * Error categories the UI can render. Errors are raised far from the UI layer, where no
 * Composable scope and no locale are available, so they carry a category rather than
 * pre-rendered English text; the UI resolves it to a localized string.
 */
enum class AppError {
    /** The request never reached the server, or the response was unreadable. */
    CONNECTION,

    /** The server answered with a failure. [AppErrorAware.serverMessage] may explain it. */
    SERVER,

    UNKNOWN,
}

/** Implemented by exceptions that can be shown to a user. */
interface AppErrorAware {
    val appError: AppError

    /** Server-supplied text. Shown verbatim when present, since it is more specific. */
    val serverMessage: String? get() = null
}
