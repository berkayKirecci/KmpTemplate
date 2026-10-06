plugins {
    id("kmptemplate.kmp.feature")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network)
            implementation(projects.core.ads)
        }
    }
}
