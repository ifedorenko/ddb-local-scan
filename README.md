Demonstrates unexpected NullPointerException when using DynamoDb-local 2.5.0. (latest as of 2024-06-01).
Works as expected with DynamoDbLocal 2.2.1.

Steps to reproduce the problem

1. Execute `adhoc.dynamodb_local.DDBLocalEmbeddedBillingMode`

Example `adhoc.dynamodb_local.DDBLocalEmbeddedBillingMode` output:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
createTable SHARED.jobs
createTable testtable
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription.getReadCapacityUnits()" because the return value of "com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription.getProvisionedThroughput()" is null
	at com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.ControlPlaneFunction.validateProvisionedThroughputWithGSIs(ControlPlaneFunction.java:668)
	at com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.CreateTableFunction.validateProvisionedThroughput(CreateTableFunction.java:172)
	at com.amazonaws.services.dynamodbv2.local.shared.access.api.cp.CreateTableFunction.apply(CreateTableFunction.java:122)
	at com.amazonaws.services.dynamodbv2.local.shared.access.awssdkv1.client.LocalAmazonDynamoDB.createTable(LocalAmazonDynamoDB.java:229)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at com.amazonaws.services.dynamodbv2.local.embedded.DDBExceptionMappingInvocationHandler.invoke(DDBExceptionMappingInvocationHandler.java:173)
	at jdk.proxy1/jdk.proxy1.$Proxy22.createTable(Unknown Source)
	at adhoc.dynamodb_local.DDBLocalEmbeddedBillingMode.main(DDBLocalEmbeddedBillingMode.java:28)
```
