plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.trasladossolidarios.data"
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
    implementation(project(":core:network"))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    // GitLive Firebase (KMP): Firestore y Auth con API multiplataforma
    // (excepciones y Timestamp viven en estos artefactos).
    implementation(libs.gitlive.firestore)
    implementation(libs.gitlive.auth)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Koin: dataModule (repositorios + FirebaseAuth)
    implementation(libs.koin.core)
}