package infra

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

// Entry point for the AWS CDK application
fun main() {
    // Instantiate the CDK App which holds all the stacks
    val app = App()

    // Deployment environment from system environment variables
    val environment = Environment.builder()
        .account(System.getenv("DEPLOY_TARGET_ACCOUNT")) // Account ID
        .region(System.getenv("DEPLOY_TARGET_REGION")) // Region
        .build()

    // Define the name of the CloudFormation stack
    val stackName = "KotlinDocsFlowStack"
    // DocsFlowStack instance with the given name and environment
    DocsFlowStack(
        app, stackName,
        StackProps.builder()
            .stackName(stackName) // Stack name for identification
            .env(environment) // Pass the defined environment
            .description("Kotlin Docs Flow Stack")
            .build()
    )

    // Synthesize the CloudFormation template from the app's stacks
    app.synth()
}
