plugins {
    id("kmptemplate.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Base
            api(projects.core.base)

            // Datastore
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }
    }
}
