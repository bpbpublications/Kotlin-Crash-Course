package function

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import aws.smithy.kotlin.runtime.net.url.Url
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification
import kotlinx.coroutines.runBlocking
import org.http4k.serverless.AwsLambdaEventFunction
import org.http4k.serverless.FnHandler
import org.http4k.serverless.FnLoader
import java.util.*
import kotlin.time.Duration.Companion.minutes

private const val TABLE_NAME = "Docs"

// Handler function to process incoming S3 events
fun documentReceivedHandler() =
    FnHandler { s3Event: S3Event, context: Context ->
        runBlocking {
            val logger = context.logger
            // Loop through all records in the event
            s3Event.records.forEach {
                // Check if the event type is a new object creation
                if (it.eventName == "ObjectCreated:Put") {
                    val documentId = UUID.randomUUID().toString()
                    // Extract the document name from the event
                    val documentName = it.s3.`object`.urlDecodedKey
                    // Log the receipt of the document
                    logger.log("Received document $documentName")
                    saveToDB(
                        documentId = documentId,
                        documentName = documentName,
                        // call analyzer and pass the result to the persistence function
                        analysis = application.analyse(it.s3.`object`.urlDecodedKey),
                        s3eventRecord = it,
                    )
                    publishEventWithPresignedUrl(
                        documentId = documentId,
                        documentName = documentName,
                        s3eventRecord = it,
                    )
                }
            }
        }
    }


// Factory function to load the event handler
fun docsReceiverEventFnLoader() = FnLoader { _: Map<String, String> ->
    // Return the handler function for S3 events
    documentReceivedHandler()
}

private suspend fun createPresignedUrl(
    documentName: String,
    s3eventRecord: S3EventNotification.S3EventNotificationRecord,
): Url = S3Client.fromEnvironment().use {
    val unsignedRequest = GetObjectRequest {
        bucket = s3eventRecord.s3.bucket.name
        key = documentName
    }
    it.presignGetObject(unsignedRequest, 60.minutes).url
}

// Publishes an event with a URL to access the document
private suspend fun publishEventWithPresignedUrl(
    documentId: String,
    documentName: String,
    s3eventRecord: S3EventNotification.S3EventNotificationRecord,
) {
    // Get a pre-signed URL for the S3 document
    val preSignedUrl = createPresignedUrl(
        documentName = documentName,
        s3eventRecord = s3eventRecord,
    )

    // Create the message body with document details
    val messageBody = """Document received: $documentName
        | with id: $documentId. 
        | Access it here: $preSignedUrl""".trimMargin()

    // Setup the SNS publish request with the message and topic
    val request = PublishRequest {
        message = messageBody
        topicArn = System.getenv("SNS_TOPIC_ARN")
    }

    // Create an SNS client and publish the message
    SnsClient.fromEnvironment { }.use { snsClient ->
        snsClient.publish(request)
    }
}

// Stores analysis results in DynamoDB
private suspend fun saveToDB(
    documentId: String,
    documentName: String,
    analysis: String,
    s3eventRecord: S3EventNotification.S3EventNotificationRecord,
) {
    // Prepare item attributes for DynamoDB
    val eventTime = s3eventRecord.eventTime.toString()
    val itemValues = mapOf(
        "id" to AttributeValue.S(documentId),
        "name" to AttributeValue.S(documentName),
        "analysis" to AttributeValue.S(analysis),
        "receivedTimestamp" to AttributeValue.S(eventTime),
    )

    // Create request to put item in DynamoDB
    val putItemRequest = PutItemRequest {
        tableName = TABLE_NAME
        item = itemValues
    }

    // Use DynamoDB client to save item
    DynamoDbClient.fromEnvironment {}.use { ddb ->
        ddb.putItem(putItemRequest)
    }
}



// AWS Lambda entry class
class DocsReceiver : AwsLambdaEventFunction(docsReceiverEventFnLoader())
