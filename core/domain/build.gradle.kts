
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    android {
        namespace = "com.trasladossolidarios.core.domain"
        compileSdk = 37
        minSdk = 26
    }

    // Targets iOS
    iosArm64()
    iosSimulatorArm64()

    // Toolchain JVM
    jvmToolchain(11)

    sourceSets {

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
