package de.neuefische.backend.service;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;




    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }


    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
}
