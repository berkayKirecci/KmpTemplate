plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Core — api because App(appConfig: AppConfig) exposes a core:base type in its
            // signature, so consumers must see it.
            api(projects.core.base)
            implementation(projects.core.network)
            implementation(projects.core.designsystem)
            implementation(projects.core.storage)
            implementation(projects.core.ads)
            implementation(projects.core.platform)
            implementation(projects.core.firebase)
            implementation(projects.core.navigation)

            // Feature
            implementation(projects.feature.post)
            implementation(projects.feature.detail)

            // Koin
            implementation(libs.koin.compose)
        }
    }
}

