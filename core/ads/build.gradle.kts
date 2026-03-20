plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "CoreAds"
            isStatic = true
        }
        pod("Google-Mobile-Ads-SDK") {
            version = "~> 13.1"
            moduleName = "GoogleMobileAds"
            packageName = "cocoapods.GoogleMobileAds"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.services.ads)
        }

        commonMain.dependencies {
            // Core Base
            implementation(projects.core.base)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
    }
}