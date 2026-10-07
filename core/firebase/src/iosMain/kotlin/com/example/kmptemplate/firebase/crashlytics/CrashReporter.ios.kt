package com.example.kmptemplate.firebase.crashlytics

import swiftPMImport.com.example.kmptemplate.core.firebase.FIRApp
import swiftPMImport.com.example.kmptemplate.core.firebase.FIRCrashlytics

actual class CrashReporter actual constructor() {

    /**
     * FirebaseApp.configure() is only possible with GoogleService-Info.plist in the bundle;
     * without it defaultApp() is null and every call below is a no-op.
     */
    private val crashlytics: FIRCrashlytics?
        get() = if (FIRApp.defaultApp() != null) FIRCrashlytics.crashlytics() else null

    actual val isAvailable: Boolean get() = crashlytics != null

    actual fun log(message: String) {
        crashlytics?.log(message)
    }

    actual fun recordException(throwable: Throwable) {
        crashlytics?.log("${throwable::class.simpleName}: ${throwable.message}")
    }

    actual fun setUserId(userId: String?) {
        crashlytics?.setUserID(userId ?: "")
    }
}
