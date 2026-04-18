plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)

            // Compose runtime
            implementation(libs.compose.runtime)

            // Navigation3
            implementation(libs.navigation3.ui)
            implementation(libs.navigation3.viewmodel)
            implementation(libs.navigation3.runtime)

            // Koin
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.navigation3)
        }
    }
}
