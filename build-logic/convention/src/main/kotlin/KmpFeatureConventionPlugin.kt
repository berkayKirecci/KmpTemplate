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
                // Core
                implementation(target.project(":core:designsystem"))
                implementation(target.project(":core:network"))

                // Serialization
                implementation(libs.findLibrary("kotlinx-serialization-json").get())

                // Koin
                implementation(libs.findLibrary("koin-compose-viewmodel").get())

                // Collections
                implementation(libs.findLibrary("kotlinx-collections-immutable").get())
            }
        }
    }
}
