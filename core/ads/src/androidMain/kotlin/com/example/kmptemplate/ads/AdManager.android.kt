package com.example.kmptemplate.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.MobileAds

actual class AdManager(private val context: Context) {
    actual fun initAds() = MobileAds.initialize(context) { status ->
        for ((adapter, adapterStatus) in status.adapterStatusMap) {
            Log.d("AdManager", "$adapter: ${adapterStatus.description}")
        }
    }
}
