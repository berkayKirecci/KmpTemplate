# KmpTemplate

A **Kotlin Multiplatform** template targeting **Android**, **iOS**, **Desktop** (JVM), and **Web** (JS / Wasm), built with **Compose Multiplatform**. This template demonstrates a clean, scalable multi-module architecture with shared business logic, UI components, and platform-specific implementations.

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Compose Multiplatform |
| DI | Koin (with compiler plugin) |
| Networking | Ktor |
| Navigation | Navigation3 |
| Async | Kotlin Coroutines + Flow |
| Serialization | Kotlinx Serialization |
| Local Storage | DataStore Preferences |
| Ads | Google Mobile Ads (Android + iOS via CocoaPods) |
| Snackbar / Messages | CrossMessages |
| Hot Reload | Compose Hot Reload |
| Build Logic | Gradle Convention Plugins |

---

## Platforms

| Platform | Entry Point | Run Command |
|---|---|---|
| Android | `:androidApp` | `./gradlew :androidApp:assembleDebug` |
| iOS | `iosApp/` (Xcode) | Open `iosApp.xcworkspace` in Xcode and run |
| Desktop | `:desktopApp` | `./gradlew :desktopApp:run` |
| Web | `:webApp` | `./gradlew :webApp:jsBrowserRun` or `./gradlew :webApp:wasmJsBrowserRun` |

---

## Project Structure

```
KmpTemplate/
├── build-logic/                  # Gradle convention plugins
│   └── convention/
│       └── src/main/kotlin/
│           ├── AndroidApplicationConventionPlugin.kt
│           ├── KmpLibraryConventionPlugin.kt
│           ├── KmpFeatureConventionPlugin.kt
│           └── Extension.kt
│
├── androidApp/                   # Android application entry point
├── iosApp/                       # iOS application entry point (Xcode + CocoaPods)
├── desktopApp/                   # Desktop (JVM) application entry point
├── webApp/                       # Web (JS / Wasm) application entry point
│
├── shared/                       # App shell — navigation, DI wiring, platform glue
│
├── core/
│   ├── base/                     # Zero-dependency base interfaces & helpers
│   ├── network/                  # Ktor client, network helpers
│   ├── designsystem/             # BaseScreen, Compose theme, shared UI
│   ├── storage/                  # DataStore Preferences (platform-specific)
│   └── ads/                      # Banner & Interstitial ad wrappers (Android/iOS/JVM)
│
└── feature/
    ├── home/                     # Home feature module
    └── post/                     # Post feature module
```

---

## Module Dependency Graph

```
                  :core:base
                 ↗    ↑    ↖
     :core:network  :core:storage  :core:designsystem  :core:ads
          ↑              ↑                ↑                ↑
          └──────┬───────┴────────────────┴────────────────┘
                 │
          :feature:post   :feature:home
                  ↑            ↑
                    :shared
                   ↗   ↑   ↖
         :androidApp :desktopApp :webApp
```

### Dependency Rules
- **`core:*` modules** depend only on `core:base` — never on `feature:*` or `shared`
- **`feature:*` modules** depend on `core:*` only, never on each other
- **`shared`** depends on all `core:*` and `feature:*` — acts as the app shell
- **Platform apps** (`androidApp`, `desktopApp`, `webApp`) depend only on `shared`
- **`iosApp`** consumes `shared` via the compiled `ComposeApp.xcframework`

---

## Module Purposes

### `build-logic`
Gradle convention plugins that eliminate boilerplate across all modules.

| Plugin | Purpose |
|---|---|
| `kmptemplate.android.application` | Applied to `androidApp` — sets up AGP, compileSdk, packaging, Compose |
| `kmptemplate.kmp.library` | Applied to all KMP library modules — sets up KMP targets (Android, iOS, JVM), Koin compiler plugin, JVM toolchain |
| `kmptemplate.kmp.feature` | Applied to feature modules — extends `kmp.library` with Compose, Koin, Serialization, Navigation3, and core deps |

### `core:base`
No dependencies. Contains the base interfaces and delegates shared by all layers:
- `BaseUiEvent` — sealed class for UI events (e.g. `ShowError`)
- `UiEventHelper` / `UiEventHelperDelegate` — shared flow for emitting UI events
- `NetworkHelper` / `NetworkHelperDelegate` — loading state + `safeCollect` for Flow error handling
- `NavRouteSerializer` — serialization helper for navigation routes

Exposes `kotlinx-coroutines-core`, `kotlinx-serialization-json`, and `koin-core` as `api`.

### `core:network`
Depends on `core:base`. Contains all networking infrastructure:
- `NetworkClient` — Ktor HTTP client wrapper with safe request methods
- `NetworkHelper` — `safeFlowRequest` for safe, typed network calls
- `BaseRequest` / `BaseResponse` — base models
- `NetworkModule` — Koin DI module

### `core:designsystem`
Depends on `core:base`. Contains shared UI building blocks:
- `BaseScreen` — handles loading state, error events via snackbar (CrossMessages), and lifecycle-aware collection
- Exposes Compose dependencies (`material3`, `foundation`, `runtime`, `ui`, `lifecycle-runtime-compose`) as `api` so feature modules don't redeclare them

### `core:storage`
Depends on `core:base`. Provides DataStore Preferences with platform-specific implementations:
- `DataStore.kt` — expect/actual for Android, iOS, and JVM
- `StorageModule` — Koin DI module

### `core:ads`
Depends on `core:base`. Provides cross-platform ad wrappers:
- `BannerAd` / `InterstitialAd` — composable wrappers with expect/actual per platform
- `AdManager` — ad lifecycle management (expect/actual for Android, iOS, JVM)
- `AdConstants` — ad unit IDs per platform
- `AdModule` — Koin DI module
- Android: Google Play Services Ads
- iOS: Google Mobile Ads SDK via CocoaPods

### `shared`
The app shell. Depends on all `core:*` and `feature:*` modules:
- `App.kt` — root composable with Koin setup
- `Navigation.kt` — Navigation3 graph wiring all feature screens
- `AppModule.kt` — Koin DI module registering all dependencies
- Platform-specific entry points (`MainViewController.kt` for iOS)

### `feature:home`
Home feature module. Contains:
- `ui/` — `HomeScreen`, `HomeViewmodel`, `HomeRoute`
- `di/` — `HomeModule` (Koin)

### `feature:post`
Post feature module. Full clean architecture slice:
- `ui/` — `PostScreen`, `PostViewModel`, `PostUiState`, `PostRoute`
- `domain/` — `PostRepository` interface, `GetPostsUseCase`, `Post` model
- `data/` — `PostRepositoryImpl`, `PostResponse`, `PostListResponse`
- `di/` — `PostModule` (Koin)

### `androidApp`
Android entry point. Applies `kmptemplate.android.application`. Contains only `MainActivity` which hosts the `App()` composable.

### `desktopApp`
Desktop (JVM) entry point. Uses `compose.desktop.currentOs` and runs the `App()` composable in a desktop window.

### `webApp`
Web entry point. Supports both **Kotlin/JS** and **Kotlin/Wasm** browser targets. Renders the `App()` composable in the browser.

### `iosApp`
iOS entry point (Xcode project). Hosts `ComposeApp.xcframework` built from `shared`. Uses CocoaPods for Google Mobile Ads SDK integration.

---

## Key Versions

| Dependency | Version |
|---|---|
| Kotlin | 2.3.20 |
| Compose Multiplatform | 1.10.2 |
| AGP | 9.1.0 |
| Ktor | 3.4.1 |
| Koin | 4.2.0 |
| Navigation3 | 1.0.0-alpha06 / 1.0.1 |
| DataStore | 1.2.1 |
| Kotlinx Serialization | 1.10.0 |
| Kotlinx Coroutines | 1.10.2 |

---

## Build Commands

### Android
```shell
./gradlew :androidApp:assembleDebug
```

### iOS
Open `iosApp/iosApp.xcworkspace` in Xcode and run.

### Desktop
```shell
./gradlew :desktopApp:run
```

### Web (JS)
```shell
./gradlew :webApp:jsBrowserRun
```

### Web (Wasm)
```shell
./gradlew :webApp:wasmJsBrowserRun
```
