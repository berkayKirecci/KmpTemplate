package com.example.kmptemplate.firebase.auth


expect class FirebaseAuth() {
    val isSignedIn: Boolean

    suspend fun signInAnonymously()
}

