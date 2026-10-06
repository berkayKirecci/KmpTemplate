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

        // HttpClient {} resolves its engine at runtime via HttpClientEngineContainer, so
        // every target needs an engine artifact on the classpath. These belong with the
        // module that builds the client, not with the consuming app.
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}