rootProject.name = "Presencify"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0" // Updated to a stable version
}

include(":composeApp")
include(":feature:admin-auth")
include(":feature:student-auth")
include(":feature:teacher-auth")
include(":feature:attendance")
include(":feature:schedule")
include(":feature:onboarding")
include(":feature:academics")
include(":feature:users")
include(":feature:admin-mgt")
include(":core:data")
include(":core:domain")
include(":core:design-systems")
include(":core:presentation")
