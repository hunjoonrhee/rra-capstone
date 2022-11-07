package de.neuefische.backend.controller;

import de.neuefische.backend.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/{routeId}")
    public String uploadNewPhoto(@PathVariable String routeId, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return photoService.uploadNewPhoto(routeId, multipartFile);
    }

}
