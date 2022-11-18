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

    @GetMapping()
    public List<Photo> getAllPhotos(){
        return photoService.getAllPhotos();
    }

}
