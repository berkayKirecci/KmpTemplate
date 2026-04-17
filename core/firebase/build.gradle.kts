plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "CoreFirebase"
            isStatic = true
        }
        // FirebaseFirestoreInternal exposes the ObjC FIR* classes for Kotlin/Native cinterop.
        // FirebaseFirestore (Swift wrapper) is still installed via the iosApp Podfile.
        pod("FirebaseFirestoreInternal") {
            version = "~> 11.6"
        }
        pod("FirebaseCore") {
            version = "~> 11.6"
        }
        pod("FirebaseAnalytics") {
            version = "~> 11.6"
        }
        pod("FirebaseAuth") {
            version = "~> 11.6"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
        }
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.auth)
        }
    }
}
