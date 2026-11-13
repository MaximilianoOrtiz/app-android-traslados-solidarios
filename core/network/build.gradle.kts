
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.trasladossolidarios.network"
        compileSdk = 37
        minSdk = 26

        // GitLive Firebase requiere bytecode JVM 17 en tu configuración.
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
        // Código y dependencias compartidas entre Android e iOS.
        commonMain.dependencies {
            implementation(project(":core:domain"))

            implementation(libs.gitlive.firestore)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.koin.core)
        }

        // Implementaciones y dependencias exclusivas de Android.
        androidMain.dependencies {
            implementation("com.cloudinary:cloudinary-android:3.1.3")

            implementation(libs.koin.android)

            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
            implementation(libs.material)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
