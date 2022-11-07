package de.neuefische.backend.controller;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/add/{routeId}")
    public String uploadNewPhoto(@PathVariable String routeId, @RequestParam("name") String name,
                                 @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return photoService.uploadNewPhoto(routeId, name, multipartFile);
    }

    @GetMapping("/{routeId}")
    public List<Photo> getPhotosByRouteId(@PathVariable String routeId){
        return photoService.getPhotosByRouteId(routeId);
    }
//    @GetMapping("/{routeId}/{photoId}")
//    public String getPhotoForGivenRouteId(@PathVariable String routeId, @PathVariable String photoId){
//        return photoService.getPhotoForGivenRouteId(routeId, photoId);
//    }

}
