plugins {
    id("kmptemplate.android.application")
}

val appId = providers.gradleProperty("kmptemplate.applicationId").get()

android {
    namespace = appId
    defaultConfig {
        applicationId = appId
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    // @Preview is used from src/main (MainActivity.kt), so the annotation artifact must be
    // on every variant; only the tooling runtime is debug-only.
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}