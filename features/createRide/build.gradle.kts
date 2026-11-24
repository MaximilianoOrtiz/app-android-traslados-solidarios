
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
        namespace = "com.trasladossolidarios.createride"
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

            // Módulos internos
            implementation(project(":core:domain"))
            implementation(project(":core:ui"))
            implementation(project(":core:data"))

            // Compose Multiplatform
            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.foundation)
            implementation(libs.compose.multiplatform.ui)
            implementation(libs.compose.multiplatform.ui.tooling.preview)
            implementation(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.material.icons.extended)
            implementation(libs.compose.multiplatform.resources)

            // Navigation 3 Runtime
            implementation(libs.androidx.navigation3.runtime)

            // Lifecycle multiplataforma
            implementation(libs.androidx.lifecycle.viewmodel)

            // Koin multiplataforma
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            // Kotlinx DateTime
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// Compose Preview para Android Studio
dependencies {
    add(
        "androidRuntimeClasspath",
        libs.compose.multiplatform.ui.tooling
    )
}
