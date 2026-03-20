@file:Suppress("MissingPermission")

package com.example.kmptemplate.ads

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
actual fun BannerAd(modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = AdConstants.bannerAdId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
actual fun InterstitialAd(onDismiss: () -> Unit, modifier: Modifier) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        InterstitialAd.load(
            context,
            AdConstants.interstitialAdId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            onDismiss()
                        }
                    }
                    ad.show(context as Activity)
                }
            }
        )
    }
}