plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.trasladosolidarios.database"
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
    // Componentes runtime y anotaciones de Room (@Entity, @Dao, @Database)
    implementation("androidx.room:room-runtime:2.8.5")

    // Soporte para Corrutinas y Flow con Kotlin (room-ktx)
    implementation(libs.androidx.room.ktx)

    // Procesador de anotaciones para generar las clases SQLite en tiempo de compilación
    ksp("androidx.room:room-compiler:2.6.1")

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}