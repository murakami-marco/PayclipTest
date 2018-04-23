# PayclipTest

## Generate Jar

Application Jar is located on build/libs folder.

To generate Jar:

1.- Clone code - **git clone https://github.com/murakami-marco/PayclipTest.git**

2.- Run Gradle to create Jar - **gradle clean fatJar**

## Run Application

The application perform the following actions:

* **ADD TRANSACTION**

**java -jar [jar_path] <user_id> add <transaction_json>**

This command should add a transaction to the user specified in <user_id> using the information specified in transaction_json.  The transaction json will have the following format:
{ “amount”: 1.23, “description”: “Joes Tacos”, “date”:”2018-12-30”, “user_id”: 345 }
This command should print out a version of the transaction added with a unique id for the transaction like this:
{ “transaction_id”: “2299ce24-9eaf-417f-82d6-e57f93777dc4”, “amount”: 1.23, “description”: “Joes Tacos”, “date”:”2018-12-30”, “user_id”: 345 }

* **SHOW TRANSACTION**

**java -jar [jar_path] <user_id> <transaction_id>**

This command should return the transaction specified in the transaction_id. If the user_id is not the user_id that corresponds with the user_id for the specified transaction,  you should print out
Transaction not found
 If the transaction does exists, you should print out the information for the transaction like this:
{ “transaction_id”: “2299ce24-9eaf-417f-82d6-e57f93777dc4”, “amount”: 1.23, “description”: “Joes Tacos”, “date”:”2018-12-30”, “user_id”: 345 }

* **LIST TRANSACTIONS**

**java -jar [jar_path] <user_id> list**

This command should print  all the transactions associated with the user specified by user_id. The transactions should be in chronological order. If the user_id does not exist, then the response should return an empty list. You should print the items in the following format:
[
{ “transaction_id”: “2299ce24-9eaf-417f-82d6-e57f93777dc4”, “amount”: 1.23, “description”: “Joes Tacos”, “date”:”2018-12-30”, “user_id”: 345 },
{ “transaction_id”: “5467ce24-9eaf-417f-82d6-e57f4444444”, “amount”: 5.26, “description”: “Freds’s Tacos”, “date”:”2018-12-19”, “user_id”: 345 }
]

* **SUM TRANSACTIONS**

**java -jar [jar_path] <user_id> sum**

This command should sum all the transactions associated with the user specified by user_id. It should print out the sum in the following format
{ “user_id”: 123, “sum”: 234.76 }