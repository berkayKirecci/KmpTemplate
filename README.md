# KmpTemplate

A **Kotlin Multiplatform** template targeting Android and iOS, built with **Compose Multiplatform**. This template demonstrates a clean, scalable multi-module architecture with shared business logic, UI components, and platform-specific implementations.

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Compose Multiplatform |
| DI | Koin |
| Networking | Ktor |
| Navigation | Navigation3 |
| Async | Kotlin Coroutines + Flow |
| Serialization | Kotlinx Serialization |
| Local Storage | DataStore Preferences |
| Build Logic | Gradle Convention Plugins |

---

## Project Structure

```
KmpTemplate/
├── build-logic/                  # Gradle convention plugins
│   └── convention/
│       └── src/main/kotlin/
│           ├── AndroidApplicationConventionPlugin.kt
│           ├── KmpLibraryConventionPlugin.kt
│           └── KmpFeatureConventionPlugin.kt
│
├── androidApp/                   # Android application entry point
│
├── iosApp/                       # iOS application entry point (Xcode)
│
├── shared/                       # App shell — navigation, DI wiring, DataStore
│
├── core/
│   ├── base/                     # Zero-dependency base interfaces
│   ├── network/                  # Ktor client, network helpers
│   └── designsystem/             # BaseScreen, Compose theme, shared UI
│
└── feature/
    ├── home/                     # Home feature module
    └── post/                     # Post feature module
```

---

## Module Dependency Graph

```
:core:base
     ↑
:core:network          :core:designsystem
     ↑                        ↑
          :feature:post   :feature:home
                  ↑            ↑
                    :shared
                       ↑
                  :androidApp
```

### Dependency Rules
- **`core:*` modules** never depend on `feature:*` or `shared`
- **`feature:*` modules** depend on `core:*` only, never on each other
- **`shared`** depends on all `core:*` and `feature:*` — acts as the app shell
- **`androidApp`** depends only on `shared`

---

## Module Purposes

### `build-logic`
Gradle convention plugins that eliminate boilerplate across all modules.

| Plugin | Purpose |
|---|---|
| `kmptemplate.android.application` | Applied to `androidApp` — sets up AGP, compileSdk, packaging |
| `kmptemplate.kmp.library` | Applied to all KMP library modules — sets up KMP targets, iOS framework, JVM toolchain |
| `kmptemplate.kmp.feature` | Applied to all feature modules — extends `kmp.library` with Compose, Koin, Serialization, and core deps |

### `core:base`
No dependencies. Contains the base interfaces and delegates shared by all layers:
- `BaseUiEvent` — sealed class for UI events (e.g. `ShowError`)
- `UiEventHelper` / `UiEventHelperDelegate` — shared flow for emitting UI events
- `NetworkHelper` / `NetworkHelperDelegate` — loading state + `safeCollect` for Flow error handling

### `core:network`
Depends on `core:base`. Contains all networking infrastructure:
- `NetworkClient` — Ktor HTTP client wrapper with `safeGet` / `safePost`
- `NetworkHelper` — `safeFlowRequest` for safe, typed network calls
- `NetworkException` — typed network error
- `BaseRequest` / `BaseResponse` — base models

### `core:designsystem`
Depends on `core:base`. Contains shared UI building blocks:
- `BaseScreen` — handles loading state, error events via snackbar, and lifecycle-aware collection
- Exposes Compose dependencies (`material3`, `foundation`, `runtime`, `ui`) as `api` so feature modules don't redeclare them

### `shared`
The app shell. Depends on all `core:*` and `feature:*` modules:
- `App.kt` — root composable with Koin setup
- `Navigation.kt` — Navigation3 graph wiring all feature screens
- `AppModule.kt` — Koin DI module registering all dependencies
- `DataStore` — platform-specific persistent storage

### `feature:home`
Home feature. Contains `HomeScreen` and `HomeViewmodel`.

### `feature:post`
Post feature. Full clean architecture slice:
- `ui/` — `PostScreen`, `PostViewModel`, `PostUiState`
- `domain/` — `PostRepository` interface, `GetPostsUseCase`
- `data/` — `PostRepositoryImpl`, `PostResponse`

### `androidApp`
Android entry point. Applies `kmptemplate.android.application`. Contains only `MainActivity` which hosts the `App()` composable.

### `iosApp`
iOS entry point (Xcode project). Hosts `ComposeApp.xcframework` built from `shared`.

---

## Build Commands

### Android
```shell
./gradlew :androidApp:assembleDebug
```

### iOS
Open `/iosApp/iosApp.xcodeproj` in Xcode and run.

### Desktop
```shell
./gradlew :desktopApp:run
```
