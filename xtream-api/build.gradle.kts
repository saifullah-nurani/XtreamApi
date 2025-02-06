import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "xtream-api"
            isStatic = true
        }
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.json)
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
        }
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
    }
}

android {
    namespace = "io.github.saifullah.xtream"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions{
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

mavenPublishing {
    coordinates("io.github.saifullah-nurani", "xtream-api", "0.1.0")
    pom {
        name.set("XtreamApi")
        description.set("XtreamApi is a Kotlin library designed to interact with Xtream Codes API, allowing seamless retrieval of movies, series, and live TV streams.")
        url.set("https://github.com/saifullah-nurani/XtreamApi")
        inceptionYear.set("2025")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/saifullah-nurani/XtreamApi/blob/main/LICENSE")
            }
        }
        developers {
            developer {
                id.set("saifullah-nurani")
                name.set("Saifullah Nurani")
                email.set("donaldperryman04@gmail.com")
                url.set("https://github.com/saifullah-nurani")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/saifullah-nurani/XtreamApi.git")
            developerConnection.set("scm:git:https://github.com/saifullah-nurani/XtreamApi.git")
            url.set("https://github.com/saifullah-nurani/XtreamApi")
        }
    }
    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    // Enable GPG signing for all publications
    signAllPublications()
}
