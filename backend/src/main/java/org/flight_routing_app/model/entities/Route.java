package org.flight_routing_app.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Route {

     private List<String> route;
     private int totalPrice;

    public Route(List<String> route, int totalPrice) {
        this.route = route;
        this.totalPrice = totalPrice;
    }
    public Route(){}

    @Override
    public String toString() {
        return route + ", " + totalPrice;
    }
}
