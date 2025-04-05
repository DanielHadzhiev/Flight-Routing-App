package org.flight_routing_app.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {

    private String origin;

    private String destination;

    private int maxFlights;

    public RequestDTO(String origin, String destination, int maxFlights) {
        this.origin = origin;
        this.destination = destination;
        this.maxFlights = maxFlights;
    }

    public RequestDTO() {}
}
