package de.neuefische.backend.controller;

import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.service.FoundRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/found-routes")
public class FoundRouteController {

    private final FoundRouteService foundRouteService;

    @Autowired
    public FoundRouteController(FoundRouteService foundRouteService) {
        this.foundRouteService = foundRouteService;
    }

    @GetMapping
    public List<FoundRoutes> getAllFoundRoutes(){
        return foundRouteService.getAllFoundRoutes();
    }

    @PostMapping
    public FoundRoutes saveFoundRoutes(@RequestParam String address){
        return foundRouteService.saveFoundRoutes(address);
    }
}
