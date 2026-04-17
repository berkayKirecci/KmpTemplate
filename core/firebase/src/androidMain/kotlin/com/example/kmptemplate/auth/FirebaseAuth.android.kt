package com.example.kmptemplate.auth

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.firebase.auth.FirebaseAuth as GoogleFirebaseAuth

actual class FirebaseAuth actual constructor() {

    private val auth = GoogleFirebaseAuth.getInstance()

    actual val isSignedIn: Boolean
        get() = auth.currentUser != null

    actual suspend fun signInAnonymously() {
        runCatching {
            if (isSignedIn) return
            suspendCancellableCoroutine { cont ->
                auth.signInAnonymously()
                    .addOnSuccessListener { cont.resume(Unit) }
                    .addOnFailureListener { cont.resumeWithException(it) }
            }
        }
    }
}

