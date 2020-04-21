# phoneservice
This is the phone service springboot app

mvn clean install

You can run the application by uisng one of the two ways.
1. mvn spring-boot:run
2. java -jar target/phoneservice-0.0.1-SNAPSHOT.jar

Run the application from postman by using the following details
endpoint: http://localhost:8080/phoneservice/v1.0/phoneNumber
Method Type POST
Request object:

{
	"filters": {
		"phoneNumber": "1234567890"
	},
	"page": 1,
	"limit": 5,
	"sortName": "name",
	"sortOrder": "desc"
}
