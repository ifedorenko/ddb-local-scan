Demonstrates unexpected LocalDBAccessException when using DynamoDb-local 2.5.2 on 
Apple MacBook Pro M3 MAX laptop.

Steps to reproduce the problem

1. Execute `adhoc.dynamodb_local.DDBLocalEmbeddedTableRecreated`

Example `adhoc.dynamodb_local.DDBLocalEmbeddedTableRecreated` output:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
0
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
Oct. 20, 2024 9:50:23 P.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "sm" ("StreamID", "StreamStatus", "TableName", "StreamInfo", "CreationDateTime", "DeletionDateTime") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: sm.StreamID]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1409)
	at com.almworks.sqlite4java.SQLiteConnection.cacheStatementHandle(SQLiteConnection.java:1338)
	at com.almworks.sqlite4java.SQLiteConnection.access$900(SQLiteConnection.java:51)
	at com.almworks.sqlite4java.SQLiteConnection$CachedController.dispose(SQLiteConnection.java:1733)
	at com.almworks.sqlite4java.SQLiteStatement.dispose(SQLiteStatement.java:187)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:125)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:840)

Oct. 20, 2024 9:50:23 P.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$3@290dbca: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "sm" ("StreamID", "StreamStatus", "TableName", "StreamInfo", "CreationDateTime", "DeletionDateTime") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: sm.StreamID]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.openNewStreamForTable(SQLiteDBAccessJob.java:397)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$3.doWork(SQLiteDBAccess.java:874)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$3.doWork(SQLiteDBAccess.java:827)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:840)

com.amazonaws.services.dynamodbv2.local.shared.exceptions.LocalDBAccessException: [1555] DB[1] step() [INSERT INTO "sm" ("StreamID", "StreamStatus", "TableName", "StreamInfo", "CreationDateTime", "DeletionDateTime") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: sm.StreamID]
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.get(AmazonDynamoDBOfflineSQLiteJob.java:84)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess.createTable(SQLiteDBAccess.java:879)
	at com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.CreateTableFunction$1.criticalSection(CreateTableFunction.java:141)
	at com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess$WriteLockWithTimeout.execute(LocalDBAccess.java:388)
	at com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.CreateTableFunction.apply(CreateTableFunction.java:144)
	at com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB.createTable(LocalAmazonDynamoDB.java:229)
	at jdk.internal.reflect.GeneratedMethodAccessor28.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at com.amazonaws.services.dynamodbv2.local.embedded.DDBExceptionMappingInvocationHandler.invoke(DDBExceptionMappingInvocationHandler.java:173)
	at jdk.proxy1/jdk.proxy1.$Proxy22.createTable(Unknown Source)
	at adhoc.dynamodb_local.DDBLocalEmbeddedTableRecreated.main(DDBLocalEmbeddedTableRecreated.java:20)
```
