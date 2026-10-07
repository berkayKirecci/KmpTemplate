import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = convention(target) { libs ->
        apply(plugin = "kmptemplate.kmp.library")
        apply(plugin = "org.jetbrains.compose")
        apply(plugin = "org.jetbrains.kotlin.plugin.compose")
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.getByName("commonMain").dependencies {
                // Core — only what every feature genuinely needs. A feature that wants
                // core:network or core:ads declares it itself, so no feature links the
                // ads SDK or the HTTP stack just by existing.
                implementation(target.project(":core:base"))
                implementation(target.project(":core:designsystem"))
                implementation(target.project(":core:navigation"))

                // Serialization
                implementation(libs.library("kotlinx-serialization-json"))

                // Koin
                implementation(libs.library("koin-compose"))
                implementation(libs.library("koin-compose-viewmodel"))
                implementation(libs.library("koin-compose-navigation3"))

                // Navigation3
                implementation(libs.library("navigation3-runtime"))

                // Collections
                implementation(libs.library("kotlinx-collections-immutable"))
            }
        }
    }
}
