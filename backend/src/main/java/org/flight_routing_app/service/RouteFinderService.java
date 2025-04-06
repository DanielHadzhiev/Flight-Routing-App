package org.flight_routing_app.service;

import org.flight_routing_app.model.entities.Flight;
import org.flight_routing_app.model.entities.Route;
import org.flight_routing_app.utils.FlightDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RouteFinderService {

    private final FlightDataLoader flightDataLoader;

    @Autowired
    public RouteFinderService(FlightDataLoader flightDataLoader) {
        this.flightDataLoader = flightDataLoader;
    }

    public List<Route> findRoutes(String origin, String destination, int maxFlights) throws IOException {

        List<Route> routes = new ArrayList<>();
        List<Flight> allFlights = flightDataLoader.loadFlightsFromFile("src/main/resources/data.txt");

        Map<String, List<Flight>> flightMap = new HashMap<>();
            for (Flight flight : allFlights) {
                flightMap.computeIfAbsent(flight.getFrom(), k -> new ArrayList<>()).add(flight);
         }

            if (origin.equals(destination)) {
                throw new IllegalArgumentException("Origin and destination cannot be the same.");
            }


            findRoutesDFS(origin, destination, flightMap, new ArrayList<>(), 0, routes, maxFlights);

        routes.sort(Comparator.comparingInt(Route::getTotalPrice));

        return routes;
    }

    private void findRoutesDFS(String currentCity, String destination, Map<String, List<Flight>> flightMap,
                               List<String> currentPath, int currentPrice, List<Route> routes, int maxFlights) {
        currentPath.add(currentCity);

        if (currentCity.equals(destination)) {

             routes.add(new Route(new ArrayList<>(currentPath), currentPrice));

        } else if (currentPath.size() <= maxFlights) {

            List<Flight> nextFlights = flightMap.getOrDefault(currentCity, new ArrayList<>());

            for (Flight flight : nextFlights) {
                if (!currentPath.contains(flight.getTo())) {

                    findRoutesDFS(flight.getTo(), destination, flightMap, currentPath,
                            currentPrice + flight.getPrice(), routes, maxFlights);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
    }
}
