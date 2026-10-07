package com.example.kmptemplate

import com.example.kmptemplate.base.AppConfig
import platform.Foundation.NSBundle

/**
 * Reads values injected by the per-environment xcconfig through Info.plist, so the Swift side
 * needs no changes and MainViewController keeps its signature.
 */
internal object IosAppConfig : AppConfig {

    override val environment: String = infoString("AppEnvironment") ?: "dev"

    override val apiBaseUrl: String = infoString("ApiBaseUrl") ?: "https://dummyjson.com/"

    override val logHttpBodies: Boolean = infoString("LogHttpBodies")?.equals("YES", true) ?: false

    private fun infoString(key: String): String? =
        (NSBundle.mainBundle.objectForInfoDictionaryKey(key) as? String)?.takeIf { it.isNotBlank() }
}
