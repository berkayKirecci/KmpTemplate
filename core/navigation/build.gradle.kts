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

            // Declared explicitly so the Compose runtime matches the Compose plugin version.
            // Without this the module has no direct Compose dependency and resolves it
            // transitively from navigation3-ui, which pins an older Compose.
            implementation(libs.compose.runtime)
            implementation(libs.compose.animation)
            implementation(libs.compose.ui)

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
