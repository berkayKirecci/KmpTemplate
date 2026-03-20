@file:Suppress("MissingPermission")

package com.example.kmptemplate.ads

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


actual class AdManager(private val context: Context) {
    actual fun initAds() = MobileAds.initialize(context) { status ->
        for ((adapter, adapterStatus) in status.adapterStatusMap) {
            Log.d("AdManager", "$adapter: ${adapterStatus.description}")
        }
    }
}

@Composable
actual fun BannerAd(adId: String, modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}