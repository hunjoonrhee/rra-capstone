package de.neuefische.backend.controller;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.service.PhotoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/add/{routeId}")
    public String uploadNewPhoto(@PathVariable String routeId, @RequestParam("name") String name)  {
        return photoService.uploadNewPhoto(routeId, name);
    }

    @GetMapping("/{routeId}")
    public List<Photo> getPhotosByRouteId(@PathVariable String routeId){
        return photoService.getPhotosByRouteId(routeId);
    }

}
