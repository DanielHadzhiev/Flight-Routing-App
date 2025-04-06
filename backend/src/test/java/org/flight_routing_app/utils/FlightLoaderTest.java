package org.flight_routing_app.utils;

import org.flight_routing_app.model.entities.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlightLoaderTest {

    private final FlightDataLoader flightDataLoader;

    @Autowired
    public FlightLoaderTest(FlightDataLoader flightDataLoader) {
        this.flightDataLoader = flightDataLoader;
    }


    @Test
    void testLoadValidFlights() throws IOException {
        List<Flight> flights = this.flightDataLoader
                .loadFlightsFromFile("src/test/resources/data_test.txt");
        assertFalse(flights.isEmpty());
    }

    @Test
    void testLoadInvalidFile() {
        assertThrows(IOException.class, () -> {
            this.flightDataLoader.loadFlightsFromFile("src/test/resources/thereIsNoSuchAFile:).txt");
        });
    }
    @Test
    void testLoadEmptyFile() throws IOException {
        List<Flight> flights = flightDataLoader.loadFlightsFromFile("src/test/resources/empty_file.txt");
        assertTrue(flights.isEmpty());
    }
}
