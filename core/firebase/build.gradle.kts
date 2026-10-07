@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)

plugins {
    id("kmptemplate.kmp.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    swiftPMDependencies {
        iosMinimumDeploymentTarget.set("16.0")

        // Firebase pulls in C++ transitive modules (gRPC, abseil, leveldb, BoringSSL) that
        // fail cinterop generation, so discovery is off and the needed modules are listed.
        discoverClangModulesImplicitly.set(false)

        // All Firebase products must come from a single package declaration: they share
        // transitive dependencies, and splitting them causes duplicate-symbol dyld crashes.
        swiftPackage(
            url = url("https://github.com/firebase/firebase-ios-sdk.git"),
            version = from(libs.versions.firebase.ios.get()),
            products = listOf(
                product("FirebaseCore"),
                product("FirebaseAnalytics"),
                product("FirebaseAuth"),
                product("FirebaseFirestore"),
                product("FirebaseCrashlytics"),
            ),
            importedClangModules = listOf(
                "FirebaseCore",
                "FirebaseAnalytics",
                "FirebaseAuth",
                "FirebaseCrashlytics",
                // Firestore ships as a binary xcframework whose ObjC Clang module is named
                // FirebaseFirestoreInternal, not FirebaseFirestore.
                "FirebaseFirestoreInternal",
            ),
        )
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
            implementation(libs.firebase.crashlytics)
        }
    }
}
