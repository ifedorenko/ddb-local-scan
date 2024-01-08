package adhoc.dynamodb_local_scan

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.*

fun main() {
    val server = DynamoDBEmbedded.create()
    val db = server.amazonDynamoDB()

    val tableName = "test"

    db.createTable(
            CreateTableRequest()
                    .withTableName(tableName)
                    .withAttributeDefinitions(AttributeDefinition("key", ScalarAttributeType.S))
                    .withKeySchema(KeySchemaElement("key", KeyType.HASH))
                    .withBillingMode(BillingMode.PAY_PER_REQUEST)
    )
    println("created table $tableName")

    db.putItem(tableName, mapOf("key" to AttributeValue("1")))
    db.putItem(tableName, mapOf("key" to AttributeValue("2")))
    db.putItem(tableName, mapOf("key" to AttributeValue("3")))
    println("executed putItem")

    val scanResult = db.scan(
            ScanRequest()
                    .withTableName(tableName)
                    .withSegment(0)
                    .withTotalSegments(1)
    )
    if (scanResult.count == 3) {
        println("SUCCESS executed scan, result count=${scanResult.count}")
    } else {
        println("FAILURE executed scan, result count=${scanResult.count}")
    }

    System.exit(0)
}
