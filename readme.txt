How to Build and Run the applications:
The build environment is using gradle. The two applications will be running on port 7340 and 8003 separately.

Run the application:
1. Set up DB:
Please make sure to download Mysql server :
Startup MysqlDB on local, use root to login, create the following user in DB:
GRANT ALL PRIVILEGES ON *.* TO user1@'%' IDENTIFIED BY 'user1' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO user1@localhost IDENTIFIED BY 'user1' WITH GRANT OPTION;

in terminal, this will create the tables and some initial data :
cd items_app
./gradlew createDb
./gradlew seedData

2. Start up the web applications :
Start the items_app :
cd items_app; 
./gradlew tomcatRunWar

Start the auth_app:
cd auth_app;
./gradlew tomcatRunWar

3. now we can run some tests using my automation test suites. I have two test files, one is for GETItem service, the other for Post Item services.

Run the Post test cases:
cd items_app;
./gradlew test --tests=com.walmart.rest.functional.PostItemTests

Run the GET test cases:
cd items_app;
./gradlew test --tests=com.walmart.rest.functional.GetItemTests

Run all the test cases:
./gradlew test

All test cases should be finished successfully.

4. You can also try some easy tests with curl. Please remember to get authtoken first using curl:

 curl -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{ "userName": "wmt_user1", "password": "wmt_password1" } }' http://localhost:8003/services/security/authToken
sample response: {"status":"OK","authToken":"36971b6f-b5e0-43ef-a3cd-b7b6737a25fd|wmt_user1"}

 then please add the valid token in the header to curl items:
 curl -H 'Accept: application/json' -H 'X-Auth-Token: 8ed40f12-5003-4992-a4d7-85de9ecb0e5b|wmt_user1' -v http://localhost:7340/services/items/1
