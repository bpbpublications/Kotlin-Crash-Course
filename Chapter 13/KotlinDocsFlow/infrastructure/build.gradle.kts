plugins {
    application
}

dependencies {
    // cdk
    implementation("software.amazon.awscdk:aws-cdk-lib:2.132.0")
    // constructs
    implementation("software.constructs:constructs:10.3.0")
}

application {
    mainClass.set("infra.DocsFlowAppKt")
}

tasks.named("run") {
    dependsOn(":docs-receiver:packageDistribution")
}
