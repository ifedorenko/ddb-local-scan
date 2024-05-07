package adhoc.gsi

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.model.*
import com.google.common.base.Stopwatch
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

// attempts to catch ddb secondary index sort-key update in progress,
// i.e. either the item is present in the index with both old and new
// index sk value or not present at all
//
// 2024-05-07 gsi index secondary key appears to be atomic
//            317 updateCount=8606 queryCount=44905/45070 0

fun main() {
    val db = AmazonDynamoDBClientBuilder.standard().build()
    val tableName = "ifedorenko.adhoc-gsi1.2024-05-06"

//    createTable(db, tableName)
//    db.emptyTable(tableName)

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

    val updateCount = AtomicLong(0)
    val queryCount1 = AtomicLong(0)
    val queryCount2 = AtomicLong(0)
    val exceptionCount = AtomicLong(0)


    for (threadNo in 0..<10) thread(name = "Query thread $threadNo") {
        val request = QueryRequest(tableName)
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
        while (true) {
            try {
                val items = db.query(request).items
                if (items.size != 1) {
                    println("Gotcha! ${db.query(request).items.size}")
                } else {
                    when (items.single()["idx_sk"]!!.s) {
                        "1" -> queryCount1.incrementAndGet()
                        "2" -> queryCount2.incrementAndGet()
                        else -> exceptionCount.incrementAndGet()
                    }
//                    println("ok")
                }
            } catch (e: Exception) {
                exceptionCount.incrementAndGet()
                println("$threadNo ${e.message}")
            }
//            sleep(1000)
        }
    }

    val stopwatch = Stopwatch.createStarted()
    var lastReport = stopwatch.elapsed()
    while (true) {
        db.putItem(
            tableName,
            mapOf(
                "hk" to AttributeValue("1"),
                "sk" to AttributeValue("1"),
                "idx_hk" to AttributeValue("1"),
                "idx_sk" to AttributeValue("2"),
            ),
        )
        updateCount.incrementAndGet()
        db.putItem(
            tableName,
            mapOf(
                "hk" to AttributeValue("1"),
                "sk" to AttributeValue("1"),
                "idx_hk" to AttributeValue("1"),
                "idx_sk" to AttributeValue("1"),
            ),
        )
        updateCount.incrementAndGet()
        val elapsed = stopwatch.elapsed()
        if ((elapsed - lastReport).seconds >= 5) {
            println("${elapsed.seconds} updateCount=$updateCount queryCount=$queryCount1/$queryCount2 $exceptionCount")
            lastReport = elapsed
        }
    }
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

