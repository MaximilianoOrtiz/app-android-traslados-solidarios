plugins {
    alias(libs.plugins.android.library)
    // Serializa los DTO de Firestore (@Serializable) con kotlinx-serialization.
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.trasladossolidarios.network"
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
        // GitLive Firebase (dev.gitlive:*) está compilado con bytecode JVM 17;
        // sus funciones inline (data<T>(), set(), ...) exigen target >= 17 aquí.
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {
    implementation("com.cloudinary:cloudinary-android:3.1.3")
    implementation(project(":core:domain"))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    // GitLive Firebase (KMP): API multiplataforma de Firestore (suspend + Flow)
    // en lugar del SDK de Android; en Android se apoya en el SDK oficial por debajo.
    implementation(libs.gitlive.firestore)
    implementation(libs.kotlinx.serialization.json)
    //implementation(libs.play.services.cast.tv)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Koin: networkModule (Firestore, Cloudinary, datasources) — usa androidContext()
    implementation(libs.koin.android)
}