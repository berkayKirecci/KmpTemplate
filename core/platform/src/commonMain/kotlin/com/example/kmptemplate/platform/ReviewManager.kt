package com.example.kmptemplate.platform

import androidx.compose.runtime.Composable

@Composable
expect fun RequestReview(trigger: Boolean, onReviewed: () -> Unit)
