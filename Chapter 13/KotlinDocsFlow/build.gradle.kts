plugins {
    // Sets the Kotlin plugin.
    kotlin("jvm") version "1.9.22"
}

allprojects {
    // Applies the Kotlin JVM plugin to all subprojects.
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        // Specifies the use of JUnit for testing.
        useJUnitPlatform()
        testLogging {
            // Sets the logging format for test exceptions.
            setExceptionFormat("full")
        }
    }

    kotlin {
        // Specifies the JVM toolchain version.
        jvmToolchain(21)
    }
}
