package com.example.kmptemplate.base

/**
 * Per-environment configuration, supplied by the platform entry point.
 *
 * Shared code cannot read build configuration directly: product flavors exist only on the
 * Android application module, because AGP's KotlinMultiplatformAndroidLibraryExtension has no
 * productFlavors or buildTypes. So each platform builds its own instance - Android from
 * BuildConfig, iOS from Info.plist - and injects it inward through Koin.
 */
interface AppConfig {
    val environment: String
    val apiBaseUrl: String
    val logHttpBodies: Boolean
}
