# KmpTemplate

A **Kotlin Multiplatform** template targeting **Android** and **iOS**, built with **Compose
Multiplatform**. It demonstrates a multi-module architecture with shared business logic, shared
UI, and platform-specific implementations behind `expect`/`actual`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Compose Multiplatform |
| DI | Koin (with compiler plugin) |
| Networking | Ktor (OkHttp on Android, Darwin on iOS) |
| Navigation | Navigation3 |
| Async | Kotlin Coroutines + Flow |
| Serialization | Kotlinx Serialization |
| Local Storage | DataStore Preferences |
| Ads | Google Mobile Ads (Play Services on Android, SwiftPM on iOS) |
| Snackbar / Messages | CrossMessages |
| Logging | Own `expect`/`actual` wrapper in `core:base` |
| Crash reporting | Firebase Crashlytics (opt-in, see below) |
| iOS native deps | SwiftPM import (`swiftPMDependencies`) — no CocoaPods |
| Build Logic | Gradle convention plugins in `build-logic` |

---

## Platforms

| Platform | Entry point | Run |
|---|---|---|
| Android | `:androidApp` | `./gradlew :androidApp:assembleDebug` |
| iOS | `iosApp/iosApp.xcodeproj` | Open in Xcode and run |

Minimums: Android `minSdk 24` (compile 37 / target 36), iOS **16.0**.

> Desktop (JVM) and Web (JS/Wasm) targets were removed from this template. There is no `jvm()`
> target, so `expect`/`actual` declarations cover Android and iOS only.

---

## Getting started from this template

1. **Set your application id.** One value in `gradle.properties` drives every module's Android
   namespace, the Gradle group, the `swiftPMImport` cinterop package and the app id:

   ```properties
   kmptemplate.applicationId=com.example.kmptemplate
   ```

2. **Set your AdMob ids before publishing.** The template ships Google's demo ad unit ids, which
   always serve test ads and cannot generate invalid traffic. Replace all four before release:

   | Where | What |
   |---|---|
   | `core/ads/src/androidMain/.../AdConstants.android.kt` | banner + interstitial unit ids |
   | `core/ads/src/iosMain/.../AdConstants.ios.kt` | banner + interstitial unit ids |
   | `androidApp/src/main/AndroidManifest.xml` | `com.google.android.gms.ads.APPLICATION_ID` |
   | `iosApp/iosApp/Info.plist` | `GADApplicationIdentifier` |

   The app id and the unit ids must belong to the same AdMob publisher, or ads fail to serve.

3. **iOS native dependencies** resolve through SwiftPM on Gradle sync. If Xcode reports a missing
   package, run `./gradlew :core:ads:integrateLinkagePackage` and reopen the project.

---

## Project Structure

```
KmpTemplate/
├── build-logic/                  # Gradle convention plugins (included build)
│   └── convention/src/main/kotlin/
│       ├── AndroidApplicationConventionPlugin.kt
│       ├── KmpLibraryConventionPlugin.kt
│       ├── KmpFeatureConventionPlugin.kt
│       └── Extension.kt
│
├── androidApp/                   # Android entry point (MainActivity)
├── iosApp/                       # Xcode project; embeds Shared.framework
│   └── KotlinMultiplatformLinkedPackage/   # generated SwiftPM linkage package
│
├── shared/                       # App shell — App(), Koin wiring, iOS entry point
│
├── core/
│   ├── base/                     # Dependency-free interfaces & delegates
│   ├── network/                  # Ktor client and request helpers
│   ├── designsystem/             # BaseScreen, theme, shared Compose UI
│   ├── storage/                  # DataStore Preferences (Android/iOS)
│   ├── navigation/               # Navigation3 graph, Navigator, routes
│   ├── ads/                      # Banner & interstitial ads
│   ├── platform/                 # Share sheet, store review prompt
│   ├── firebase/                 # Analytics, Auth, Firestore, Crashlytics
│   └── permission/               # Runtime permissions via moko  (opt-in)
│
└── feature/
    ├── post/                     # Full clean-architecture slice
    └── detail/                   # Minimal feature example
```

---

## Module Dependency Graph

```
:androidApp ──► :shared
iosApp      ──► Shared.framework, built from :shared

:shared ──► :core:network  :core:designsystem  :core:storage
            :core:ads      :core:platform      :core:navigation
            :core:firebase
            :feature:post  :feature:detail

:feature:post    ──► :core:base, :core:designsystem, :core:navigation   (via convention plugin)
                 ──► :core:network, :core:ads                           (declared by the module)
:feature:detail  ──► :core:base, :core:designsystem, :core:navigation   (via convention plugin)

every :core:* ──► :core:base
```

### Dependency Rules

- **`core:*`** depends only on `core:base` — never on `feature:*` or `shared`.
- **`feature:*`** depends on `core:*` only, never on another feature.
- The **feature convention plugin** supplies only what every feature needs
  (`core:base`, `core:designsystem`, `core:navigation`). A feature that needs networking or ads
  declares `core:network` / `core:ads` itself, so no feature links the ads SDK just by existing.
- **`shared`** depends on the `core:*` and `feature:*` modules it wires together — it is the app
  shell, not a library.
- **`androidApp`** depends only on `shared` (added by the convention plugin).
- **`iosApp`** consumes `Shared.framework` through the `:shared:embedAndSignAppleFrameworkForXcode`
  build phase, plus a generated local Swift package that links the SwiftPM dependencies.

### Not wired into the app

`core:permission` is **opt-in scaffolding**: it compiles and is built, but no module depends on it
and it is not registered in `appModule`. Add a dependency when you need it.

`core:firebase` *is* wired in, but is inert until you supply Firebase config. See
[Crash reporting](#crash-reporting).

---

## Module Purposes

### `build-logic`

| Plugin | Purpose |
|---|---|
| `kmptemplate.android.application` | `androidApp` — AGP setup, SDK levels, packaging, Compose, release signing from `local.properties` |
| `kmptemplate.kmp.library` | All KMP modules — Android + `iosArm64` + `iosSimulatorArm64` targets, static frameworks, Koin compiler plugin, JVM toolchain, namespace/group derived from the Gradle path |
| `kmptemplate.kmp.feature` | Feature modules — extends `kmp.library` with Compose, Serialization, Koin, Navigation3 and the universal core modules |

### `core:base`
No module dependencies. Base interfaces and delegates shared by all layers:
- `BaseUiEvent` — sealed UI events (e.g. `ShowError`)
- `UiEventHelper` / `UiEventHelperDelegate` — shared flow for emitting UI events
- `NetworkHelper` / `NetworkHelperDelegate` — loading state plus `safeCollect` / `safeCall`
- `AppConfig` — per-environment values, supplied by the platform entry point
- `AppError` / `AppErrorAware` — error categories the UI localizes
- `Log` / `LogSink` — multiplatform logger with pluggable extra destinations

Exposes `kotlinx-coroutines-core`, `kotlinx-serialization-json` and `koin-core` as `api`.

### `core:network`
- `NetworkClient` — Ktor client wrapper
- `SafeRequest.kt` — `NetworkException`, `safeRequest`, `safeFlowRequest`
- `BaseRequest` / `BaseResponse` — base models
- `NetworkModule` — Koin module
- Engines live here: OkHttp for Android, Darwin for iOS

### `core:designsystem`
- `BaseScreen` — loading state, error events via CrossMessages snackbar, lifecycle-aware collection
- Theme and shared composables
- Exposes Compose (`runtime`, `foundation`, `material3`, `ui`, icons, resources) and
  `lifecycle-runtime-compose` as `api` so features need not redeclare them

### `core:navigation`
- `Navigation` — Navigation3 graph
- `Navigator` / `NavigatorState` — navigation commands and back stack
- `Routes`, `NavRouteSerializer` — typed routes and their serialization
- `NavigationModule` — Koin module

### `core:storage`
- `DataStore` — `expect`/`actual` Preferences storage for Android and iOS
- `StorageModule` — Koin module

### `core:ads`
- `BannerAd` / `InterstitialAd` — composable wrappers
- `AdManager`, `AdConstants`, `AdModule`
- Android: Play Services Ads · iOS: Google Mobile Ads via SwiftPM

### `core:platform`
OS integration that is not advertising, kept separate so reaching it does not pull in the ads SDK:
- `AppShareManager` — system share sheet
- `ReviewManager` — in-app store review prompt

### `core:firebase`
`Analytics`, `FirebaseAuth`, `Firestore`, `CrashReporter` with `expect`/`actual` per platform, and
`FirebaseModule`. iOS bindings come from the Firebase SwiftPM package; Firestore's Objective-C
Clang module is `FirebaseFirestoreInternal`, which is why the module lists `importedClangModules`
explicitly.

### `core:permission` *(opt-in)*
`PermissionManager` wrapping moko-permissions for runtime permission requests.

### `shared`
The app shell:
- `App.kt` — root composable, Koin setup
- `di/AppModule.kt` — aggregates every feature and core Koin module
- `iosMain/MainViewController.kt` — iOS entry point

### `feature:post`
Full clean-architecture slice: `ui/` (`PostScreen`, `PostViewModel`, `PostUiState`), `domain/`
(`PostRepository`, `GetPostsUseCase`, `Post`), `data/` (`PostRepositoryImpl`, responses), `di/`.

### `feature:detail`
Minimal feature showing the smallest viable slice: `ui/` (`DetailScreen`, `DetailViewModel`), `di/`.

### `androidApp`
Applies `kmptemplate.android.application`. Contains `MainActivity`, which hosts `App()`.

### `iosApp`
Xcode project. A "Compile Kotlin Framework" build phase runs
`:shared:embedAndSignAppleFrameworkForXcode`, and `KotlinMultiplatformLinkedPackage` links the
SwiftPM dependencies into the app.

---

## Build environments

Three environments, selected per platform. Product flavors only exist on `androidApp` because
AGP's `KotlinMultiplatformAndroidLibraryExtension` supports neither `productFlavors` nor
`buildTypes`, so each platform builds an `AppConfig` (`core:base`) and injects it through Koin.

| | Android | iOS |
|---|---|---|
| Selection | product flavor `dev` / `staging` / `prod` | build configuration `Debug` / `Staging` / `Release` |
| Source of values | `buildConfigField` in `AndroidApplicationConventionPlugin` | `iosApp/Configuration/{Dev,Staging,Prod}.xcconfig` |
| Reaches Kotlin via | `BuildConfig` → `AndroidAppConfig` | `Info.plist` → `IosAppConfig` (`NSBundle`) |
| App id | `.dev` / `.staging` suffix | `PRODUCT_BUNDLE_IDENTIFIER` suffix |

```shell
./gradlew :androidApp:assembleDevDebug
./gradlew :androidApp:assembleProdRelease
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -configuration Staging build
```

`logHttpBodies` is true for dev and staging and **false for prod**, so release builds never write
request or response payloads to the device log.

Two xcconfig gotchas worth knowing before editing those files:

- `//` starts a comment, so a literal `https://` must be escaped as `https:/$()/`.
- Kotlin's `embedAndSign` infers debug/release from the configuration *name*, so any
  configuration not named exactly `Debug` or `Release` must set `KOTLIN_FRAMEWORK_BUILD_TYPE`.

---

## Crash reporting

`core:firebase` provides a `CrashReporter`, and `App()` registers it as a `LogSink` so `Log.w` and
`Log.e` become crash breadcrumbs.

**It is inert until you add your own Firebase config**, and the build stays green without it:

- The `google-services` and `firebase-crashlytics` Gradle plugins are applied **only** when
  `androidApp/google-services.json` exists. Without it the build logs that it skipped them.
- The iOS dSYM upload build phase exits early unless `iosApp/iosApp/GoogleService-Info.plist`
  exists.
- `CrashReporter.isAvailable` is false and every method is a no-op when Firebase did not
  initialise.

To enable it: drop `google-services.json` into `androidApp/`, drop `GoogleService-Info.plist` into
`iosApp/iosApp/`, call `FirebaseApp.configure()` from `iOSApp.swift`, then run
`./gradlew :core:firebase:integrateLinkagePackage` and re-resolve packages in Xcode.

---

## Key Versions

| Dependency | Version |
|---|---|
| Kotlin | 2.4.20 |
| Compose Multiplatform | 1.12.1 |
| Compose Material3 | 1.12.0-alpha03 |
| AGP | 9.3.3 |
| Gradle | 9.5.1 |
| Ktor | 3.6.0 |
| Koin | 4.2.2 (compiler plugin 1.2.1) |
| Navigation3 | ui 1.1.2 / runtime 1.2.0 |
| Lifecycle | 2.11.0 |
| DataStore | 1.2.1 |
| Kotlinx Serialization | 1.11.0 |
| Kotlinx Coroutines | 1.11.0 |
| Firebase iOS (SwiftPM) | 12.6.0 |
| Google Mobile Ads iOS (SwiftPM) | 13.11.0 |

AGP and Gradle are coupled: AGP 9.3.x requires Gradle 9.5.0+, and AGP 9.4.x would require 9.6.0+.
Bump them together.

---

## Build Commands

```shell
# Everything — Android and iOS, debug and release
./gradlew build

# Android only
./gradlew :androidApp:assembleDebug

# iOS framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# iOS app
open iosApp/iosApp.xcodeproj     # then run from Xcode
```

> On machines with limited RAM, a cold `./gradlew build` links many Kotlin/Native release
> frameworks at once and can exhaust the Kotlin daemon heap. If you hit
> `java.lang.OutOfMemoryError: Java heap space` during `linkReleaseFramework*`, use
> `./gradlew build --max-workers=2`, or raise `kotlin.daemon.jvmargs` in `gradle.properties`.

---

## Not included

This is a structural template, not a finished app. It currently has **no tests**, no CI workflow,
no static analysis or formatting configuration, and no dependency-update automation. Add them to
suit your project.

Crash reporting is wired but inert until you supply Firebase config — see
[Crash reporting](#crash-reporting).
