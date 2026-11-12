plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)

}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    // Koin (koin-core es multiplataforma): domainModule registra los use cases.
    // El dominio no conoce Android ni frameworks de UI; Koin core es puro Kotlin/JVM.
    implementation(libs.koin.core)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}


