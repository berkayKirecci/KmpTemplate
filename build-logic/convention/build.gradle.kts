plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.serializationGradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kmptemplate.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("kmpLibrary") {
            id = "kmptemplate.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("kmpFeature") {
            id = "kmptemplate.kmp.feature"
            implementationClass = "KmpFeatureConventionPlugin"
        }
    }
}