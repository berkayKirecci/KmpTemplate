package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect class AdManager {
    fun initAds()
}

@Composable
expect fun BannerAd(adId: String, modifier: Modifier = Modifier)