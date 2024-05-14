package adhoc.dynamodb_local;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.common.collect.ImmutableMap;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Demonstrates unexpected InternalFailure when using DynamoDbLocal 2.4.0 (latest as of 2024-05-14).
 * Works as expected with DynamoDbLocal 2.2.1.
 *
 * Steps to reproduce the problem
 *
 * 1. Download and unzip DynamoDbLocal as per https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html
 * 2. Start DynamoDbLocal (adjust JAVA_HOME to match your local environment)
 *
 *      export JAVA_HOME=/opt/java17
 *      export PATH=$JAVA_HOME/bin:$PATH
 *      java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -port 8000 -inMemory
 *
 * 3. Execute this java class
 */
public class DDBLocalSQLiteException {
    public static void main(String[] args) {
        var db = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", null)
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("dummyAccessKey", "secretkey1234567890")
                        )
                )
                .build();
        test(db);
    }

    static AttributeValue newAttributeValueB(byte... bytes) {
        return new AttributeValue().withB(ByteBuffer.wrap(bytes));
    }

    static void test(AmazonDynamoDB db) {

// describeTable {TableName: SHARED.scopes.Counters}
//System.out.println("describeTable SHARED.scopes.Counters");
//db.describeTable("SHARED.scopes.Counters");

// createTable {AttributeDefinitions: [{AttributeName: IdentifierGroup,AttributeType: S}],TableName: SHARED.scopes.Counters,KeySchema: [{AttributeName: IdentifierGroup,KeyType: HASH}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.scopes.Counters");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.Counters")
                        .withAttributeDefinitions(new AttributeDefinition("IdentifierGroup", ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement("IdentifierGroup", KeyType.HASH))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)


        );

// describeTable {TableName: SHARED.jobs}
//System.out.println("describeTable SHARED.jobs");
//db.describeTable("SHARED.jobs");

        // createTable {AttributeDefinitions: [{AttributeName: hk,AttributeType: B}, {AttributeName: sk,AttributeType: B}, {AttributeName: outstanding_tasks_hk,AttributeType: B}, {AttributeName: execute_after,AttributeType: B}],TableName: SHARED.jobs,KeySchema: [{AttributeName: hk,KeyType: HASH}, {AttributeName: sk,KeyType: RANGE}],GlobalSecondaryIndexes: [{IndexName: outstanding_tasks,KeySchema: [{AttributeName: outstanding_tasks_hk,KeyType: HASH}, {AttributeName: execute_after,KeyType: RANGE}],Projection: {ProjectionType: ALL,},}],BillingMode: PAY_PER_REQUEST,Tags: [],}
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

// describeTable {TableName: SHARED.scopes.Services}
//System.out.println("describeTable SHARED.scopes.Services");
//db.describeTable("SHARED.scopes.Services");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.Services,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.Services");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.Services")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=25 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=25 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 83, 101, 114, 118, 105, 99, 101, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=25 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 83, 101, 114, 118, 105, 99, 101, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 22, 95}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.scopes.Clouds}
//System.out.println("describeTable SHARED.scopes.Clouds");
//db.describeTable("SHARED.scopes.Clouds");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.Clouds,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.Clouds");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.Clouds")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=23 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=23 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 67, 108, 111, 117, 100, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=23 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 67, 108, 111, 117, 100, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 22, -3}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.scopes.DevScopesByIdentifier}
//System.out.println("describeTable SHARED.scopes.DevScopesByIdentifier");
//db.describeTable("SHARED.scopes.DevScopesByIdentifier");

// createTable {AttributeDefinitions: [{AttributeName: Identifier,AttributeType: N}],TableName: SHARED.scopes.DevScopesByIdentifier,KeySchema: [{AttributeName: Identifier,KeyType: HASH}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.scopes.DevScopesByIdentifier");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevScopesByIdentifier")
                        .withAttributeDefinitions(new AttributeDefinition("Identifier", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Identifier", KeyType.HASH))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)


        );

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");

// createTable {AttributeDefinitions: [{AttributeName: Scope,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.metadata.dev.Documents,KeySchema: [{AttributeName: Scope,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.metadata.dev.Documents");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.metadata.dev.Documents")
                        .withAttributeDefinitions(new AttributeDefinition("Scope", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Scope", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.scopes.DevCloudTenants}
//System.out.println("describeTable SHARED.scopes.DevCloudTenants");
//db.describeTable("SHARED.scopes.DevCloudTenants");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.DevCloudTenants,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.DevCloudTenants");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevCloudTenants")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.scopes.DevTenantsLookup}
//System.out.println("describeTable SHARED.scopes.DevTenantsLookup");
//db.describeTable("SHARED.scopes.DevTenantsLookup");

// createTable {AttributeDefinitions: [{AttributeName: TopLevelScopeAndTenantType,AttributeType: S}, {AttributeName: KeyAndVersion,AttributeType: S}],TableName: SHARED.scopes.DevTenantsLookup,KeySchema: [{AttributeName: TopLevelScopeAndTenantType,KeyType: HASH}, {AttributeName: KeyAndVersion,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.scopes.DevTenantsLookup");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevTenantsLookup")
                        .withAttributeDefinitions(new AttributeDefinition("TopLevelScopeAndTenantType", ScalarAttributeType.S), new AttributeDefinition("KeyAndVersion", ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement("TopLevelScopeAndTenantType", KeyType.HASH), new KeySchemaElement("KeyAndVersion", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)


        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 67, 108, 111, 117, 100, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 67, 108, 111, 117, 100, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, 45}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {3, 83, 72, 65, 82, 69, 68, 46, 109, 101, 116, 97, 100, 97, 116, 97, 46, 100, 101, 118, 46, 68, 111, 99, 117, 109, 101, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {3, 83, 72, 65, 82, 69, 68, 46, 109, 101, 116, 97, 100, 97, 116, 97, 46, 100, 101, 118, 46, 68, 111, 99, 117, 109, 101, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, 61}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.scopes.DevTenantGroups}
//System.out.println("describeTable SHARED.scopes.DevTenantGroups");
//db.describeTable("SHARED.scopes.DevTenantGroups");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.DevTenantGroups,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.DevTenantGroups");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevTenantGroups")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.scopes.DevTenantsLookup}
//System.out.println("describeTable SHARED.scopes.DevTenantsLookup");
//db.describeTable("SHARED.scopes.DevTenantsLookup");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 84, 101, 110, 97, 110, 116, 71, 114, 111, 117, 112, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 84, 101, 110, 97, 110, 116, 71, 114, 111, 117, 112, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, 88}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.scopes.DevServices}
//System.out.println("describeTable SHARED.scopes.DevServices");
//db.describeTable("SHARED.scopes.DevServices");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.DevServices,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.DevServices");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevServices")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=28 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=28 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 83, 101, 114, 118, 105, 99, 101, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=28 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 83, 101, 114, 118, 105, 99, 101, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, 123}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.scopes.DevTenants}
//System.out.println("describeTable SHARED.scopes.DevTenants");
//db.describeTable("SHARED.scopes.DevTenants");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.DevTenants,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.DevTenants");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevTenants")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.scopes.DevTenantsLookup}
//System.out.println("describeTable SHARED.scopes.DevTenantsLookup");
//db.describeTable("SHARED.scopes.DevTenantsLookup");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=27 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=27 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=27 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, -106}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.metadata.dev.Documents}
//System.out.println("describeTable SHARED.metadata.dev.Documents");
//db.describeTable("SHARED.metadata.dev.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=32 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.scopes.DevClouds}
//System.out.println("describeTable SHARED.scopes.DevClouds");
//db.describeTable("SHARED.scopes.DevClouds");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.DevClouds,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.DevClouds");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.DevClouds")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=26 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=26 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 67, 108, 111, 117, 100, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=26 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 68, 101, 118, 67, 108, 111, 117, 100, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 23, -85}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=10 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.scopes.Clouds,Limit: 1,ConsistentRead: true,KeyConditions: {Name={AttributeValueList: [{S: c2,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.scopes.Clouds,Item: {Name={S: c2,}, Identifier={N: 0,}, Version={N: 0,}, CreatedVersion={N: 0,}, Timestamp={N: 1715699004522,}, Status={N: 0,}, PurgeStatus={N: 5,}, CreatedTime={N: 1715699004487,}, Multitenancy={M: {MultitenantNamespace={S: com.test.multitenant,}, TenantViewNamespace={S: com.test.tenantview,}, TenantMetadataNameQualifier={S: __,}},}, LastSystemModifiedTime={N: 1715699004528,}},Expected: {Name={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.scopes.Clouds");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Name", new AttributeValue().withS("c2"));
            item.put("Identifier", new AttributeValue().withN("0"));
            item.put("Version", new AttributeValue().withN("0"));
            item.put("CreatedVersion", new AttributeValue().withN("0"));
            item.put("Timestamp", new AttributeValue().withN("1715699004522"));
            item.put("Status", new AttributeValue().withN("0"));
            item.put("PurgeStatus", new AttributeValue().withN("5"));
            item.put("CreatedTime", new AttributeValue().withN("1715699004487"));
            item.put("Multitenancy", new AttributeValue().withM(ImmutableMap.of("MultitenantNamespace", new AttributeValue().withS("com.test.multitenant"), "TenantViewNamespace", new AttributeValue().withS("com.test.tenantview"), "TenantMetadataNameQualifier", new AttributeValue().withS("__"))));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699004528"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.scopes.Clouds")
                            .withItem(item)



            );
        }
// getItem {TableName: SHARED.scopes.Clouds,Key: {Name={S: c2,}, Version={N: 1,}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.metadata.clouds.c2.0.Documents}
//System.out.println("describeTable SHARED.metadata.clouds.c2.0.Documents");
//db.describeTable("SHARED.metadata.clouds.c2.0.Documents");

// describeTable {TableName: SHARED.metadata.clouds.c2.0.Documents}
//System.out.println("describeTable SHARED.metadata.clouds.c2.0.Documents");
//db.describeTable("SHARED.metadata.clouds.c2.0.Documents");

// createTable {AttributeDefinitions: [{AttributeName: Scope,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.metadata.clouds.c2.0.Documents,KeySchema: [{AttributeName: Scope,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.metadata.clouds.c2.0.Documents");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.metadata.clouds.c2.0.Documents")
                        .withAttributeDefinitions(new AttributeDefinition("Scope", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Scope", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );
// getItem {TableName: SHARED.scopes.Clouds,Key: {Name={S: c2,}, Version={N: 1,}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.metadata.clouds.c2.0.Documents}
//System.out.println("describeTable SHARED.metadata.clouds.c2.0.Documents");
//db.describeTable("SHARED.metadata.clouds.c2.0.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=40 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=40 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {3, 83, 72, 65, 82, 69, 68, 46, 109, 101, 116, 97, 100, 97, 116, 97, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 68, 111, 99, 117, 109, 101, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=40 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {3, 83, 72, 65, 82, 69, 68, 46, 109, 101, 116, 97, 100, 97, 116, 97, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 68, 111, 99, 117, 109, 101, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 36, -87}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }
// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// getItem {TableName: SHARED.scopes.Clouds,Key: {Name={S: c2,}, Version={N: 0,}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.metadata.clouds.c2.0.Documents,Item: {Scope={S: cc2.0,}, Version={N: 0,}, Document={S: {"schemas":[],"references":[{"uri":"/","version":1,"includes":[{"namespace":"com.salesforce.zero.eventsourcing.v0"},{"namespace":"com.salesforce.zero.ttl.v0"},{"namespace":"com.salesforce.zero.core.v0"}]}],"lastModifiedTime":"May 14, 2024, 11:03:24 AM","version":0,"status":"ACTIVE","nextEntityTypeId":0},}, LastSystemModifiedTime={N: 1715699004718,}},Expected: {Scope={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.metadata.clouds.c2.0.Documents");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Scope", new AttributeValue().withS("cc2.0"));
            item.put("Version", new AttributeValue().withN("0"));
            item.put("Document", new AttributeValue().withS("{\"schemas\":[],\"references\":[{\"uri\":\"/\",\"version\":1,\"includes\":[{\"namespace\":\"com.salesforce.zero.eventsourcing.v0\"},{\"namespace\":\"com.salesforce.zero.ttl.v0\"},{\"namespace\":\"com.salesforce.zero.core.v0\"}]}],\"lastModifiedTime\":\"May 14, 2024, 11:03:24 AM\",\"version\":0,\"status\":\"ACTIVE\",\"nextEntityTypeId\":0}"));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699004718"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.metadata.clouds.c2.0.Documents")
                            .withItem(item)



            );
        }

// describeTable {TableName: SHARED.scopes.clouds.c2.0.Tenants}
//System.out.println("describeTable SHARED.scopes.clouds.c2.0.Tenants");
//db.describeTable("SHARED.scopes.clouds.c2.0.Tenants");

// createTable {AttributeDefinitions: [{AttributeName: Name,AttributeType: S}, {AttributeName: Version,AttributeType: N}],TableName: SHARED.scopes.clouds.c2.0.Tenants,KeySchema: [{AttributeName: Name,KeyType: HASH}, {AttributeName: Version,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.scopes.clouds.c2.0.Tenants");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.clouds.c2.0.Tenants")
                        .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S), new AttributeDefinition("Version", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Name", KeyType.HASH), new KeySchemaElement("Version", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.scopes.clouds.c2.0.TenantsByIdentifier}
//System.out.println("describeTable SHARED.scopes.clouds.c2.0.TenantsByIdentifier");
//db.describeTable("SHARED.scopes.clouds.c2.0.TenantsByIdentifier");

// createTable {AttributeDefinitions: [{AttributeName: Identifier,AttributeType: N}],TableName: SHARED.scopes.clouds.c2.0.TenantsByIdentifier,KeySchema: [{AttributeName: Identifier,KeyType: HASH}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.scopes.clouds.c2.0.TenantsByIdentifier");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.scopes.clouds.c2.0.TenantsByIdentifier")
                        .withAttributeDefinitions(new AttributeDefinition("Identifier", ScalarAttributeType.N))
                        .withKeySchema(new KeySchemaElement("Identifier", KeyType.HASH))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)


        );
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=36 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=36 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=36 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {7, 83, 72, 65, 82, 69, 68, 46, 115, 99, 111, 112, 101, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 84, 101, 110, 97, 110, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 37, 61}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 1}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

// describeTable {TableName: SHARED.streams.clouds.c2.0.Metadata}
//System.out.println("describeTable SHARED.streams.clouds.c2.0.Metadata");
//db.describeTable("SHARED.streams.clouds.c2.0.Metadata");

        // createTable {AttributeDefinitions: [{AttributeName: 0,AttributeType: B}, {AttributeName: 1,AttributeType: B}, {AttributeName: 2,AttributeType: B}, {AttributeName: 4,AttributeType: B}, {AttributeName: 2A,AttributeType: B}],TableName: SHARED.streams.clouds.c2.0.Metadata,KeySchema: [{AttributeName: 0,KeyType: HASH}, {AttributeName: 1,KeyType: RANGE}],GlobalSecondaryIndexes: [{IndexName: idx_pstreams,KeySchema: [{AttributeName: 2,KeyType: HASH}],Projection: {ProjectionType: KEYS_ONLY,},}, {IndexName: idx_vstreams,KeySchema: [{AttributeName: 4,KeyType: HASH}],Projection: {ProjectionType: KEYS_ONLY,},}, {IndexName: idx_queries,KeySchema: [{AttributeName: 2A,KeyType: HASH}],Projection: {ProjectionType: KEYS_ONLY,},}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.streams.clouds.c2.0.Metadata");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.streams.clouds.c2.0.Metadata")
                        .withAttributeDefinitions(new AttributeDefinition("0", ScalarAttributeType.B), new AttributeDefinition("1", ScalarAttributeType.B), new AttributeDefinition("2", ScalarAttributeType.B), new AttributeDefinition("4", ScalarAttributeType.B), new AttributeDefinition("2A", ScalarAttributeType.B))
                        .withKeySchema(new KeySchemaElement("0", KeyType.HASH), new KeySchemaElement("1", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)
                        .withGlobalSecondaryIndexes(new GlobalSecondaryIndex()
                                .withIndexName("idx_pstreams")
                                .withKeySchema(new KeySchemaElement("2", KeyType.HASH))
                                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)), new GlobalSecondaryIndex()
                                .withIndexName("idx_vstreams")
                                .withKeySchema(new KeySchemaElement("4", KeyType.HASH))
                                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)), new GlobalSecondaryIndex()
                                .withIndexName("idx_queries")
                                .withKeySchema(new KeySchemaElement("2A", KeyType.HASH))
                                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY)))

        );
// getItem {TableName: SHARED.scopes.Clouds,Key: {Name={S: c2,}, Version={N: 1,}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.metadata.clouds.c2.0.Documents}
//System.out.println("describeTable SHARED.metadata.clouds.c2.0.Documents");
//db.describeTable("SHARED.metadata.clouds.c2.0.Documents");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=40 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.scopes.clouds.c2.0.Tenants}
//System.out.println("describeTable SHARED.scopes.clouds.c2.0.Tenants");
//db.describeTable("SHARED.scopes.clouds.c2.0.Tenants");

// describeTable {TableName: SHARED.scopes.clouds.c2.0.TenantsByIdentifier}
//System.out.println("describeTable SHARED.scopes.clouds.c2.0.TenantsByIdentifier");
//db.describeTable("SHARED.scopes.clouds.c2.0.TenantsByIdentifier");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=36 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
// describeTable {TableName: SHARED.streams.clouds.c2.0.Metadata}
//System.out.println("describeTable SHARED.streams.clouds.c2.0.Metadata");
//db.describeTable("SHARED.streams.clouds.c2.0.Metadata");
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {9, 83, 72, 65, 82, 69, 68, 46, 115, 116, 114, 101, 97, 109, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 77, 101, 116, 97, 100, 97, 116, 97, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 107, 6, 21, 48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 107, 6, 21, 48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {9, 83, 72, 65, 82, 69, 68, 46, 115, 116, 114, 101, 97, 109, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 77, 101, 116, 97, 100, 97, 116, 97, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 37, -123}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {10, 83, 72, 65, 82, 69, 68, 46, 115, 116, 114, 101, 97, 109, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 77, 101, 116, 97, 100, 97, 116, 97, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 107, 61, 3, -80}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 107, 61, 3, -80}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=38 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {10, 83, 72, 65, 82, 69, 68, 46, 115, 116, 114, 101, 97, 109, 115, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 77, 101, 116, 97, 100, 97, 116, 97, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 37, -106}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.scopes.Clouds,Item: {Name={S: c2,}, Identifier={N: 0,}, Version={N: 1,}, CreatedVersion={N: 0,}, Timestamp={N: 1715699004853,}, Status={N: 1,}, PurgeStatus={N: 5,}, CreatedTime={N: 1715699004487,}, Multitenancy={M: {MultitenantNamespace={S: com.test.multitenant,}, TenantViewNamespace={S: com.test.tenantview,}, TenantMetadataNameQualifier={S: __,}},}, LastSystemModifiedTime={N: 1715699004853,}},Expected: {Name={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.scopes.Clouds");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Name", new AttributeValue().withS("c2"));
            item.put("Identifier", new AttributeValue().withN("0"));
            item.put("Version", new AttributeValue().withN("1"));
            item.put("CreatedVersion", new AttributeValue().withN("0"));
            item.put("Timestamp", new AttributeValue().withN("1715699004853"));
            item.put("Status", new AttributeValue().withN("1"));
            item.put("PurgeStatus", new AttributeValue().withN("5"));
            item.put("CreatedTime", new AttributeValue().withN("1715699004487"));
            item.put("Multitenancy", new AttributeValue().withM(ImmutableMap.of("MultitenantNamespace", new AttributeValue().withS("com.test.multitenant"), "TenantViewNamespace", new AttributeValue().withS("com.test.tenantview"), "TenantMetadataNameQualifier", new AttributeValue().withS("__"))));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699004853"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.scopes.Clouds")
                            .withItem(item)



            );
        }
// query {TableName: SHARED.scopes.Clouds,Limit: 1,ConsistentRead: true,KeyConditions: {Name={AttributeValueList: [{S: c2,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.scopes.Clouds,Limit: 1,ConsistentRead: true,KeyConditions: {Name={AttributeValueList: [{S: c2,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.scopes.Clouds,Limit: 6,ConsistentRead: true,KeyConditions: {Name={AttributeValueList: [{S: c2,}],ComparisonOperator: EQ}, Version={AttributeValueList: [{N: 0,}],ComparisonOperator: GE}},ScanIndexForward: true,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.metadata.clouds.c2.0.Documents,Item: {Scope={S: cc2.0,}, Version={N: 1,}, Document={S: {"schemas":[{"namespace":"com.testtenant","entityTypes":[{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleNestedObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"CREATING","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"CREATING","createdVersion":1}],"navigationProperties":[{"name":"parent","type":"com.testtenant.SimpleObject","referentialConstraints":[],"partner":"children","containsTarget":false,"collection":false,"interleavedTarget":false,"id":2,"status":"CREATING","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"CREATING","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":1,"majorVersion":1,"createdVersion":1,"id":0,"nextPropertyId":3},{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"CREATING","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"CREATING","createdVersion":1}],"navigationProperties":[{"name":"children","type":"com.testtenant.SimpleNestedObject","referentialConstraints":[],"partner":"parent","containsTarget":true,"collection":true,"interleavedTarget":false,"id":2,"status":"CREATING","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"CREATING","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":1,"majorVersion":1,"createdVersion":1,"id":1,"nextPropertyId":3}],"complexTypes":[],"enumTypes":[],"status":"UPDATING","lastModifiedVersion":1}],"references":[{"uri":"/","version":1,"includes":[{"namespace":"com.salesforce.zero.eventsourcing.v0"},{"namespace":"com.salesforce.zero.ttl.v0"},{"namespace":"com.salesforce.zero.core.v0"}]}],"lastModifiedTime":"May 14, 2024, 11:03:25 AM","version":1,"status":"UPDATING","nextEntityTypeId":2},}, LastSystemModifiedTime={N: 1715699005237,}},Expected: {Scope={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.metadata.clouds.c2.0.Documents");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Scope", new AttributeValue().withS("cc2.0"));
            item.put("Version", new AttributeValue().withN("1"));
            item.put("Document", new AttributeValue().withS("{\"schemas\":[{\"namespace\":\"com.testtenant\",\"entityTypes\":[{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleNestedObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"CREATING\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"CREATING\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"parent\",\"type\":\"com.testtenant.SimpleObject\",\"referentialConstraints\":[],\"partner\":\"children\",\"containsTarget\":false,\"collection\":false,\"interleavedTarget\":false,\"id\":2,\"status\":\"CREATING\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"CREATING\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":1,\"majorVersion\":1,\"createdVersion\":1,\"id\":0,\"nextPropertyId\":3},{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"CREATING\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"CREATING\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"children\",\"type\":\"com.testtenant.SimpleNestedObject\",\"referentialConstraints\":[],\"partner\":\"parent\",\"containsTarget\":true,\"collection\":true,\"interleavedTarget\":false,\"id\":2,\"status\":\"CREATING\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"CREATING\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":1,\"majorVersion\":1,\"createdVersion\":1,\"id\":1,\"nextPropertyId\":3}],\"complexTypes\":[],\"enumTypes\":[],\"status\":\"UPDATING\",\"lastModifiedVersion\":1}],\"references\":[{\"uri\":\"/\",\"version\":1,\"includes\":[{\"namespace\":\"com.salesforce.zero.eventsourcing.v0\"},{\"namespace\":\"com.salesforce.zero.ttl.v0\"},{\"namespace\":\"com.salesforce.zero.core.v0\"}]}],\"lastModifiedTime\":\"May 14, 2024, 11:03:25 AM\",\"version\":1,\"status\":\"UPDATING\",\"nextEntityTypeId\":2}"));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699005237"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.metadata.clouds.c2.0.Documents")
                            .withItem(item)



            );
        }

// describeTable {TableName: SHARED.data.clouds.c2.0.shared.Objects}
//System.out.println("describeTable SHARED.data.clouds.c2.0.shared.Objects");
//db.describeTable("SHARED.data.clouds.c2.0.shared.Objects");

// createTable {AttributeDefinitions: [{AttributeName: 0,AttributeType: B}, {AttributeName: 1,AttributeType: B}],TableName: SHARED.data.clouds.c2.0.shared.Objects,KeySchema: [{AttributeName: 0,KeyType: HASH}, {AttributeName: 1,KeyType: RANGE}],BillingMode: PAY_PER_REQUEST,StreamSpecification: {StreamEnabled: true,StreamViewType: NEW_AND_OLD_IMAGES},Tags: [],}
        System.out.println("createTable SHARED.data.clouds.c2.0.shared.Objects");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.data.clouds.c2.0.shared.Objects")
                        .withAttributeDefinitions(new AttributeDefinition("0", ScalarAttributeType.B), new AttributeDefinition("1", ScalarAttributeType.B))
                        .withKeySchema(new KeySchemaElement("0", KeyType.HASH), new KeySchemaElement("1", KeyType.RANGE))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)

                        .withStreamSpecification(new StreamSpecification().withStreamEnabled(true).withStreamViewType(StreamViewType.NEW_AND_OLD_IMAGES))
        );

// describeTable {TableName: SHARED.data.clouds.c2.0.shared.Objects.Leases}
//System.out.println("describeTable SHARED.data.clouds.c2.0.shared.Objects.Leases");
//db.describeTable("SHARED.data.clouds.c2.0.shared.Objects.Leases");

// createTable {AttributeDefinitions: [{AttributeName: leaseKey,AttributeType: S}],TableName: SHARED.data.clouds.c2.0.shared.Objects.Leases,KeySchema: [{AttributeName: leaseKey,KeyType: HASH}],BillingMode: PAY_PER_REQUEST,Tags: [],}
        System.out.println("createTable SHARED.data.clouds.c2.0.shared.Objects.Leases");
        db.createTable(
                new CreateTableRequest()
                        .withTableName("SHARED.data.clouds.c2.0.shared.Objects.Leases")
                        .withAttributeDefinitions(new AttributeDefinition("leaseKey", ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement("leaseKey", KeyType.HASH))
                        .withBillingMode(BillingMode.PAY_PER_REQUEST)


        );

        // putItem {TableName: SHARED.metadata.clouds.c2.0.Documents,Item: {Scope={S: cc2.0,}, Version={N: 2,}, Document={S: {"schemas":[{"namespace":"com.testtenant","entityTypes":[{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleNestedObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"CREATING","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"CREATING","createdVersion":1}],"navigationProperties":[{"name":"parent","type":"com.testtenant.SimpleObject","referentialConstraints":[],"partner":"children","containsTarget":false,"collection":false,"interleavedTarget":false,"id":2,"status":"CREATING","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"CREATING_READY","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":2,"majorVersion":1,"createdVersion":1,"id":0,"nextPropertyId":3},{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"CREATING","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"CREATING","createdVersion":1}],"navigationProperties":[{"name":"children","type":"com.testtenant.SimpleNestedObject","referentialConstraints":[],"partner":"parent","containsTarget":true,"collection":true,"interleavedTarget":false,"id":2,"status":"CREATING","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"CREATING","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":1,"majorVersion":1,"createdVersion":1,"id":1,"nextPropertyId":3}],"complexTypes":[],"enumTypes":[],"status":"UPDATING","lastModifiedVersion":1}],"references":[{"uri":"/","version":1,"includes":[{"namespace":"com.salesforce.zero.eventsourcing.v0"},{"namespace":"com.salesforce.zero.ttl.v0"},{"namespace":"com.salesforce.zero.core.v0"}]}],"lastModifiedTime":"May 14, 2024, 11:03:25 AM","version":2,"status":"UPDATING","nextEntityTypeId":2},}, LastSystemModifiedTime={N: 1715699005467,}},Expected: {Scope={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.metadata.clouds.c2.0.Documents");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Scope", new AttributeValue().withS("cc2.0"));
            item.put("Version", new AttributeValue().withN("2"));
            item.put("Document", new AttributeValue().withS("{\"schemas\":[{\"namespace\":\"com.testtenant\",\"entityTypes\":[{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleNestedObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"CREATING\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"CREATING\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"parent\",\"type\":\"com.testtenant.SimpleObject\",\"referentialConstraints\":[],\"partner\":\"children\",\"containsTarget\":false,\"collection\":false,\"interleavedTarget\":false,\"id\":2,\"status\":\"CREATING\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"CREATING_READY\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":2,\"majorVersion\":1,\"createdVersion\":1,\"id\":0,\"nextPropertyId\":3},{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"CREATING\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"CREATING\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"children\",\"type\":\"com.testtenant.SimpleNestedObject\",\"referentialConstraints\":[],\"partner\":\"parent\",\"containsTarget\":true,\"collection\":true,\"interleavedTarget\":false,\"id\":2,\"status\":\"CREATING\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"CREATING\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":1,\"majorVersion\":1,\"createdVersion\":1,\"id\":1,\"nextPropertyId\":3}],\"complexTypes\":[],\"enumTypes\":[],\"status\":\"UPDATING\",\"lastModifiedVersion\":1}],\"references\":[{\"uri\":\"/\",\"version\":1,\"includes\":[{\"namespace\":\"com.salesforce.zero.eventsourcing.v0\"},{\"namespace\":\"com.salesforce.zero.ttl.v0\"},{\"namespace\":\"com.salesforce.zero.core.v0\"}]}],\"lastModifiedTime\":\"May 14, 2024, 11:03:25 AM\",\"version\":2,\"status\":\"UPDATING\",\"nextEntityTypeId\":2}"));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699005467"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.metadata.clouds.c2.0.Documents")
                            .withItem(item)



            );
        }

// describeTable {TableName: SHARED.data.clouds.c2.0.shared.Objects}
//System.out.println("describeTable SHARED.data.clouds.c2.0.shared.Objects");
//db.describeTable("SHARED.data.clouds.c2.0.shared.Objects");
// describeTimeToLive {TableName: SHARED.data.clouds.c2.0.shared.Objects}
// updateTimeToLive {TableName: SHARED.data.clouds.c2.0.shared.Objects,TimeToLiveSpecification: {Enabled: true,AttributeName: #}}
        System.out.println("updateTimeToLive SHARED.data.clouds.c2.0.shared.Objects");
        db.updateTimeToLive(new UpdateTimeToLiveRequest().withTableName("SHARED.data.clouds.c2.0.shared.Objects").withTimeToLiveSpecification(
                new TimeToLiveSpecification().withEnabled(true).withAttributeName("#")
        ));
// getItem {TableName: SHARED.jobs,Key: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=41 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}},ConsistentRead: true,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=41 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=2 cap=1024],}, outstanding_tasks_hk={B: java.nio.HeapByteBuffer[pos=0 lim=1 cap=1],}, execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, scheduled_execute_after={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {1, 83, 72, 65, 82, 69, 68, 46, 100, 97, 116, 97, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 115, 104, 97, 114, 101, 100, 46, 79, 98, 106, 101, 99, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0, -128}));
            item.put("outstanding_tasks_hk", newAttributeValueB(new byte[] {0}));
            item.put("execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));
            item.put("scheduled_execute_after", newAttributeValueB(new byte[] {-47, 127, 106, -54, -110, -48}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.jobs,Item: {hk={B: java.nio.HeapByteBufferR[pos=0 lim=41 cap=2048],}, sk={B: java.nio.HeapByteBufferR[pos=0 lim=1 cap=1024],}, submitted_datetime={B: java.nio.HeapByteBuffer[pos=0 lim=6 cap=1024],}, task_count={N: 1,}, outstanding_tasks={NS: [0],}, custom_params={B: java.nio.HeapByteBuffer[pos=0 lim=9 cap=9],}},ReturnConsumedCapacity: TOTAL,ConditionExpression: attribute_not_exists(#0) AND attribute_not_exists(#1),ExpressionAttributeNames: {#0=hk, #1=sk},}
        System.out.println("putItem SHARED.jobs");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("hk", newAttributeValueB(new byte[] {1, 83, 72, 65, 82, 69, 68, 46, 100, 97, 116, 97, 46, 99, 108, 111, 117, 100, 115, 46, 99, 50, 46, 48, 46, 115, 104, 97, 114, 101, 100, 46, 79, 98, 106, 101, 99, 116, 115, 0, 1}));
            item.put("sk", newAttributeValueB(new byte[] {0}));
            item.put("submitted_datetime", newAttributeValueB(new byte[] {-47, 127, 103, -111, 40, 33}));
            item.put("task_count", new AttributeValue().withN("1"));
            item.put("outstanding_tasks", new AttributeValue().withNS("0"));
            item.put("custom_params", newAttributeValueB(new byte[] {1, -128, 0, 0, 0, -128, 0, 0, 7}));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.jobs")
                            .withItem(item)
                            .withConditionExpression("attribute_not_exists(#0) AND attribute_not_exists(#1)")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "hk", "#1", "sk"))

            );
        }

        // putItem {TableName: SHARED.metadata.clouds.c2.0.Documents,Item: {Scope={S: cc2.0,}, Version={N: 3,}, Document={S: {"schemas":[{"namespace":"com.testtenant","entityTypes":[{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleNestedObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"ACTIVE","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"ACTIVE","createdVersion":1}],"navigationProperties":[{"name":"parent","type":"com.testtenant.SimpleObject","referentialConstraints":[],"partner":"children","containsTarget":false,"collection":false,"interleavedTarget":false,"id":2,"status":"ACTIVE","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"ACTIVE","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":3,"majorVersion":1,"createdVersion":1,"id":0,"nextPropertyId":3},{"Abstract":false,"multitenant":false,"crossTenantViewEnabled":false,"name":"SimpleObject","key":[{"name":"id"}],"properties":[{"name":"id","type":"String","collection":false,"Implements":[],"id":0,"status":"ACTIVE","createdVersion":1},{"name":"value","type":"Int32","collection":false,"Implements":[],"id":1,"status":"ACTIVE","createdVersion":1}],"navigationProperties":[{"name":"children","type":"com.testtenant.SimpleNestedObject","referentialConstraints":[],"partner":"parent","containsTarget":true,"collection":true,"interleavedTarget":false,"id":2,"status":"ACTIVE","createdVersion":1}],"indexes":[],"constraints":[],"conditionalProperties":[],"Implements":[],"status":"ACTIVE","upgradingData":false,"lastModifiedVersion":1,"lastModifiedSystemVersion":3,"majorVersion":1,"createdVersion":1,"id":1,"nextPropertyId":3}],"complexTypes":[],"enumTypes":[],"status":"ACTIVE","lastModifiedVersion":1}],"references":[{"uri":"/","version":1,"includes":[{"namespace":"com.salesforce.zero.eventsourcing.v0"},{"namespace":"com.salesforce.zero.ttl.v0"},{"namespace":"com.salesforce.zero.core.v0"}]}],"lastModifiedTime":"May 14, 2024, 11:03:25 AM","version":3,"status":"ACTIVE","nextEntityTypeId":2},}, LastSystemModifiedTime={N: 1715699005500,}},Expected: {Scope={ComparisonOperator: NULL,}, Version={ComparisonOperator: NULL,}},ReturnConsumedCapacity: TOTAL,}
        System.out.println("putItem SHARED.metadata.clouds.c2.0.Documents");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("Scope", new AttributeValue().withS("cc2.0"));
            item.put("Version", new AttributeValue().withN("3"));
            item.put("Document", new AttributeValue().withS("{\"schemas\":[{\"namespace\":\"com.testtenant\",\"entityTypes\":[{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleNestedObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"ACTIVE\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"ACTIVE\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"parent\",\"type\":\"com.testtenant.SimpleObject\",\"referentialConstraints\":[],\"partner\":\"children\",\"containsTarget\":false,\"collection\":false,\"interleavedTarget\":false,\"id\":2,\"status\":\"ACTIVE\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"ACTIVE\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":3,\"majorVersion\":1,\"createdVersion\":1,\"id\":0,\"nextPropertyId\":3},{\"Abstract\":false,\"multitenant\":false,\"crossTenantViewEnabled\":false,\"name\":\"SimpleObject\",\"key\":[{\"name\":\"id\"}],\"properties\":[{\"name\":\"id\",\"type\":\"String\",\"collection\":false,\"Implements\":[],\"id\":0,\"status\":\"ACTIVE\",\"createdVersion\":1},{\"name\":\"value\",\"type\":\"Int32\",\"collection\":false,\"Implements\":[],\"id\":1,\"status\":\"ACTIVE\",\"createdVersion\":1}],\"navigationProperties\":[{\"name\":\"children\",\"type\":\"com.testtenant.SimpleNestedObject\",\"referentialConstraints\":[],\"partner\":\"parent\",\"containsTarget\":true,\"collection\":true,\"interleavedTarget\":false,\"id\":2,\"status\":\"ACTIVE\",\"createdVersion\":1}],\"indexes\":[],\"constraints\":[],\"conditionalProperties\":[],\"Implements\":[],\"status\":\"ACTIVE\",\"upgradingData\":false,\"lastModifiedVersion\":1,\"lastModifiedSystemVersion\":3,\"majorVersion\":1,\"createdVersion\":1,\"id\":1,\"nextPropertyId\":3}],\"complexTypes\":[],\"enumTypes\":[],\"status\":\"ACTIVE\",\"lastModifiedVersion\":1}],\"references\":[{\"uri\":\"/\",\"version\":1,\"includes\":[{\"namespace\":\"com.salesforce.zero.eventsourcing.v0\"},{\"namespace\":\"com.salesforce.zero.ttl.v0\"},{\"namespace\":\"com.salesforce.zero.core.v0\"}]}],\"lastModifiedTime\":\"May 14, 2024, 11:03:25 AM\",\"version\":3,\"status\":\"ACTIVE\",\"nextEntityTypeId\":2}"));
            item.put("LastSystemModifiedTime", new AttributeValue().withN("1715699005500"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.metadata.clouds.c2.0.Documents")
                            .withItem(item)



            );
        }
// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}// query {TableName: SHARED.metadata.clouds.c2.0.Documents,Limit: 1,ConsistentRead: true,KeyConditions: {Scope={AttributeValueList: [{S: cc2.0,}],ComparisonOperator: EQ}},ScanIndexForward: false,ReturnConsumedCapacity: TOTAL,}
        // putItem {TableName: SHARED.data.clouds.c2.0.shared.Objects,Item: {0={B: java.nio.HeapByteBuffer[pos=0 lim=4 cap=25],}, 1={B: java.nio.HeapByteBuffer[pos=0 lim=5 cap=1024],}, B={N: 42,}, C={B: java.nio.HeapByteBuffer[pos=0 lim=14 cap=14],}, -={N: 1,}},ReturnConsumedCapacity: TOTAL,ConditionExpression: ((attribute_not_exists(#0) AND attribute_not_exists(#1)) OR attribute_exists(#2)) AND (((attribute_not_exists(#0) AND attribute_not_exists(#1)) OR attribute_exists(#2)) OR (#3 = :0)),ExpressionAttributeNames: {#0=0, #1=1, #2=%, #3=-},ExpressionAttributeValues: {:0={N: 1,}},ReturnValuesOnConditionCheckFailure: ALL_OLD}
        System.out.println("putItem SHARED.data.clouds.c2.0.shared.Objects");
        {
            var item = new HashMap<String, AttributeValue>();
            item.put("0", newAttributeValueB(new byte[] {0, 1, 1, 32}));
            item.put("1", newAttributeValueB(new byte[] {102, 111, 111, 0, 1}));
            item.put("B", new AttributeValue().withN("42"));
            item.put("C", newAttributeValueB(new byte[] {0, -127, -89, 89, 109, 70, 121, 65, 65, 69, -127, -95, 66, 42}));
            item.put("-", new AttributeValue().withN("1"));

            db.putItem(
                    new PutItemRequest()
                            .withTableName("SHARED.data.clouds.c2.0.shared.Objects")
                            .withItem(item)
                            .withConditionExpression("((attribute_not_exists(#0) AND attribute_not_exists(#1)) OR attribute_exists(#2)) AND (((attribute_not_exists(#0) AND attribute_not_exists(#1)) OR attribute_exists(#2)) OR (#3 = :0))")
                            .withExpressionAttributeNames(ImmutableMap.of("#0", "0", "#1", "1", "#2", "%", "#3", "-"))
                            .withExpressionAttributeValues(ImmutableMap.of(":0", new AttributeValue().withN("1")))
            );
        }
    }
}
