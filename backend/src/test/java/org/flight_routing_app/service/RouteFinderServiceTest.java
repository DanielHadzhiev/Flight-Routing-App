package org.flight_routing_app.service;

import org.flight_routing_app.model.entities.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RouteFinderServiceTest {

    private final RouteFinderService routeFinderService;

    @Autowired
    public RouteFinderServiceTest(RouteFinderService routeFinderService) {
        this.routeFinderService = routeFinderService;
    }


    @Test
    void testFindCheapestFlight() throws IOException {

        String testOrigin = "SOF";
        String testDestination = "MLE";

        List<Route> routes = this.routeFinderService.findRoutes(testOrigin, testDestination, 2);

        assert(routes.get(0).getTotalPrice()<routes.get(1).getTotalPrice());
    }
    @Test
    void findAllRoutes() throws IOException {
        String testOrigin = "SOF";
        String testDestination1 = "MLE";
        String testDestination2 = "LON";

        List<Route> routes1 = this.routeFinderService.findRoutes(testOrigin, testDestination1, 2);
        List<Route> routes2 = this.routeFinderService.findRoutes(testOrigin, testDestination2, 2);


        assert(routes1.size()==2);
        assert(routes2.size()==1);
    }

    @Test
    void findDirectFlightsOnly() throws IOException {
        String testOrigin = "SOF";
        String testDestination = "MLE";

        List<Route> routes = this.routeFinderService.findRoutes(testOrigin, testDestination, 1);

        assert(routes.size()==1);
        assert(routes.get(0).getRoute().get(0).equals("SOF") && routes.get(0).getRoute().get(1).equals("MLE"));
    }
    @Test
    void ifOriginSameAsDestination() {
        assertThrows(IllegalArgumentException.class, () ->
                routeFinderService.findRoutes("SOF", "SOF", 2)
        );
    }
}
