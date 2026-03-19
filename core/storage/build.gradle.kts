plugins {
    id("kmptemplate.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Base
            implementation(projects.core.base)

            // Datastore
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)

            // Koin
            api(libs.koin.core)
        }
    }
}
