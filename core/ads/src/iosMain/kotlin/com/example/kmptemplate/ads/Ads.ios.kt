package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMobileAds.GADAdSizeBanner
import cocoapods.GoogleMobileAds.GADBannerView
import cocoapods.GoogleMobileAds.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun BannerAd(adId: String, modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            GADBannerView(adSize = GADAdSizeBanner.readValue()).apply {
                adUnitID = adId
                rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                loadRequest(GADRequest())
            }
        }
    )
}