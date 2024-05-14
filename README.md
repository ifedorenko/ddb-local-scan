Demonstrates unexpected InternalFailure when using DynamoDbLocal 2.4.0 (latest as of 2024-05-14).
Works as expected with DynamoDbLocal 2.2.1.

Steps to reproduce the problem

1. Download and unzip DynamoDbLocal as per https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html
2. Start DynamoDbLocal (adjust JAVA_HOME to match your local environment)

       export JAVA_HOME=/opt/java17
       export PATH=$JAVA_HOME/bin:$PATH
       java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -port 8000 -inMemory

3. Execute `adhoc.dynamodb_local.DDBLocalSQLiteException`

Example `adhoc.dynamodb_local.DDBLocalSQLiteException` output
```
/opt/openjdk_17.0.5_17.38.22_x64/bin/java --add-opens java.base/java.lang=ALL-UNNAMED -javaagent:/Users/ifedorenko/Applications/IntelliJ IDEA Community Edition.app/Contents/lib/idea_rt.jar=50855:/Users/ifedorenko/Applications/IntelliJ IDEA Community Edition.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/ifedorenko/workspaces/zos-dev/ddb-local-scan/target/classes:/Users/ifedorenko/.m2/repository/com/amazonaws/aws-java-sdk-core/1.12.633/aws-java-sdk-core-1.12.633.jar:/Users/ifedorenko/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/Users/ifedorenko/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar:/Users/ifedorenko/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/Users/ifedorenko/.m2/repository/org/apache/httpcomponents/httpcore/4.4.13/httpcore-4.4.13.jar:/Users/ifedorenko/.m2/repository/software/amazon/ion/ion-java/1.0.2/ion-java-1.0.2.jar:/Users/ifedorenko/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.7.1/jackson-databind-2.12.7.1.jar:/Users/ifedorenko/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.7/jackson-annotations-2.12.7.jar:/Users/ifedorenko/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.7/jackson-core-2.12.7.jar:/Users/ifedorenko/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-cbor/2.12.6/jackson-dataformat-cbor-2.12.6.jar:/Users/ifedorenko/.m2/repository/joda-time/joda-time/2.8.1/joda-time-2.8.1.jar:/Users/ifedorenko/.m2/repository/com/amazonaws/aws-java-sdk-dynamodb/1.12.633/aws-java-sdk-dynamodb-1.12.633.jar:/Users/ifedorenko/.m2/repository/com/amazonaws/aws-java-sdk-s3/1.12.633/aws-java-sdk-s3-1.12.633.jar:/Users/ifedorenko/.m2/repository/com/amazonaws/aws-java-sdk-kms/1.12.633/aws-java-sdk-kms-1.12.633.jar:/Users/ifedorenko/.m2/repository/com/amazonaws/jmespath-java/1.12.633/jmespath-java-1.12.633.jar:/Users/ifedorenko/.m2/repository/com/google/guava/guava/33.0.0-jre/guava-33.0.0-jre.jar:/Users/ifedorenko/.m2/repository/com/google/guava/failureaccess/1.0.2/failureaccess-1.0.2.jar:/Users/ifedorenko/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/Users/ifedorenko/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:/Users/ifedorenko/.m2/repository/org/checkerframework/checker-qual/3.41.0/checker-qual-3.41.0.jar:/Users/ifedorenko/.m2/repository/com/google/errorprone/error_prone_annotations/2.23.0/error_prone_annotations-2.23.0.jar:/Users/ifedorenko/.m2/repository/com/google/j2objc/j2objc-annotations/2.8/j2objc-annotations-2.8.jar adhoc.dynamodb_local.DDBLocalSQLiteException
createTable SHARED.scopes.Counters
createTable SHARED.jobs
createTable SHARED.scopes.Services
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.Clouds
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.DevScopesByIdentifier
createTable SHARED.metadata.dev.Documents
createTable SHARED.scopes.DevCloudTenants
createTable SHARED.scopes.DevTenantsLookup
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.DevTenantGroups
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.DevServices
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.DevTenants
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.scopes.DevClouds
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.scopes.Clouds
createTable SHARED.metadata.clouds.c2.0.Documents
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.metadata.clouds.c2.0.Documents
createTable SHARED.scopes.clouds.c2.0.Tenants
createTable SHARED.scopes.clouds.c2.0.TenantsByIdentifier
putItem SHARED.jobs
putItem SHARED.jobs
createTable SHARED.streams.clouds.c2.0.Metadata
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.scopes.Clouds
putItem SHARED.metadata.clouds.c2.0.Documents
createTable SHARED.data.clouds.c2.0.shared.Objects
createTable SHARED.data.clouds.c2.0.shared.Objects.Leases
putItem SHARED.metadata.clouds.c2.0.Documents
updateTimeToLive SHARED.data.clouds.c2.0.shared.Objects
putItem SHARED.jobs
putItem SHARED.jobs
putItem SHARED.metadata.clouds.c2.0.Documents
putItem SHARED.data.clouds.c2.0.shared.Objects
Exception in thread "main" com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException: The request processing has failed because of an unknown error, exception or failure. (Service: AmazonDynamoDBv2; Status Code: 500; Error Code: InternalFailure; Request ID: ea4f7c47-54f8-41cc-b87b-57983bb57f13; Proxy: null)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.handleErrorResponse(AmazonHttpClient.java:1879)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.handleServiceErrorResponse(AmazonHttpClient.java:1418)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeOneRequest(AmazonHttpClient.java:1387)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeHelper(AmazonHttpClient.java:1157)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.doExecute(AmazonHttpClient.java:814)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.executeWithTimer(AmazonHttpClient.java:781)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.execute(AmazonHttpClient.java:755)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutor.access$500(AmazonHttpClient.java:715)
	at com.amazonaws.http.AmazonHttpClient$RequestExecutionBuilderImpl.execute(AmazonHttpClient.java:697)
	at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:561)
	at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:541)
	at com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient.doInvoke(AmazonDynamoDBClient.java:6903)
	at com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient.invoke(AmazonDynamoDBClient.java:6870)
	at com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient.executePutItem(AmazonDynamoDBClient.java:4241)
	at com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient.putItem(AmazonDynamoDBClient.java:4205)
	at adhoc.dynamodb_local.DDBLocalSQLiteException.test(DDBLocalSQLiteException.java:1079)
	at adhoc.dynamodb_local.DDBLocalSQLiteException.main(DDBLocalSQLiteException.java:41)

Process finished with exit code 1
```


Corresponding DynamoDbLocal console log
```
ifedorenko@ifedore-ltmwjnn dynamodb_local_latest % java -version
openjdk version "17.0.5" 2022-10-18 LTS
OpenJDK Runtime Environment Zulu17.38+22-SA (build 17.0.5+8-LTS)
OpenJDK 64-Bit Server VM Zulu17.38+22-SA (build 17.0.5+8-LTS, mixed mode, sharing)
ifedorenko@ifedore-ltmwjnn dynamodb_local_latest % java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -port 8000 -inMemory
Initializing DynamoDB Local with the following configuration:
Port:	8000
InMemory:	true
Version:	2.4.0
DbPath:	null
SharedDb:	false
shouldDelayTransientStatuses:	false
CorsParams:	null

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@5f864c18: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@22f44dc1: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@176886c3: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@6017f48: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:08 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@1c819787: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:09 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:09 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@3135b0d8: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:09 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:09 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@1bbca5fb: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:10 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:10 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@e79f136: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:11 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:11 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@7f85fcf2: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:16 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:16 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@6ba855c5: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:26 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C]: exception when clearing
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] reset [UNIQUE constraint failed: us.SequenceNumber]
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
	at java.base/java.lang.Thread.run(Thread.java:833)

May 14, 2024 11:20:26 A.M. com.almworks.sqlite4java.Internal log
WARNING: [sqlite] SQLiteDBAccess$17@139a5d1d: job exception
com.almworks.sqlite4java.SQLiteException: [1555] DB[1] step() [INSERT INTO "us" ("StreamID", "ShardID", "SequenceNumber", "CreationDateTime", "StreamRecord", "OperationType") VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: us.SequenceNumber]
	at com.almworks.sqlite4java.SQLiteConnection.throwResult(SQLiteConnection.java:1436)
	at com.almworks.sqlite4java.SQLiteConnection$BaseController.throwResult(SQLiteConnection.java:1689)
	at com.almworks.sqlite4java.SQLiteStatement.stepResult(SQLiteStatement.java:1402)
	at com.almworks.sqlite4java.SQLiteStatement.step(SQLiteStatement.java:301)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccessJob.insertUpdateStreamRecordIfActiveShardPresent(SQLiteDBAccessJob.java:358)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1826)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess$17.doWork(SQLiteDBAccess.java:1807)
	at com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.AmazonDynamoDBOfflineSQLiteJob.job(AmazonDynamoDBOfflineSQLiteJob.java:117)
	at com.almworks.sqlite4java.SQLiteJob.execute(SQLiteJob.java:372)
	at com.almworks.sqlite4java.SQLiteQueue.executeJob(SQLiteQueue.java:534)
	at com.almworks.sqlite4java.SQLiteQueue.queueFunction(SQLiteQueue.java:667)
	at com.almworks.sqlite4java.SQLiteQueue.runQueue(SQLiteQueue.java:623)
	at com.almworks.sqlite4java.SQLiteQueue.access$000(SQLiteQueue.java:77)
	at com.almworks.sqlite4java.SQLiteQueue$1.run(SQLiteQueue.java:205)
	at java.base/java.lang.Thread.run(Thread.java:833)
```
