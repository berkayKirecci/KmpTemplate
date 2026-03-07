import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
   alias(libs.plugins.kotlinMultiplatform)
   alias(libs.plugins.androidMultiplatformLibrary)
   alias(libs.plugins.kotlin.serialization)
}

kotlin {
   android {
      namespace = "com.example.kmptemplate.core.network"
      compileSdk = libs.versions.android.compileSdk.get().toInt()
      minSdk = libs.versions.android.minSdk.get().toInt()

      compilerOptions {
         jvmTarget.set(JvmTarget.JVM_11)
      }
   }

   listOf(
      iosArm64(),
      iosSimulatorArm64()
   ).forEach { iosTarget ->
      iosTarget.binaries.framework {
         baseName = "CoreNetwork"
         isStatic = true
      }
   }

   jvm()

   @OptIn(ExperimentalKotlinGradlePluginApi::class)
   dependencies {
      // Serialization
      implementation(libs.kotlinx.serialization.json)

      // Ktor
      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)
      implementation(libs.ktor.client.logging)
   }
}