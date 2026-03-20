plugins {
    id("kmptemplate.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Coroutines
            api(libs.kotlinx.coroutines.core)

            // Serialization
            api(libs.kotlinx.serialization.json)

            // Koin
            api(libs.koin.core)
        }
    }
}
