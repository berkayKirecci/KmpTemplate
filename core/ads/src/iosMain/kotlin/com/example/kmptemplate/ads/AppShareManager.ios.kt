@file:OptIn(ExperimentalForeignApi::class)

package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Composable
actual fun rememberAppShareManager(): AppShareManager {
    return remember {
        object : AppShareManager {
            override fun shareApp(message: String) {
                val activityItems = listOf(message)
                val controller = UIActivityViewController(
                    activityItems = activityItems,
                    applicationActivities = null
                )
                UIApplication.sharedApplication.keyWindow?.rootViewController
                    ?.presentViewController(controller, animated = true, completion = null)
            }
        }
    }
}

