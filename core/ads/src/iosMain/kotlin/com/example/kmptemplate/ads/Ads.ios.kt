@file:OptIn(ExperimentalForeignApi::class)

package com.example.kmptemplate.ads

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import swiftPMImport.com.example.kmptemplate.core.ads.GADAdSizeBanner
import swiftPMImport.com.example.kmptemplate.core.ads.GADBannerView
import swiftPMImport.com.example.kmptemplate.core.ads.GADFullScreenContentDelegateProtocol
import swiftPMImport.com.example.kmptemplate.core.ads.GADFullScreenPresentingAdProtocol
import swiftPMImport.com.example.kmptemplate.core.ads.GADInterstitialAd
import swiftPMImport.com.example.kmptemplate.core.ads.GADRequest
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
actual fun InterstitialAd(onDismiss: () -> Unit) {
    LaunchedEffect(Unit) {
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
}