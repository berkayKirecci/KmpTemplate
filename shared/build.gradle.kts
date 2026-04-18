plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xskip-prerelease-check")
    }
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
            // Core
            implementation(projects.core.network)
            implementation(projects.core.designsystem)
            implementation(projects.core.storage)
            implementation(projects.core.ads)
            implementation(projects.core.navigation)

            // Feature
            implementation(projects.feature.post)
            implementation(projects.feature.detail)

            // Koin
            implementation(libs.koin.compose)
        }
    }
}

