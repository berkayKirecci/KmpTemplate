package com.example.kmptemplate.ads

/**
 * Google's demo ad unit ids. They always serve test ads and are not tied to any AdMob
 * account, so they cannot generate invalid traffic.
 *
 * Replace both values with your own ad unit ids before publishing, and update the
 * AdMob application id in androidApp/src/main/AndroidManifest.xml to match.
 *
 * https://developers.google.com/admob/android/test-ads
 */
actual object AdConstants {
    actual val bannerAdId = "ca-app-pub-3940256099942544/9214589741"
    actual val interstitialAdId = "ca-app-pub-3940256099942544/1033173712"
}
