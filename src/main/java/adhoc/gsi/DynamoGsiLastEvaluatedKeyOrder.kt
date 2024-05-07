package adhoc.gsi

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.*

fun main() {
    val db = AmazonDynamoDBClientBuilder.standard().build()
    val tableName = "ifedorenko.adhoc-gsi1.2024-05-06"

//    createTable(db, tableName)
    db.emptyTable(tableName)

    // four items with the same secondary index key, but different primary hash keys
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("1"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("2"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("3"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("4"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("5"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("6"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("7"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )
    db.putItem(
        tableName,
        mapOf(
            "hk" to AttributeValue("8"),
            "sk" to AttributeValue("1"),
            "idx_hk" to AttributeValue("1"),
            "idx_sk" to AttributeValue("1"),
        ),
    )


    var lastEvaluatedKey: Map<String, AttributeValue>? = null
    do {
        val response = db.query(
            QueryRequest(tableName)
                .withIndexName("idx")
                .withKeyConditionExpression(
                    "#idx_hk = :idx_hk"
                )
                .withExpressionAttributeNames(
                    mapOf("#idx_hk" to "idx_hk")
                )
                .withExpressionAttributeValues(
                    mapOf(":idx_hk" to AttributeValue().withS("1"))
                )
                .withExclusiveStartKey(lastEvaluatedKey)
                .withLimit(1)
        )
        lastEvaluatedKey = response.lastEvaluatedKey
        for (item in response.items) {
            println(item["hk"]!!.s)
        }
    } while (lastEvaluatedKey != null)


    System.exit(0)
}

private fun createTable(db: AmazonDynamoDB, tableName: String) {
    db.createTable(
        CreateTableRequest()
            .withTableName(tableName)
            .withAttributeDefinitions(
                AttributeDefinition("hk", ScalarAttributeType.S),
                AttributeDefinition("sk", ScalarAttributeType.S),
                AttributeDefinition("idx_hk", ScalarAttributeType.S),
                AttributeDefinition("idx_sk", ScalarAttributeType.S),
            )
            .withKeySchema(
                KeySchemaElement("hk", KeyType.HASH),
                KeySchemaElement("sk", KeyType.RANGE),
            )
            .withGlobalSecondaryIndexes(
                GlobalSecondaryIndex().withIndexName("idx")
                    .withKeySchema(
                        KeySchemaElement("idx_hk", KeyType.HASH),
                        KeySchemaElement("idx_sk", KeyType.RANGE),
                    )
                    .withProjection(
                        Projection().withProjectionType(ProjectionType.ALL),
                    )
            )
            .withBillingMode(BillingMode.PAY_PER_REQUEST)
    )
    println("created table $tableName")
}
