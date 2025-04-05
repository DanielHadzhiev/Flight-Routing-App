package org.flight_routing_app.service;

import org.flight_routing_app.model.entities.Flight;
import org.flight_routing_app.model.entities.Route;
import org.flight_routing_app.utils.FlightDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteFinderService {

    private final FlightDataLoader flightDataLoader;

    @Autowired
    public RouteFinderService(FlightDataLoader flightDataLoader) {
        this.flightDataLoader = flightDataLoader;
    }

    public List<Route> findRoutes(String origin, String destination,int maxFlights) throws IOException {
        List<Route> routes = new ArrayList<>();

        List<Flight> allEndPointFlights = findAllEndPointFlights
                (destination,this.flightDataLoader
                        .loadFlightsFromFile
                                ("src\\main\\resources\\data.txt"));

        for(Flight flight : allEndPointFlights) {
            if(flight.getFrom().equals(origin)){
                routes.add(new Route(List.of(flight.getFrom(),flight.getTo()),flight.getPrice()));
            }
        }











        return routes;
    }

    private List<Flight>  findAllEndPointFlights(String destination,List<Flight> flights) throws IOException {

        List<Flight> endPointFlights = new ArrayList<>();

        for(Flight flight : flights) {
            if(flight.getTo().equals(destination)) {
                endPointFlights.add(flight);
            }
        }
        return endPointFlights;
    }
}
