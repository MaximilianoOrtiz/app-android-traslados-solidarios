plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.trasladossolidarios.location"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(project(":core:domain"))
    // Servicios de Ubicación de Google Play Services
    implementation("com.google.android.gms:play-services-location:21.3.0")
    // Soporte para await() en tareas de Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")
    // Koin: locationModule (LocationTracker + FusedLocationProviderClient) — usa androidContext()
    implementation(libs.koin.android)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}