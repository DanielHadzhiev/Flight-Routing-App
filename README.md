Overview
The Flight Routing App helps users find the best flight routes between two cities, sorted by price. The app allows for multiple layovers and can find the most cost-effective routes. It also provides a REST API for users to query routes programmatically.

Features
Finds routes between an origin and destination city.

Sorts routes by price.

Supports layovers and multiple flights.

Exposes a REST API for querying routes, with interactive API documentation using Springdoc OpenAPI.

Setup Instructions
1. Clone the repository
Clone this repository to your local machine:


git clone https://github.com/DanielHadzhiev/Flight-Routing-App.git
2. Install dependencies
Ensure you have the necessary dependencies installed, including the Springdoc OpenAPI dependency for API documentation:

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.4</version>
</dependency>
Run the following command to install all required dependencies:

mvn install
3. Running the app
Run the application using the following command:

bash
Copy
Edit
mvn spring-boot:run
This will start the app, and you can begin querying routes through the provided API.

API Documentation
The app comes with interactive API documentation using Springdoc OpenAPI. Once the app is running, you can visit the following URL to explore and interact with the API:

http://localhost:8080/swagger-ui.html

Endpoint: /find-routes
This POST endpoint allows you to find routes between two cities.

Request Body:
json
Copy
Edit
{
    "origin": "SOF",
    "destination": "MLE"
}
Example Response:
json
Copy
Edit
[
    {
        "route": ["SOF", "LON", "MLE"],
        "price": 30
    },
    {
        "route": ["SOF", "MLE"],
        "price": 70
    }
]
Optional Parameters:
maxFlights: Limit the number of flights in the route. For example, if maxFlights is set to 1, only direct flights will be returned.

Example with maxFlights:

{
    "origin": "SOF",
    "destination": "MLE",
    "maxFlights": 1
}
Example Response:

[
    {
        "cities": ["SOF", "MLE"],
        "price": 70
    }
]
Data File (Acts as Database)
The app uses a data file (flights_data.txt) to store all available flights and their prices. This file acts as a simple database, which is loaded when the application starts.

Data Format:
Each flight is listed as follows:

SOF,IST,10
IST,CMB,20
CMB,MLE,40
Each line represents a direct flight from one city to another with a price.

Testing
To ensure the app works correctly, automated tests have been included. To run tests, use the following command:

mvn test
The tests cover different scenarios like multiple routes, invalid data, and edge cases.

Notes
All flights are one-way, and prices are fixed.

Routes cannot contain the same city more than once.

If no route is found, the API will return an empty list.
