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
    // Solo las ANOTACIONES de inyección (@Inject, @Qualifier), no una librería de DI.
    // El dominio declara "necesito un Repository" sin saber quién se lo va a dar
    // ni con qué framework: por eso acá no hay Hilt, ni Android.
    // Es `api` para que los módulos que lo consumen vean las anotaciones.
    api(libs.javax.inject)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}

