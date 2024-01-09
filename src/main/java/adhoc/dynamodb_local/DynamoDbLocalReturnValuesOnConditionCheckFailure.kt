package adhoc.dynamodb_local

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

    val item = mapOf("key" to AttributeValue("1"))
    db.putItem(tableName, item)

    try {
        db.putItem(
                PutItemRequest()
                        .withTableName(tableName)
                        .withItem(item)
                        .withConditionExpression("attribute_not_exists(#key)")
                        .withExpressionAttributeNames(mapOf("#key" to "key"))
                        .withReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure.ALL_OLD)
        )
    } catch(e: ConditionalCheckFailedException) {
        if (e.item.isNullOrEmpty()) {
            println("FAILURE")
        } else {
            println("SUCCESS")
        }
    }

    System.exit(0)
}
