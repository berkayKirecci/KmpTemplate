import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = convention(target) { libs ->
        apply(plugin = "com.android.application")
        apply(plugin = "org.jetbrains.kotlin.plugin.compose")
        apply(plugin = "org.jetbrains.compose")

        val localProps = Properties().also { props ->
            val f = rootProject.file("local.properties")
            if (f.exists()) f.inputStream().use(props::load)
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.version("android-compileSdk")
            defaultConfig {
                minSdk = libs.version("android-minSdk")
                targetSdk = libs.version("android-targetSdk")
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            signingConfigs {
                val storeFilePath = localProps.getProperty("signing.storeFile")
                if (storeFilePath != null) {
                    create("release") {
                        storeFile = file(storeFilePath)
                        storePassword = localProps.getProperty("signing.storePassword")
                        keyAlias = localProps.getProperty("signing.keyAlias")
                        keyPassword = localProps.getProperty("signing.keyPassword")
                    }
                }
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    val releaseSigningConfig = signingConfigs.findByName("release")
                    if (releaseSigningConfig != null) {
                        signingConfig = releaseSigningConfig
                    }
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }

        extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
                freeCompilerArgs.add("-Xskip-prerelease-check")
            }
        }

        dependencies.add("implementation", project(":shared"))
    }
}