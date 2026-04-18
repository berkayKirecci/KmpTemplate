package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable

interface AppShareManager {
    fun shareApp(message: String)
}

@Composable
expect fun rememberAppShareManager(): AppShareManager

