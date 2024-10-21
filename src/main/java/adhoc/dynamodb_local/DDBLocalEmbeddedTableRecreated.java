package adhoc.dynamodb_local;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * Demonstrates unexpected LocalDBAccessException when using DynamoDb-local on
 * Apple MacBook Pro M3 MAX.
 */
public class DDBLocalEmbeddedTableRecreated {

    public static void main(String[] args) {
        var server = DynamoDBEmbedded.create();
        var db = server.amazonDynamoDB();
        var dbstreams = server.amazonDynamoDBStreams();

        try {
            for (int x = 0; x < 100; x++) {
                System.out.println(x);
                db.createTable(
                        new CreateTableRequest()
                                .withTableName("TEST_TABLE")
                                .withAttributeDefinitions(new AttributeDefinition("IdentifierGroup", ScalarAttributeType.S))
                                .withKeySchema(new KeySchemaElement("IdentifierGroup", KeyType.HASH))
                                .withBillingMode(BillingMode.PAY_PER_REQUEST)
                                .withStreamSpecification(
                                        new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES)
                                )
                );
                db.deleteTable("TEST_TABLE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        server.shutdownNow();
    }
}
