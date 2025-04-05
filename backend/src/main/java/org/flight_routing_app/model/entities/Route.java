package org.flight_routing_app.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Route {

     private List<String> cities;
     private int totalPrice;

    public Route(List<String> cities, int totalPrice) {
        this.cities = cities;
        this.totalPrice = totalPrice;
    }
    public Route(){}

    @Override
    public String toString() {
        return cities + ", " + totalPrice;
    }
}
