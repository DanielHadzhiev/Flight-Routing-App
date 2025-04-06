
# Overview
The Flight Routing App helps users find the best flight routes between two cities, sorted by price. The app allows for multiple layovers and can find the most cost-effective routes. It also provides a REST API for users to query routes programmatically.

## Installation

Clone the repository and install the required dependencies.

```bash
git clone https://github.com/DanielHadzhiev/Flight-Routing-App.git
```
Make sure to install the necessary dependencies:

```bash
mvn install
```
## Usage
Run the application with:

```bash
mvn spring-boot:run
```
You can now query routes via the API or access the interactive API documentation at:

```url
http://localhost:8080/swagger-ui.html
```

## Example Request
You can make a POST request to the /find-routes endpoint to get flight routes between two cities:

```json

{
    "origin": "SOF",
    "destination": "MLE"
}
```
## Example Response
```json
[
    {
        "route": ["SOF", "LON", "MLE"],
        "totalprice": 30
    },
    {
        "route": ["SOF", "MLE"],
        "totalprice": 70
    }
]
```
## Optional Parameters
maxFlights: Limit the number of flights in the route. For example, if maxFlights is set to 1, only direct flights will be returned.

### Example with maxFlights:

```json
{
    "origin": "SOF",
    "destination": "MLE",
    "maxFlights": 1
}
```
### Response:

```json
[
    {
        "cities": ["SOF", "MLE"],
        "price": 70
    }
]
```
## Data File (Acts as Database)
The app uses a data file (flights_data.txt) to store all available flights and their prices. This file acts as a simple database, which is loaded when the application starts.

## Data Format
Each line represents a direct flight from one city to another with a price:

```objectives

SOF,IST,10
IST,CMB,20
CMB,MLE,40
```
## Testing
To ensure the app works correctly, automated tests have been included. To run tests, use the following command:

```bash
mvn test
```
The tests cover different scenarios like multiple routes, invalid data, and edge cases.
## License
No license is specified for this project.
