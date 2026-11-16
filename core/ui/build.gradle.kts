plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
}

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

            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.ui)
            implementation(libs.compose.multiplatform.ui.tooling.preview)
            implementation(libs.compose.multiplatform.resources)
            implementation(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.material.icons.extended)

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
