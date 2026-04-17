package com.example.kmptemplate.auth


expect class FirebaseAuth() {
    val isSignedIn: Boolean

    suspend fun signInAnonymously()
}

