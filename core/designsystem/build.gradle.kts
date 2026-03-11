plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Base
            api(projects.core.base)

            // Compose
            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.ui)
            api(libs.compose.components.resources)

            // Lifecycle
            api(libs.androidx.lifecycle.runtimeCompose)

            // Snackbar
            implementation(libs.crossmessages)
        }
    }
}
