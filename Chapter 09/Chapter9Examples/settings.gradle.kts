rootProject.name = "Chapter9Examples"

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm").version("1.9.22")
        id("org.jetbrains.compose").version("1.6.1")
    }
}
