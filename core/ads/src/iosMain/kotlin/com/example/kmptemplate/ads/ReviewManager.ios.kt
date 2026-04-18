@file:OptIn(ExperimentalForeignApi::class)

package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.cinterop.ExperimentalForeignApi
import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene

@Composable
actual fun RequestReview(trigger: Boolean, onReviewed: () -> Unit) {
    if (!trigger) return
    LaunchedEffect(Unit) {
        val scene = UIApplication.sharedApplication.connectedScenes
            .filterIsInstance<UIWindowScene>()
            .firstOrNull()
        if (scene != null) {
            SKStoreReviewController.requestReviewInScene(scene)
        } else {
            @Suppress("DEPRECATION")
            SKStoreReviewController.requestReview()
        }
        onReviewed()
    }
}
