package org.flight_routing_app.exceptions;

public class NoRoutesException extends RuntimeException {
    public NoRoutesException(String message) {
        super(message);
    }
}
