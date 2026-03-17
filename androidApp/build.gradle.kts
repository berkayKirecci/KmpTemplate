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

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.ktor.client.okhttp)
    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.compose.uiToolingPreview)
}