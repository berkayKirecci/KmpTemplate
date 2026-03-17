plugins {
    id("kmptemplate.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Coroutines
            api(libs.kotlinx.coroutines.core)

            // Koin
            api(libs.koin.core)
        }
    }
}
