import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = convention(target) { libs ->
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
        apply(plugin = "com.android.kotlin.multiplatform.library")
        apply(plugin = "io.insert-koin.compiler.plugin")

        val segments = target.path.removePrefix(":").split(":")
        val namespace = "$APPLICATION_ID.${segments.joinToString(".")}"
        val frameworkName = segments.joinToString("") { it.replaceFirstChar(Char::uppercaseChar) }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(11)

            (this as ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                this.namespace = namespace
                compileSdk = libs.version("android-compileSdk")
                minSdk = libs.version("android-minSdk")
            }

            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }

            listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
                iosTarget.binaries.framework {
                    baseName = frameworkName
                    isStatic = true
                }
            }

            jvm()
        }
    }
}
