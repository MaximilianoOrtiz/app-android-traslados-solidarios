
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {

    android {
        namespace = "com.trasladossolidarios.shared"
        compileSdk = 37
        minSdk = 26

        androidResources {
            enable = true
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvmToolchain(11)

    sourceSets {

        commonMain.dependencies {

            // Módulos internos: shared conoce todos los módulos como los conocía :app
            implementation(project(":core:domain"))
            implementation(project(":core:data"))
            implementation(project(":core:network"))
            implementation(project(":core:ui"))
            implementation(project(":features:auth"))
            implementation(project(":features:home"))
            implementation(project(":features:profile"))
            implementation(project(":features:myRides"))
            implementation(project(":features:openRides"))
            implementation(project(":features:createRide"))

            // Compose Multiplatform
            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.ui)
            implementation(libs.compose.multiplatform.ui.tooling.preview)
            implementation(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.material.icons.extended)
            implementation(libs.compose.multiplatform.resources)

            // Navigation 3 (runtime es multiplataforma; NavDisplay vive en androidMain)
            implementation(libs.androidx.navigation3.runtime)
            // Un ViewModelStore por pantalla del back stack
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)

            // Lifecycle multiplataforma
            implementation(libs.androidx.lifecycle.viewmodel)

            // Serialización type-safe de las rutas (@Serializable)
            implementation(libs.kotlinx.serialization.core)

            // Koin multiplataforma
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            // NavDisplay (navigation3-ui no tiene variantes iOS)
            implementation(libs.androidx.navigation3.ui)
            // androidContext() para startKoin en initApp()
            implementation(libs.koin.android)
            // locationModule (core:location es android-only)
            implementation(project(":core:location"))
            // MediaManager.init() en initApp() (misma versión que core:network)
            implementation("com.cloudinary:cloudinary-android:3.1.3")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// Tooling necesario para visualizar @Preview en Android Studio
dependencies {
    add(
        "androidRuntimeClasspath",
        libs.compose.multiplatform.ui.tooling
    )
}
