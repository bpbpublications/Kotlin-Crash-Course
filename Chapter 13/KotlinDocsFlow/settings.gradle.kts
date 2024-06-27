// Configure Gradle's plugin management
pluginManagement {
    // Define the repositories to fetch plugins from
    repositories {
        mavenCentral() // Use Maven Central for community plugins
        gradlePluginPortal() // Include Gradle's official plugin portal
    }
}

// Set the name of the root project
rootProject.name = "KotlinDocsFlow"

// Include and set up the FaaS module
include(":docs-receiver") // Include the docs-receiver module
// Define the location of the docs-receiver module
project(":docs-receiver").projectDir = file("software/docs-receiver")

// Include and set up the IaC module
include(":infrastructure") // Include the infrastructure module
// Define the location of the infrastructure module
project(":infrastructure").projectDir = file("infrastructure")
