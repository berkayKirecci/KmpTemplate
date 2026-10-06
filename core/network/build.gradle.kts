plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core Base
            api(projects.core.base)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
        }

        // HttpClient {} resolves its engine at runtime via HttpClientEngineContainer, so each
        // target needs an engine artifact on the classpath. Android gets OkHttp from androidApp.
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}