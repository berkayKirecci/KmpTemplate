package com.example.kmptemplate.platform

import androidx.compose.runtime.Composable

interface AppShareManager {
    fun shareApp(message: String)
}

@Composable
expect fun rememberAppShareManager(): AppShareManager

