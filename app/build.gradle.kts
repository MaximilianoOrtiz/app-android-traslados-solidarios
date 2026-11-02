plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    // Genera el código de serialización de las rutas de navegación type-safe (@Serializable)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.trasladossolidarios"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.trasladossolidarios"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))
    implementation(project(":core:location"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":features:auth"))
    implementation(project(":features:createRide"))
    implementation(project(":features:home"))
    implementation(project(":features:myRides"))
    implementation(project(":features:openRides"))
    implementation(project(":features:profile"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Navigation 3: el back stack es una lista que manejamos nosotros
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    // Un ViewModelStore por pantalla del back stack (cada entrada tiene su propio ViewModel)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.play.services.cast)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // hiltViewModel(): el puente entre el grafo de Hilt y los ViewModel de Compose
    implementation(libs.androidx.hilt.navigation.compose)


    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation("com.cloudinary:cloudinary-android:2.5.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    //para no tener que indicar cada version de FireBase
    implementation(platform("com.google.firebase:firebase-bom:34.19.0"))
    //libreria de autenticación
    implementation("com.google.firebase:firebase-auth")
    //para usar funciones asincronas (await)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.11.0")
}