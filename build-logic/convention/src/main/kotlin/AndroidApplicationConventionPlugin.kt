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

        // Read through providers so the file is an explicitly declared configuration
        // input rather than relying on Gradle instrumenting the raw file read.
        val localProps = Properties().also { props ->
            providers
                .fileContents(rootProject.layout.projectDirectory.file("local.properties"))
                .asText.orNull
                ?.let { props.load(it.reader()) }
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

            // buildConfigField needs this; AGP 9 defaults the feature off.
            buildFeatures {
                buildConfig = true
            }

            // Product flavors only exist here. KotlinMultiplatformAndroidLibraryExtension,
            // which every core/feature/shared module uses, has no productFlavors or
            // buildTypes - so per-environment values are read here and injected into shared
            // code as an AppConfig.
            flavorDimensions += "environment"
            productFlavors {
                create("dev") {
                    dimension = "environment"
                    applicationIdSuffix = ".dev"
                    versionNameSuffix = "-dev"
                    buildConfigField("String", "ENVIRONMENT", "\"dev\"")
                    buildConfigField("String", "API_BASE_URL", "\"https://dummyjson.com/\"")
                    buildConfigField("boolean", "LOG_HTTP_BODIES", "true")
                }
                create("staging") {
                    dimension = "environment"
                    applicationIdSuffix = ".staging"
                    versionNameSuffix = "-staging"
                    buildConfigField("String", "ENVIRONMENT", "\"staging\"")
                    buildConfigField("String", "API_BASE_URL", "\"https://dummyjson.com/\"")
                    buildConfigField("boolean", "LOG_HTTP_BODIES", "true")
                }
                create("prod") {
                    dimension = "environment"
                    buildConfigField("String", "ENVIRONMENT", "\"prod\"")
                    buildConfigField("String", "API_BASE_URL", "\"https://dummyjson.com/\"")
                    // Never log request/response bodies in production.
                    buildConfigField("boolean", "LOG_HTTP_BODIES", "false")
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
            }
        }

        dependencies.add("implementation", project(":shared"))
    }
}