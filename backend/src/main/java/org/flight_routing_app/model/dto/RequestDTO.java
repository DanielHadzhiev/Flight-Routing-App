package org.flight_routing_app.model.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {

    @NotBlank(message = "Origin must not be blank")
    @Size(min = 3,max = 3,message = "The origin code must be 3 uppercase letters long")
    private String origin;

    @NotBlank(message = "Destination must not be blank")
    @Size(min = 3,max = 3,message = "The destination code must be 3 uppercase letters long")
    private String destination;

    @Min(value = 1,message = "The value should be at least 1")
    private int maxFlights;

    public RequestDTO(String origin, String destination, int maxFlights) {
        this.origin = origin;
        this.destination = destination;
        this.maxFlights = maxFlights;
    }

    public RequestDTO() {}
}
