package infra

import software.amazon.awscdk.Duration
import software.amazon.awscdk.RemovalPolicy
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.appsync.*
import software.amazon.awscdk.services.dynamodb.Attribute
import software.amazon.awscdk.services.dynamodb.AttributeType
import software.amazon.awscdk.services.dynamodb.BillingMode
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.lambda.Architecture
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Runtime
import software.amazon.awscdk.services.lambda.eventsources.S3EventSource
import software.amazon.awscdk.services.logs.LogGroup
import software.amazon.awscdk.services.logs.RetentionDays
import software.amazon.awscdk.services.s3.Bucket
import software.amazon.awscdk.services.s3.EventType
import software.amazon.awscdk.services.sns.Topic
import software.amazon.awscdk.services.sns.subscriptions.EmailSubscription
import software.constructs.Construct


// Define the AWS CloudFormation stack for our serverless application
class DocsFlowStack(
    scope: Construct,
    id: String,
    props: StackProps,
) : Stack(scope, id, props) {
    init {
        // Create an S3 bucket to store documents
        val docsBucket = Bucket.Builder.create(this, "DocsBucket")
            // Policy to delete on stack destruction
            .removalPolicy(RemovalPolicy.DESTROY)
            .build()

        // Create an SNS topic
        val notificationTopic = Topic.Builder
            .create(this, "DocsNotificationTopic")
            .displayName("Document Processing Notifications")
            .build()

        // Email subscription to the SNS topic
        val email = "youremail@example.com" // TODO: Replace with your email
        notificationTopic.addSubscription(
            EmailSubscription(email)
        )

        // Define the ID for our Lambda function
        val functionId = "DocsReceiver"
        // Create the Lambda function with the specified attributes
        val function = Function.Builder.create(this, functionId)
            .description("Receives documents for processing")
            .handler("function.DocsReceiver::handleRequest")
            .runtime(Runtime.JAVA_21)
            // Link the code for Lambda from the provided directory
            .code(Code.fromAsset("../build/dist/docs-receiver.zip"))
            // Use ARM 64 architecture for better performance
            .architecture(Architecture.ARM_64)
            // Specify a separate log group for function logging
            .logGroup(
                LogGroup.Builder.create(this, "LogGroupFor$functionId")
                    // Keep logs for one week
                    .retention(RetentionDays.ONE_WEEK)
                    .build()
            )
            .memorySize(512) // Assign memory size for the function
            .timeout(Duration.seconds(120)) // Set the timeout duration
            .environment(
                mapOf(
                    "SNS_TOPIC_ARN" to notificationTopic.topicArn
                )
            )
            .build()

        // Allow the Lambda function to read from the created S3 bucket
        docsBucket.grantRead(function)
        // Grant the Lambda function permissions to publish to the SNS topic
        notificationTopic.grantPublish(function)


        // Trigger the Lambda function on S3 ObjectCreated events
        val s3EventSource = S3EventSource.Builder.create(docsBucket)
            .events(listOf(EventType.OBJECT_CREATED))
            .build()

        // Add the event source to the Lambda function
        function.addEventSource(s3EventSource)

        val tableName = "Docs"
        val docsTable = Table.Builder.create(this, tableName)
            .tableName(tableName)
            .partitionKey(
                Attribute.builder()
                    .type(AttributeType.STRING)
                    .name("id")
                    .build()
            )
            .removalPolicy(RemovalPolicy.DESTROY)
            .pointInTimeRecovery(false)
            // Setting to keep ourselves within the free tier
            .billingMode(BillingMode.PROVISIONED)
            .readCapacity(12)
            .writeCapacity(12)
            .build()

        // Grant the Lambda function permission to write to the table
        docsTable.grantWriteData(function)

        // Setup GraphQL API for document handling
        val apiName = "DocsFlowApi"
        val docsApi = GraphqlApi.Builder.create(this, apiName)
            .name(apiName)
            // Load GraphQL schema from a local file
            .definition(
                Definition.fromSchema(
                    SchemaFile.fromAsset(
                        checkNotNull(
                            this::class.java.getResource(
                                "/schemas/docs.graphql"
                            )?.path
                        )
                    )
                )
            )
            // Configure API authorization method
            .authorizationConfig(
                AuthorizationConfig.builder()
                    .defaultAuthorization(
                        AuthorizationMode.builder()
                            // Use API Key for simplicity in this tutorial
                            .authorizationType(
                                AuthorizationType.API_KEY)
                            .build()
                    ).build()
            )
            // Set log level for API to only log errors
            .logConfig(
                LogConfig.builder()
                    .fieldLogLevel(FieldLogLevel.ERROR)
                    .build()
            ).build()

        // Link DynamoDB as a data source with a resolver
        docsApi.addDynamoDbDataSource("getDocumentById", docsTable)
            .createResolver(
                "resolveById",
                BaseResolverProps.builder()
                    .typeName("Query")
                    // Resolver for 'getDocumentById' query
                    .fieldName("getDocumentById")
                    // Define how to fetch item by ID from DynamoDB
                    .requestMappingTemplate(
                        MappingTemplate.dynamoDbGetItem("id", "id")
                    )
                    // Template for the response format
                    .responseMappingTemplate(
                        MappingTemplate.dynamoDbResultItem()
                    )
                    .build()
            )

    }
}
