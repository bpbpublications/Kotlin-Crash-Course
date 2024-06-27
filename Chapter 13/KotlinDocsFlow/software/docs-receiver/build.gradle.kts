// Define the project's dependencies
dependencies {
    // http4k BOM (Bill of Materials) for consistent library versions
    implementation(platform("org.http4k:http4k-bom:5.14.0.0"))
    // SLF4J BOM for consistent logging framework versions
    implementation(platform("org.slf4j:slf4j-bom:2.0.12"))
    // Use AWS Kotlin SDK BOM for consistent SDK versions
    implementation(platform("aws.sdk.kotlin:bom:1.0.74"))
    implementation("aws.sdk.kotlin:s3-jvm")
    implementation("aws.sdk.kotlin:sns-jvm")
    implementation("aws.sdk.kotlin:dynamodb-jvm")
    // Include http4k library for building serverless applications
    implementation("org.http4k:http4k-serverless-lambda")
    // Include AWS events library to handle AWS Lambda events
    implementation("com.amazonaws:aws-lambda-java-events:3.11.4")
    // SLF4J API for logging within the application
    implementation("org.slf4j:slf4j-api")
    // SLF4J simple binder for simple logging implementations
    implementation("org.slf4j:slf4j-simple")
}

// Register a Gradle task for packaging the application
tasks.register<Zip>("packageDistribution") {
    // Include compiled Kotlin classes in the package
    from(tasks.compileKotlin)
    // Include compiled Java classes (if any) in the package
    from(tasks.compileJava)
    // Include processed resources like config files in the package
    from(tasks.processResources)
    // Place all runtime dependencies into a 'lib' folder inside the zip
    into("lib") {
        from(configurations.runtimeClasspath)
    }
    // Set the name of the output zip file
    archiveFileName.set("docs-receiver.zip")
    // Specify the directory where the zip file will be created
    destinationDirectory.set(file("${project.rootDir}/build/dist"))
    // Ensure the zip task depends on the project being built first
    dependsOn(":docs-receiver:build")
}

