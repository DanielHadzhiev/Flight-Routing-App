package org.flight_routing_app.controllers;

import org.flight_routing_app.model.entities.Route;
import org.flight_routing_app.service.RouteFinderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private final RouteFinderService routeFinderService;

    @Autowired
    public ApiControllerTest(RouteFinderService routeFinderService,MockMvc mockMvc) {
        this.routeFinderService = routeFinderService;
        this.mockMvc = mockMvc;
    }

    @Test
    public void testFindRoutesSuccess() throws Exception {

        String origin = "SOF";
        String destination = "MLE";
        int maxFlights = 3;

        Route route1 = new Route(List.of("SOF", "LON", "MLE"), 30);
        Route route2 = new Route(List.of("SOF", "MLE"), 70);
        List<Route> routes = Arrays.asList(route1, route2);

        when(routeFinderService.findRoutes(origin, destination, maxFlights)).thenReturn(routes);

        mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"SOF\",\"destination\":\"MLE\",\"maxFlights\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].route[0]").value("SOF"))
                .andExpect(jsonPath("$[0].route[1]").value("LON"))
                .andExpect(jsonPath("$[0].route[2]").value("MLE"))
                .andExpect(jsonPath("$[0].totalPrice").value(30))
                .andExpect(jsonPath("$[1].route[0]").value("SOF"))
                .andExpect(jsonPath("$[1].route[1]").value("MLE"))
                .andExpect(jsonPath("$[1].totalPrice").value(70));

        verify(routeFinderService, times(1)).findRoutes(origin, destination, maxFlights);
    }

    @Test
    public void whenNoRoutesFound_thenReturnNotFound() throws Exception {
        String origin = "SOF";
        String destination = "FDJ";
        int maxFlights = 3;

        List<Route> routes = new ArrayList<>();
        when(routeFinderService.findRoutes(origin, destination, maxFlights)).thenReturn(routes);

        mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"SOF\",\"destination\":\"FDJ\",\"maxFlights\":3}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No routes found"));

        verify(routeFinderService, times(1)).findRoutes(origin, destination, maxFlights);
    }
}
