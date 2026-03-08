import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    dependencies {
        // Core
        implementation(projects.core.network)

        // Compose
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.compose.material3)
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
        implementation(libs.compose.uiToolingPreview)

        // Serialization
        implementation(libs.kotlinx.serialization.json)

        // Lifecycle
        implementation(libs.androidx.lifecycle.viewmodelCompose)
        implementation(libs.androidx.lifecycle.runtimeCompose)

        // Navigation3
        implementation(libs.navigation3.ui)
        implementation(libs.navigation3.viewmodel)

        // Koin
        implementation(platform(libs.koin.bom))
        implementation(libs.koin.compose)
        implementation(libs.koin.compose.viewmodel)
        implementation(libs.koin.compose.viewmodel.navigation)
        implementation(libs.koin.annotations)

        // Datastore
        implementation(libs.androidx.datastore)
        implementation(libs.androidx.datastore.preferences)

        // Collections
        implementation(libs.kotlinx.collections.immutable)

        // Snackbar
        implementation(libs.crossmessages)
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
