package com.example.kmptemplate.ads

/**
 * Google's demo ad unit ids. They always serve test ads and are not tied to any AdMob
 * account, so they cannot generate invalid traffic.
 *
 * Replace both values with your own ad unit ids before publishing, and update
 * GADApplicationIdentifier in iosApp/iosApp/Info.plist to match.
 *
 * https://developers.google.com/admob/ios/test-ads
 */
actual object AdConstants {
    actual val bannerAdId: String = "ca-app-pub-3940256099942544/2435281174"
    actual val interstitialAdId: String = "ca-app-pub-3940256099942544/4411468910"
}
