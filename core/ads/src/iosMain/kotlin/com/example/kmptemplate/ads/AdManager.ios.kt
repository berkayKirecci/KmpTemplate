package com.example.kmptemplate.ads

import swiftPMImport.com.example.kmptemplate.core.ads.GADMobileAds
import com.example.kmptemplate.base.Log
import kotlinx.cinterop.ExperimentalForeignApi

actual class AdManager {
    @OptIn(ExperimentalForeignApi::class)
    actual fun initAds() {
        GADMobileAds.sharedInstance.startWithCompletionHandler { status ->
            Log.i("AdManager", "Google Mobile Ads initialised: ${status?.description}")
        }
    }
}