@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.example.kmptemplate.auth

import cocoapods.FirebaseAuth.FIRAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class FirebaseAuth actual constructor() {

    private val auth = FIRAuth.auth()

    actual val isSignedIn: Boolean
        get() = auth.currentUser() != null

    actual suspend fun signInAnonymously() {
        runCatching {
            if (isSignedIn) return
            suspendCancellableCoroutine { cont ->
                auth.signInAnonymouslyWithCompletion { _, error: NSError? ->
                    if (error != null) cont.resumeWithException(Exception(error.localizedDescription))
                    else cont.resume(Unit)
                }
            }
        }
    }
}

