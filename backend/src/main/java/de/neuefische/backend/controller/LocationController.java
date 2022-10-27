package de.neuefische.backend.controller;

import de.neuefische.backend.model.LocationDTO;
import de.neuefische.backend.model.LocationReturn;
import de.neuefische.backend.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/search")
    public List<LocationReturn> getLocations(@RequestParam String address){
        return locationService.getLocations(address);
    }


}
