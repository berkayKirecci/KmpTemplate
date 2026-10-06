@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)

plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    swiftPMDependencies {
        iosMinimumDeploymentTarget.set("16.0")

        swiftPackage(
            url = url("https://github.com/googleads/swift-package-manager-google-mobile-ads.git"),
            version = from(libs.versions.google.mobile.ads.ios.get()),
            products = listOf(product("GoogleMobileAds")),
        )
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.services.ads)
        }

        commonMain.dependencies {
            // Core Base
            implementation(projects.core.base)

            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
    }
}
