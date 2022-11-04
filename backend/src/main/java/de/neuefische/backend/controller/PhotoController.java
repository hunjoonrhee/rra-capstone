package de.neuefische.backend.controller;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.model.PhotoDTO;
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

    @PostMapping("/{routeId}")
    public Photo uploadNewPhoto(@PathVariable String routeId, @RequestBody PhotoDTO photoDTO){
        return photoService.uploadNewPhoto(routeId, photoDTO);
    }

    @GetMapping("/{routeId}")
    public List<Photo> getAllPhotosByRouteId(@PathVariable String routeId){
        return photoService.getAllPhotosByRouteId(routeId);
    }
}
