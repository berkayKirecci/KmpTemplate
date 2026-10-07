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
│   ├── firebase/                 # Analytics, Auth, Firestore  (opt-in)
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

`core:firebase` and `core:permission` are **opt-in scaffolding**. They compile and are included in
the build, but no module depends on them and they are not registered in `appModule`. Add a
dependency and register their Koin modules when you need them. `core:firebase` additionally needs
`GoogleService-Info.plist` plus `FirebaseApp.configure()` on iOS, and
`./gradlew :core:firebase:integrateLinkagePackage` so Xcode links the Firebase SwiftPM products.

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

### `core:firebase` *(opt-in)*
`Analytics`, `FirebaseAuth`, `Firestore` with `expect`/`actual` per platform, and `FirebaseModule`.
iOS bindings come from the Firebase SwiftPM package; Firestore's Objective-C Clang module is
`FirebaseFirestoreInternal`, which is why the module lists `importedClangModules` explicitly.

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
and no static analysis or formatting configuration. Add them to suit your project.
