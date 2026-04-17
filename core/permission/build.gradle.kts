plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            implementation(libs.permissions.notifications)
            implementation(libs.permissions.compose)
            implementation(libs.permissions)
        }
    }
}