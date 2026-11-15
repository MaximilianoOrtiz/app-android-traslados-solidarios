plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
}

// Versión de Compose Multiplatform (coordenadas org.jetbrains.compose.* directas:
// los accessors compose.* están deprecados en CMP 1.12).
val composeMultiplatformVersion = libs.versions.composeMultiplatform.get()

kotlin {
    android {
        namespace = "com.ui"
        compileSdk = 37
        minSdk = 26

        compilerOptions {
            jvmTarget.set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
            )
        }

        // Requerido para usar la clase Res (compose resources) en el target Android.
        androidResources {
            enable = true
        }

        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }
    }

    // Targets iOS
    iosArm64()
    iosSimulatorArm64()

    // Toolchain JVM
    jvmToolchain(11)

    sourceSets {

        commonMain.dependencies {
            implementation(project(":core:domain"))

            // Compose Multiplatform: coordenadas directas (CMP las resuelve a androidx en Android).
            implementation("org.jetbrains.compose.runtime:runtime:$composeMultiplatformVersion")
            implementation("org.jetbrains.compose.foundation:foundation:$composeMultiplatformVersion")
            implementation("org.jetbrains.compose.ui:ui:$composeMultiplatformVersion")
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:$composeMultiplatformVersion")
            implementation("org.jetbrains.compose.components:components-resources:$composeMultiplatformVersion")
            // Material3: la versión 1.9.0 de CMP envuelve androidx material3:1.4.0
            // (misma versión estable del proyecto) y publica variantes iOS.
            implementation("org.jetbrains.compose.material3:material3:1.9.0")
            // material-icons-extended está congelado por CMP en la versión 1.7.3.
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

            // Coil 3: carga de imágenes multiplataforma.
            implementation(libs.coil.compose)

            // LocalDate/TimeZone/MonthNames: fechas en RideCard (kotlin.time no los cubre).
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            // CameraPicker: ActivityResultContracts / FileProvider.
            implementation(libs.androidx.activity.compose)

            // Motor de red de Coil: artefacto solo Android/JVM (sin variantes iOS).
            implementation(libs.coil.network.okhttp)
        }

        getByName("androidHostTest").dependencies {
            implementation(libs.junit)
        }

        getByName("androidDeviceTest").dependencies {
            implementation(libs.androidx.junit)
            implementation(libs.androidx.espresso.core)
        }
    }
}

dependencies {
    // ui-tooling sin variantes de build (reemplaza a debugImplementation).
    "androidRuntimeClasspath"(libs.androidx.compose.ui.tooling)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.trasladossolidarios.ui.resources"
}
