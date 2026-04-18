package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable

@Composable
expect fun RequestReview(trigger: Boolean, onReviewed: () -> Unit)
