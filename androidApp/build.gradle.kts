plugins {
    id("kmptemplate.android.application")
}

android {
    namespace = "com.example.kmptemplate"
    defaultConfig {
        applicationId = "com.example.kmptemplate"
        versionCode = 1
        versionName = "1.0"
    }
}

kotlin {
    dependencies {
        implementation(projects.shared)
        implementation(libs.androidx.activity.compose)
        implementation(libs.compose.uiToolingPreview)
        implementation(libs.ktor.client.okhttp)
    }
}