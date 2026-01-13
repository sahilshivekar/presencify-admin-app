import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // New Plugins
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)
    alias(libs.plugins.kotlinSerialization)
}

// FIX 1: Pass the path as a String, not a File object
//room {
//    schemaDirectory("$projectDir/schemas")
//}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    // This creates 'jvmMain' and 'jvmTest' source sets
    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            // --- Android Specific ---
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.compose.material.icons.extended)

            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)
        }

        commonMain.dependencies {
            implementation(project(":core:data"))
            implementation(project(":core:domain"))
            implementation(project(":core:design-systems"))
            implementation(project(":core:presentation"))


            // UI
            implementation("org.jetbrains.compose.runtime:runtime:1.10.0")
            implementation("org.jetbrains.compose.foundation:foundation:1.10.0")
            implementation("org.jetbrains.compose.material3:material3:1.9.0")
            implementation("org.jetbrains.compose.material:material:1.10.0")
            implementation("org.jetbrains.compose.ui:ui:1.10.0")
            implementation("org.jetbrains.compose.components:components-resources:1.10.0")
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.0")


            // Lifecycle & Navigation
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)

            // Core
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.collections.immutable)

            // DI (Koin)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Networking (Ktor)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Database (Room) & Storage
//            implementation(libs.room.runtime)
//            implementation(libs.sqlite.bundled)
            implementation(libs.datastore.preferences)

            // Image Loading (Coil)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)

            // Testing Libraries
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(libs.koin.test)

            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation("org.jetbrains.compose.ui:ui-test:1.10.0")
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.cio)
            implementation(libs.compose.material.icons.core.desktop)
            implementation(libs.compose.material.icons.extended.desktop)
        }

        // FIX 2: Changed 'desktopTest' to 'jvmTest' to match the 'jvm()' target defined above
        jvmTest.dependencies {
            implementation("org.jetbrains.compose.ui:ui-test-junit4:1.10.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)
        }
    }
}

// Add Room KSP dependencies for each target
dependencies {
    // Note: KSP dependencies are usually added at the top-level dependencies block
    // targeting specific source sets using "ksp<Target>"
//    add("kspCommonMainMetadata", libs.room.compiler)
//    add("kspAndroid", libs.room.compiler)
//    add("kspJvm", libs.room.compiler)
//    add("kspIosArm64", libs.room.compiler)
//    add("kspIosSimulatorArm64", libs.room.compiler)

    debugImplementation(compose.uiTooling)

    // Android UI Testing
    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    debugImplementation(libs.androidx.ui.test.manifest)
}

android {
    namespace = "edu.watumull.presencify"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "edu.watumull.presencify"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "edu.watumull.presencify.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "edu.watumull.presencify"
            packageVersion = "1.0.0"
        }
    }
}