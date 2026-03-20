package com.example.kmptemplate.ads

import cocoapods.GoogleMobileAds.GADMobileAds
import kotlinx.cinterop.ExperimentalForeignApi

actual class AdManager {
    @OptIn(ExperimentalForeignApi::class)
    actual fun initAds() {
        GADMobileAds.sharedInstance.startWithCompletionHandler { status ->
            println("AdManager Status : ${status?.description}")
        }
    }
}