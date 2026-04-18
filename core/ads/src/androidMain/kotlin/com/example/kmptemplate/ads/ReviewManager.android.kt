package com.example.kmptemplate.ads

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
actual fun RequestReview(trigger: Boolean, onReviewed: () -> Unit) {
    if (!trigger) return
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val activity = context as? Activity ?: run {
            Log.w("RequestReview", "LocalContext is not an Activity")
            onReviewed()
            return@LaunchedEffect
        }
        val manager = ReviewManagerFactory.create(context)
        manager.requestReviewFlow().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                manager.launchReviewFlow(activity, task.result)
                Log.d("RequestReview", "Review flow launched")
            } else {
                Log.e("RequestReview", "Review flow failed", task.exception)
            }
        }
        onReviewed()
    }
}
