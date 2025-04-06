package org.flight_routing_app.controllers;


import org.flight_routing_app.exceptions.NoRoutesException;
import org.flight_routing_app.service.RouteFinderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {


    private final MockMvc mockMvc;

    @MockitoBean
    private final RouteFinderService routeFinderService;

    @Autowired
    public GlobalExceptionHandlerTest(MockMvc mockMvc,
                                      RouteFinderService routeFinderService) {
        this.mockMvc = mockMvc;
        this.routeFinderService = routeFinderService;
    }

    @Test
    public void whenUnexpectedExceptionThenInternalServerErrorWithMessage() throws Exception {
        String origin = "SOF";
        String destination = "MLE";
        int maxFlights = 3;

        when(this.routeFinderService.findRoutes(origin, destination, maxFlights))
                .thenThrow(new RuntimeException("Something went wrong"));

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "SOF",
                              "destination": "MLE",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unexpected error happened"));

        verify(this.routeFinderService, times(1)).findRoutes(origin, destination, maxFlights);
    }

    @Test
    public void whenInvalidInputThenBadRequestWithValidationErrors() throws Exception {
        String invalidRequestBody = """
            {
              "destination": "MLE",
              "maxFlights": 0
            }
            """;

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.origin").value("Origin must not be blank"))
                .andExpect(jsonPath("$.errors.maxFlights").value("The value should be at least 1"));
    }

    @Test
    public void whenNoRoutesFoundThenReturnNotFoundWithMessage() throws Exception {
        String origin = "SOF";
        String destination = "FDJ";
        int maxFlights = 3;

        when(routeFinderService.findRoutes(origin, destination, maxFlights))
                .thenThrow(new NoRoutesException("No routes found"));

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "SOF",
                              "destination": "FDJ",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No routes found"));

        verify(this.routeFinderService, times(1)).findRoutes(origin, destination, maxFlights);
    }


}
