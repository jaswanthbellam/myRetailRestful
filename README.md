
# Overview

The project is an RESTFUL service to retrieve product and price details by ID.

# Configuration

The following environment variables need to be set: 

	redSkyApi - <url>

	(MongoDB database)
	server - <url>
	port - <27017>
	user - <username>
	pwd - <pwd>
	database- <databse name>


# Running Tests

`mvn test`

# To run

`mvn spring-boot:run` or `java -jar <project.jar>`

# Consuming the Api


### Request:

GET to http://`server address`/myRetailRestful/api/product/`id`
	
### Response:

	200 ok
	{
		“id”: 123,
		“name”:”The Blue sky”,
		“current_price”: {
		“value”: 45.49,
		“currency_code”:”USD”
		}
	}

### Request:

PUT to http://`server address`/myRetailRestful/api/product/`id`
	
	
	{
   		"id": 13860428,
    		"name": "The Big Lebowski (Blu-ray)",
    		"current_price": {
        	"value": 45.49,
        	"currency_code": "USD"
   		 }
	}
	
### Response:

	200 ok
	Data Updated

# Environment

Java8, Springboot, Mongodb, Spockframework, Maven, log4j2

