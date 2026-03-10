plugins {
    id("kmptemplate.kmp.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }
    }
}
