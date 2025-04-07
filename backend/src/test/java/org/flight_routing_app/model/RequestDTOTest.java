package org.flight_routing_app.model;

import org.flight_routing_app.model.dto.RequestDTO;
import org.flight_routing_app.service.RouteFinderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestDTOTest {

    private final MockMvc mockMvc;

    @Autowired
    public RequestDTOTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testingValidationForBlankOrigin() throws Exception {

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "   ",
                              "destination": "FDJ",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.origin").value("Origin must not be blank"));

    }
    @Test
    public void testingValidationForBlankDestination() throws Exception {

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "SOF",
                              "destination": "   ",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.destination").value("Destination must not be blank"));

    }
    @Test
    public void testingValidationForSizeDestination() throws Exception {

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "SOF",
                              "destination": "DESTINATION",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.destination").value("The destination code must be 3 uppercase letters long"));

    }
    @Test
    public void testingValidationForSizeOrigin() throws Exception {

        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "ORIGIN",
                              "destination": "SOF",
                              "maxFlights": 3
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.origin").value("The origin code must be 3 uppercase letters long"));

    }

    @Test
    public void testingValidationForInvalidMaxFlights() throws Exception {


        this.mockMvc.perform(post("/api/get_routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "origin": "MLE",
                              "destination": "SOF",
                              "maxFlights": 0
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.maxFlights").value("The value should be at least 1"));
    }

}
