
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    android {
        namespace = "com.trasladossolidarios.location"
        compileSdk = 37
        minSdk = 26

        compilerOptions {
            jvmTarget.set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
            )
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvmToolchain(11)

    sourceSets {
        commonMain.dependencies {
            // Contratos y modelos de ubicación.
            implementation(project(":core:domain"))
        }

        androidMain.dependencies {
            // API nativa de ubicación de Android.
            implementation("com.google.android.gms:play-services-location:21.3.0")

            // Permite utilizar await() sobre Tasks de Google Play Services.
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")

            // Koin con acceso a androidContext().
            implementation(libs.koin.android)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
