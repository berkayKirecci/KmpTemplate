@file:OptIn(ExperimentalForeignApi::class)

package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMobileAds.GADAdSizeBanner
import cocoapods.GoogleMobileAds.GADBannerView
import cocoapods.GoogleMobileAds.GADFullScreenContentDelegateProtocol
import cocoapods.GoogleMobileAds.GADFullScreenPresentingAdProtocol
import cocoapods.GoogleMobileAds.GADInterstitialAd
import cocoapods.GoogleMobileAds.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.UIKit.UIApplication
import platform.darwin.NSObject

@Composable
actual fun BannerAd(modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            GADBannerView(adSize = GADAdSizeBanner.readValue()).apply {
                adUnitID = AdConstants.bannerAdId
                rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                loadRequest(GADRequest())
            }
        }
    )
}

@Composable
actual fun InterstitialAd(onDismiss: () -> Unit, modifier: Modifier) {
    GADInterstitialAd.loadWithAdUnitID(
        adUnitID = AdConstants.interstitialAdId,
        request = GADRequest(),
        completionHandler = { ad, _ ->
            if (ad != null) {
                ad.fullScreenContentDelegate =
                    object : NSObject(), GADFullScreenContentDelegateProtocol {
                        override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                            onDismiss()
                        }
                    }
                ad.presentFromRootViewController(UIApplication.sharedApplication.keyWindow?.rootViewController)
            }
        }
    )
}