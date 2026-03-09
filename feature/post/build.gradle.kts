plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Modules
            implementation(projects.core.designsystem)
            implementation(projects.core.network)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Koin
            implementation(libs.koin.compose.viewmodel)

            // Collections
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}
