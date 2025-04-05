package org.flight_routing_app.model.entities;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Flight {
    private String from;
    private String to;
    private int price;

    public Flight(String from, String to, int price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }
    public Flight(){}
}
