package org.flight_routing_app.controllers;

import org.flight_routing_app.model.dto.RequestDTO;
import org.flight_routing_app.model.entities.Route;
import org.flight_routing_app.service.RouteFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/get_routes")
public class ApiController {

    private final RouteFinderService routeFinderService;

    @Autowired
    public ApiController(RouteFinderService routeFinderService) {

        this.routeFinderService = routeFinderService;
    }

    @PostMapping
    public List<Route> findRoutes(@RequestBody RequestDTO request) throws IOException {

        return routeFinderService.findRoutes(request.getOrigin(), request.getDestination(), request.getMaxFlights());

    }
}
