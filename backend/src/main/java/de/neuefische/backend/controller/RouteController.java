package de.neuefische.backend.controller;

import de.neuefische.backend.model.*;
import de.neuefische.backend.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/route")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<Route> getAllRoutesInRepo(){
        return routeService.getAllRoutesInRepo();
    }

    @PostMapping
    public Route addNewRoute(@RequestBody RouteDTO routeDTO){
        return routeService.addNewRoute(routeDTO);
    }

    @GetMapping("/routes")
    public List<Route> findByRoutesNear(@RequestParam String address){
        return routeService.findByRoutesNear(address);
    }

    @DeleteMapping("/{id}")
    public void deleteRouteById(@PathVariable String id, @RequestParam String address){
        routeService.deleteRouteById(id, address);
    }

    @GetMapping("/photos/{id}")
    public List<Photo> getPhotosOfRoute(@PathVariable String id){
        return routeService.getPhotosOfRoute(id);
    }
    @PostMapping("/photos/{id}")
    public Photo addANewPhotoForRoute(@PathVariable String id, @RequestBody PhotoDTO photoDTO){
        return routeService.addANewPhotoForRoute(id, photoDTO);
    }
    @DeleteMapping("/photos/{id}")
    public void deletePhotoOfRoute(@PathVariable String id, @RequestParam String photoId){
        routeService.deletePhotoOfRoute(id, photoId);
    }

    @GetMapping("/comments/{id}")
    public List<Commentary> getAllCommentariesOfRoute(@PathVariable String id){
        return routeService.getAllCommentariesOfRoute(id);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteCommentaryOfRoute(@PathVariable String id, @RequestParam String commentaryId){
        routeService.deleteCommentaryOfRoute(id, commentaryId);
    }


}


