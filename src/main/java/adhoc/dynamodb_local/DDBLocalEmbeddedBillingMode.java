package adhoc.dynamodb_local;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * Demonstrates unexpected NullPointerException when using DynamoDb-local 2.5.1.
 * Works as expected with DynamoDb-local 2.2.1
 */
public class DDBLocalEmbeddedBillingMode {
    public static void main(String[] args) {
        var server = DynamoDBEmbedded.create();
        var db = server.amazonDynamoDB();

        System.out.println("createTable SHARED.data.clouds.c2.0.shared.Objects");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.data.clouds.c2.0.shared.Objects")
                        .withAttributeDefinitions(new AttributeDefinition("0", ScalarAttributeType.B), new AttributeDefinition("1", ScalarAttributeType.B), new AttributeDefinition("4", ScalarAttributeType.B), new AttributeDefinition("5", ScalarAttributeType.B), new AttributeDefinition("2", ScalarAttributeType.B), new AttributeDefinition("3", ScalarAttributeType.B), new AttributeDefinition("2A", ScalarAttributeType.B), new AttributeDefinition("3A", ScalarAttributeType.B))
                        .withKeySchema(new KeySchemaElement("0", KeyType.HASH), new KeySchemaElement("1", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)
                        .withGlobalSecondaryIndexes(new GlobalSecondaryIndex()
                                .withIndexName("idx_0_1")
                                .withKeySchema(new KeySchemaElement("4", KeyType.HASH), new KeySchemaElement("5", KeyType.RANGE))
                                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)), new GlobalSecondaryIndex()
                                .withIndexName("idx_0_0")
                                .withKeySchema(new KeySchemaElement("2", KeyType.HASH), new KeySchemaElement("3", KeyType.RANGE))
                                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)), new GlobalSecondaryIndex()
                                .withIndexName("idx_0_2")
                                .withKeySchema(new KeySchemaElement("2A", KeyType.HASH), new KeySchemaElement("3A", KeyType.RANGE))
                                .withProjection(new Projection().withProjectionType(ProjectionType.ALL)))
                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

        System.out.println("updateTable SHARED.data.clouds.c2.0.shared.Objects");
        db.updateTable(
                new UpdateTableRequest()
                        .withTableName("SHARED.data.clouds.c2.0.shared.Objects")
                        .withBillingMode(BillingMode.PROVISIONED)
                        .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(20000L).withWriteCapacityUnits(20000L))
                        .withGlobalSecondaryIndexUpdates(
                                new GlobalSecondaryIndexUpdate().withUpdate(
                                        new UpdateGlobalSecondaryIndexAction()
                                                .withIndexName("idx_0_0")
                                                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(20000L).withWriteCapacityUnits(20000L))),
                                new GlobalSecondaryIndexUpdate().withUpdate(
                                        new UpdateGlobalSecondaryIndexAction()
                                                .withIndexName("idx_0_2")
                                                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(20000L).withWriteCapacityUnits(20000L))),
                                new GlobalSecondaryIndexUpdate().withUpdate(
                                        new UpdateGlobalSecondaryIndexAction()
                                                .withIndexName("idx_0_1")
                                                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(20000L).withWriteCapacityUnits(20000L)))
                        )
        );

        server.shutdownNow();
    }
}
