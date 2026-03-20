package com.example.kmptemplate.ads

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual class AdManager {
    actual fun initAds() = Unit
}

@Composable
actual fun BannerAd(adId: String, modifier: Modifier) {
    Box {}
}