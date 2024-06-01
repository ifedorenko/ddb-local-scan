package adhoc.dynamodb_local;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * Demonstrates unexpected NullPointerException when using DynamoDb-local 2.5.0.
 * Works as expected with DynamoDb-local 2.2.1
 */
public class DDBLocalEmbeddedBillingMode {
    public static void main(String[] args) {
        var server = DynamoDBEmbedded.create();
        var db = server.amazonDynamoDB();

        System.out.println("createTable SHARED.jobs");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.jobs")
                        .withAttributeDefinitions(new AttributeDefinition("hk", ScalarAttributeType.B), new AttributeDefinition("sk", ScalarAttributeType.B), new AttributeDefinition("outstanding_tasks_hk", ScalarAttributeType.B), new AttributeDefinition("execute_after", ScalarAttributeType.B))
                        .withKeySchema(new KeySchemaElement("hk", KeyType.HASH), new KeySchemaElement("sk", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)
                        .withGlobalSecondaryIndexes(new GlobalSecondaryIndex()
                                .withIndexName("outstanding_tasks")
                                .withKeySchema(new KeySchemaElement("outstanding_tasks_hk", KeyType.HASH), new KeySchemaElement("execute_after", KeyType.RANGE))
                                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)))
        );

        System.out.println("createTable testtable");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("testtable")
                        .withAttributeDefinitions(new AttributeDefinition("hk", ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement("hk", KeyType.HASH))
                        .withBillingMode(BillingMode.PROVISIONED)
                        .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L))
        );

        server.shutdownNow();
    }
}
