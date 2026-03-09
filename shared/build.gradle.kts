plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin)
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
            // Core
            implementation(projects.core.network)
            implementation(projects.core.designsystem)

            // Feature
            implementation(projects.feature.post)
            implementation(projects.feature.home)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Navigation3
            implementation(libs.navigation3.ui)
            implementation(libs.navigation3.viewmodel)

            // Koin
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.koin.annotations)

            // Datastore
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
