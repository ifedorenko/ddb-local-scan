package adhoc.dynamodb_local;

import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.HashMap;

public class DynamoDbLocalReturnValuesOnConditionCheckFailure {
    public static void main(String[] args) {
        var server = DynamoDBEmbedded.create();
        var db = server.amazonDynamoDB();

        var tableName = "test";

        db.createTable(
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(new AttributeDefinition("key", ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement("key", KeyType.HASH))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)
        );
        System.out.println("created table " + tableName);

        var item = new HashMap<String, AttributeValue>();
        item.put("key", new AttributeValue("1"));
        db.putItem(tableName, item);

        try {
            var expressionAttributeNames = new HashMap<String, String>();
            expressionAttributeNames.put("#key", "key");
            db.putItem(
                    new PutItemRequest()
                            .withTableName(tableName)
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#key)")
                            .withExpressionAttributeNames(expressionAttributeNames)
                            .withReturnValuesOnConditionCheckFailure(ReturnValuesOnConditionCheckFailure.ALL_OLD)
            );
        } catch (ConditionalCheckFailedException e) {
            if (e.getItem() != null && !e.getItem().isEmpty()) {
                System.out.println("SUCCESS putItem ReturnValuesOnConditionCheckFailure.ALL_OLD value present");
            } else {
                System.out.println("FAILURE putItem ReturnValuesOnConditionCheckFailure.ALL_OLD value NOT present");
            }
        }

        System.exit(0);
    }
}
