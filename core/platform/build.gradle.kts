plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.play.review)
        }

        commonMain.dependencies {
            // Core Base
            implementation(projects.core.base)

            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
    }
}
