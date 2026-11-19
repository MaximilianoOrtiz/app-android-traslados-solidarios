# Plan: crear módulo `:shared` (navegación + AppRoot/AppShell)

## Objetivo
Crear el módulo KMP `:shared` que reciba toda la navegación que hoy vive en `:app` (más `AppRoot`, `AppShell` y el arranque de DI). `shared` conocerá todos los módulos como hoy los conoce `app`; `app` queda solo como punto de entrada de Android dependiendo exclusivamente de `:shared` + libs mínimas.

## Decisiones validadas con el usuario
1. **home/profile**: ya migrados a KMP manualmente por el usuario — **no se tocan** (verificados: build KMP + fuentes en `commonMain` ✓).
2. **Koin + MediaManager.init**: se mudan a `shared/androidMain` (`initApp(context)`); `app` solo la llama.
3. **`:core:database`**: se quita de `app` y no se agrega a `shared` (sin uso real).
4. iOS: no se implementa; donde haga falta plataformas, `iosMain` recibe placeholders mínimos (mismo criterio que createRide).

## Hallazgos técnicos (research completado)
- **`androidx.navigation3:navigation3-ui` NO tiene variantes iOS en ninguna versión** (1.2.0, 1.2.1, 1.3.0-alpha04: solo android/jvmStubs/linuxx64Stubs) → `NavDisplay` se envuelve en un `expect/actual NavDisplayHost` (androidMain real, iosMain placeholder).
- `navigation3-runtime` (iOS ✓ empírico con createRide) y `lifecycle-viewmodel-navigation3:2.12.0-alpha04` (iOS ✓ verificado en su `.module`) sí resuelven en iOS → `entryProvider`, decorators y `rememberViewModelStoreNavEntryDecorator` van al `commonMain`.
- `ApMobileTP2Theme` está en `core:ui` **commonMain**; `AuthViewModel`/`SessionViewModel`/`LoginScrennRoute` están en `features:auth` **commonMain** → `AppRoot` es common-ready.
- Adaptaciones necesarias: `TopLevelDestination` (`@StringRes`+`com.trasladossolidarios.R` → `StringResource`+`Res` de composeResources) y `AppShell` (`androidx.compose.ui.res.stringResource` → `org.jetbrains.compose.ui.res.stringResource`).
- Paquete Res de shared: `apmobiletp2.shared.generated.resources` (rootProject `ApMobileTP2`).
- Los artefactos CMP material-icons mantienen el paquete `androidx.compose.material.icons.*` → imports de iconos no cambian.
- cloudinary: `core:network` androidMain ya usa `3.1.3` (app declara `2.5.0`, hoy se resuelve a 3.1.3) → `shared` declara `3.1.3` y app lo elimina.

## Estructura final

```
:shared (nuevo — KMP: android + iosArm64 + iosSimulatorArm64, namespace com.trasladossolidarios.shared)
├── build.gradle.kts
└── src/
    ├── commonMain/kotlin/com/trasladossolidarios/
    │   ├── navigation/   Destinations.kt, NavigationState.kt, TopLevelDestination.kt*, AppNavDisplay.kt*, NavDisplayHost.kt (expect)
    │   ├── ui/           AppRoot.kt, AppShell.kt*
    │   └── shared/       TrasladosApp.kt (nuevo)
    ├── commonMain/composeResources/values/strings.xml (nuevo: tab_*)
    ├── androidMain/kotlin/com/trasladossolidarios/
    │   ├── navigation/   NavDisplayHost.android.kt (actual → NavDisplay)
    │   └── shared/       AppInit.kt (nuevo: initApp(context))
    └── iosMain/kotlin/com/trasladossolidarios/navigation/NavDisplayHost.ios.kt (actual → placeholder)
```
`*` = archivo con cambios al moverse. Los archivos conservan su package original (sin cambios de import en los consumidores internos).

## Fases (cada una termina compilando)

### Fase 1 — Esqueleto del módulo
1. `settings.gradle.kts`: agregar `include(":shared")`.
2. Crear `shared/build.gradle.kts` (template de `features/home`/`features/auth`):
   - plugins: `kotlin.multiplatform`, `android.kotlin.multiplatform.library`, `kotlin.compose`, `compose.multiplatform`, `kotlin.serialization`.
   - `kotlin { android { namespace = "com.trasladossolidarios.shared"; compileSdk = 37; minSdk = 26; androidResources { enable = true }; compilerOptions { jvmTarget JVM_11 } ; iosArm64(); iosSimulatorArm64(); jvmToolchain(11) }`.
   - `commonMain.dependencies`: `:core:domain`, `:core:data`, `:core:network`, `:core:ui`, `:features:auth`, `:features:home`, `:features:profile`, `:features:myRides`, `:features:openRides`, `:features:createRide`; `libs.androidx.navigation3.runtime`; `libs.androidx.lifecycle.viewmodel.navigation3`; `libs.androidx.lifecycle.viewmodel`; `libs.kotlinx.serialization.core`; `libs.koin.core`; `libs.koin.compose.viewmodel`; CMP `runtime/foundation/ui/ui.tooling.preview/material3/material.icons.extended/resources`.
   - `androidMain.dependencies`: `libs.androidx.navigation3.ui`; `libs.koin.android`; `project(":core:location")` (por `locationModule`); `com.cloudinary:cloudinary-android:3.1.3`.
   - `commonTest.dependencies`: `kotlin("test")` + bloque `androidRuntimeClasspath` tooling igual que home.
   - **Sin** `:core:database`.
3. Checkpoint: `.\gradlew.bat :shared:compileAndroidMain`.

### Fase 2 — Mover navegación + UI (con adaptaciones)
Mover de `app/src/main/java/com/trasladossolidarios/...` a `shared/src/commonMain/kotlin/com/trasladossolidarios/...` y borrar los originales:

| Archivo | Cambios |
|---|---|
| `navigation/Destinations.kt` | ninguno (NavKey runtime + @Serializable ✓) |
| `navigation/NavigationState.kt` | ninguno (compose saveable + nav3 runtime ✓) |
| `navigation/TopLevelDestination.kt` | quitar `@StringRes` y `import com.trasladossolidarios.R`; `labelRes: Int` → `label: StringResource` (import `apmobiletp2.shared.generated.resources.*`); valores `R.string.tab_*` → `Res.string.tab_*`; borrar bloque comentado muerto |
| `navigation/AppNavDisplay.kt` | quitar `import androidx.navigation3.ui.NavDisplay`; `NavDisplay(...)` → `NavDisplayHost(...)`; resto idéntico |
| `ui/AppRoot.kt` | ninguno |
| `ui/AppShell.kt` | `androidx.compose.ui.res.stringResource` → `org.jetbrains.compose.ui.res.stringResource`; `stringResource(tab.labelRes)` → `stringResource(tab.label)` |

Archivos nuevos:
- `commonMain/.../navigation/NavDisplayHost.kt`: `@Composable expect fun NavDisplayHost(entries: List<NavEntry<NavKey>>, onBack: () -> Unit, modifier: Modifier)` (firma replica la llamada actual compilada — `onBack = navigationState::goBack`; ajustar si el compilador pide otro tipo).
- `androidMain/.../navigation/NavDisplayHost.android.kt`: `actual` = `NavDisplay(entries, onBack, modifier)` (import `androidx.navigation3.ui.NavDisplay`).
- `iosMain/.../navigation/NavDisplayHost.ios.kt`: `actual` = `Box(modifier)` con comentario "iOS pendiente".
- `commonMain/.../shared/TrasladosApp.kt`: `ApMobileTP2Theme { Surface(fillMaxSize, background) { koinViewModel<AuthViewModel>() + koinViewModel<SessionViewModel>(); AppRoot(...) } }` (lógica que hoy está en MainActivity).
- `androidMain/.../shared/AppInit.kt`: `fun initApp(context: Context)` = `startKoin { androidContext(context); modules(domainModule, dataModule, networkModule, locationModule, authViewModelModule, homeViewModelModule, myRidesViewModelModule, openRidesViewModelModule, profileViewModelModule, createRideViewModelModule, networkModuleAndroid) }` + `MediaManager.init(context, mapOf("cloud_name" to "jymfy8jm"))` (misma lista de módulos que la Application actual).
- `commonMain/composeResources/values/strings.xml`: mover `tab_inicio`, `tab_disponibles`, `tab_mis_traslados`, `tab_perfil` desde `app/src/main/res/values/strings.xml`.
- Checkpoint: `.\gradlew.bat :shared:compileAndroidMain :shared:compileKotlinIosSimulatorArm64`.

### Fase 3 — `app` solo entry point
1. `app/build.gradle.kts`:
   - plugins: quitar `kotlin.serialization` (quedan `android.application`, `kotlin.compose`, google-services).
   - deps: solo `implementation(project(":shared"))`, `libs.androidx.activity.compose`, `libs.androidx.core.ktx` (FileProvider) y el bloque de test actual tal cual. Eliminar: BOM, los 10 `project(...)`, material3/icons/ui/ui-graphics/ui-tooling-preview, core-ktx lifecycle/nav3 ×2/lifecycle-viewmodel-navigation3, serialization, play.services.cast, koin ×2, cloudinary 2.5.0, lifecycle-viewmodel-compose 2.8.7.
2. `MainActivity.kt`: `enableEdgeToEdge(); setContent { TrasladosApp() }`; borrar `Greeting`/`GreetingPreview` y todos los imports que sobren (theme, Surface, koinViewModel, AppRoot).
3. `ComposeTrasladosSolidariosApp.kt`: `onCreate { super.onCreate(); initApp(this@ComposeTrasladosSolidariosApp) }` (import `com.trasladossolidarios.shared.initApp`); eliminar imports de koin/cloudinary/features.
4. `AndroidManifest.xml`: **sin cambios** (Application, tema XML, FileProvider, permisos).
5. `res/values/strings.xml`: quitar los 4 `tab_*` (ya en shared). Grep `R\.string` sobre `app/src/main/java` → si no queda ninguna referencia, dejar solo `app_name` (borrar placeholders/error solo si están sin usar).
6. Checkpoint: `.\gradlew.bat assembleDebug`.

### Fase 4 — Verificación completa
```
.\gradlew.bat :shared:compileAndroidMain
.\gradlew.bat :shared:compileKotlinIosSimulatorArm64
.\gradlew.bat compileKotlinIosSimulatorArm64     (raíz, todos los módulos)
.\gradlew.bat assembleDebug
.\gradlew.bat test
```
Greps (deben dar 0):
- `com\.trasladossolidarios\.R|R\.string` en `shared/src`
- `androidx\.compose\.ui\.res` en `shared/src`
- `androidx\.navigation3\.ui` en `shared/src/commonMain`
- `com\.cloudinary|androidx\.annotation` en `shared/src/commonMain`
- `navigation3|koin|cloudinary|import com\.trasladossolidarios\.(features|core)` en `app/src/main/java` (solo debe quedar `...shared.TrasladosApp` / `...shared.initApp`)
- `R\.string\.tab_` en todo el repo

## Riesgos / mitigaciones
- **NavDisplayHost**: firma exacta de `NavDisplay` (nav3 1.2.0) se confirma al compilar; se ajusta el `expect` según el error (hoy la llamada real compila con `onBack = navigationState::goBack`).
- **home/profile (del usuario)**: si `:shared:compileKotlinIosSimulatorArm64` fallara por alguna dependencia de esas features (p. ej. `collectAsStateWithLifecycle`/`lifecycle-runtime-compose` sin variantes iOS), se informa al usuario y se propone el fix mínimo (`collectAsState`, patrón de AppRoot) **sin** re-migrar nada.
- Warnings preexistentes conocidos (TabRow/menuAnchor deprecados, RideCard, RideMapper, RideRepositoryImpl) se ignoran — no bloquean.
- Sin commits (solo si el usuario lo pide).
