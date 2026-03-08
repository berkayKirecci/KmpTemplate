import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    dependencies {
        // Core Base
        api(projects.core.base)

        // Serialization
        implementation(libs.kotlinx.serialization.json)

        // Ktor
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.ktor.client.logging)
    }
}