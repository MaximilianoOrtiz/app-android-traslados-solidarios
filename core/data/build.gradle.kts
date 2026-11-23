
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.trasladossolidarios.data"
        compileSdk = 37
        minSdk = 26

        // GitLive requiere bytecode JVM 17 en esta configuración.
        compilerOptions {
            jvmTarget.set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
            )
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {

            implementation(project(":core:domain"))
            implementation(project(":core:network"))

            // Firebase multiplataforma.
            implementation(libs.gitlive.firestore)
            implementation(libs.gitlive.auth)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            // API nativa de ubicación de Android.
            implementation(libs.play.services.location)

            // Permite utilizar await() sobre Tasks de Google Play Services.
            implementation(libs.kotlinx.coroutines.play.services)
            // Koin con acceso a androidContext().
            implementation(libs.koin.android)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
