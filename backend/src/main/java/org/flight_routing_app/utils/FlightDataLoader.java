package org.flight_routing_app.utils;

import org.flight_routing_app.model.entities.Flight;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightDataLoader {

    public List<Flight> loadFlightsFromFile(String filePath) throws IOException {
        List<Flight> flights = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] flightData = line.split(",");
            if (flightData.length == 3) {
                String from = flightData[0].trim();
                String to = flightData[1].trim();
                int price = Integer.parseInt(flightData[2].trim());

                flights.add(new Flight(from, to, price));
            }
        }
        reader.close();
        return flights;
    }
}
