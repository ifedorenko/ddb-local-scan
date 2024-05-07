package adhoc.gsi

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.*

const val BATCH_WRITE_MAX_SIZE = 25

fun AmazonDynamoDB.emptyTable(tableName: String) {
    val keyAttributes = this.describeTable(tableName).table.keySchema.map({ it.attributeName })
    var lastEvaluatedKey: Map<String, AttributeValue>? = null
    do {
        val result = this.scan(
            ScanRequest(tableName)
                .withLimit(BATCH_WRITE_MAX_SIZE)
                .withAttributesToGet(keyAttributes)
                .withExclusiveStartKey(lastEvaluatedKey)
        )
        this.deleteItems(tableName, result.items)
        lastEvaluatedKey = result.lastEvaluatedKey
    } while (lastEvaluatedKey != null)
}

fun AmazonDynamoDB.deleteItems(tableName: String, items: List<Map<String, AttributeValue>>) {
    if (items.isEmpty()) {
        return
    }

    require(items.size <= BATCH_WRITE_MAX_SIZE)

    val writeRequests = items.map {
        WriteRequest(DeleteRequest().withKey(it))
    }

    var res = this.batchWriteItem(BatchWriteItemRequest().withRequestItems(mapOf(tableName to writeRequests)))
    while (!res.unprocessedItems.isNullOrEmpty()) {
        res = this.batchWriteItem(res.unprocessedItems)
    }
}
