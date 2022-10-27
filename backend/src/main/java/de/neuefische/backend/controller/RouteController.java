package de.neuefische.backend.controller;

import de.neuefische.backend.model.Route;
import de.neuefische.backend.model.RouteDTO;
import de.neuefische.backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/route")
public class RouteController {

    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public Route addNewRoute(@RequestBody RouteDTO routeDTO){
        return routeService.addNewRoute(routeDTO);
    }

    @GetMapping("/routes")
    public List<Route> findByRoutesNear(@RequestParam String address){
        return routeService.findByRoutesNear(address);
    }

}
