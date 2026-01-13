plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    // New Plugins (apply false)
//    alias(libs.plugins.ksp) apply false
//    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.androidLint) apply false
}
// In your TOP-LEVEL root build.gradle.kts
// In your TOP-LEVEL root build.gradle.kts
allprojects {
    configurations.all {
        if (name.contains("jvm") || name.contains("desktop")) {
            attributes {
                attribute(Attribute.of("ui", String::class.java), "desktop")
            }

            resolutionStrategy.eachDependency {
                if (requested.group == "org.jetbrains.compose.material" &&
                    requested.name.startsWith("material-icons")) {

                    // ONLY add -desktop if the name doesn't already have it
                    if (!requested.name.endsWith("-desktop")) {
                        useTarget("${requested.group}:${requested.name}-desktop:${requested.version}")
                    }
                }
            }
        }
    }
}