package de.neuefische.backend.service;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final RouteRepository routeRepository;
    private final String uploadDirectory = System.getProperty("user.dir")+"/frontend/src/images/uploads";



    public PhotoService(PhotoRepository photoRepository, RouteRepository routeRepository) {
        this.photoRepository = photoRepository;
        this.routeRepository = routeRepository;
    }

    public String uploadNewPhoto(String routeId, MultipartFile multipartFile) throws IOException {
        File directoryName = new File(Path.of(uploadDirectory).toUri());
        File directory = new File(directoryName.toURI());
        if(!directory.exists()){
            directory.mkdir();
        }

        Path fileNameAndPath = Paths.get(uploadDirectory, multipartFile.getOriginalFilename());

        Files.write(fileNameAndPath, multipartFile.getBytes());

        Photo newPhoto = Photo.builder()
                .routeId(routeId)
                .photoName(multipartFile.getOriginalFilename())
                .build();
        photoRepository.save(newPhoto);


        Route route;
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        if(optionalRoute.isPresent()){
            route = optionalRoute.get();
            List<Photo> photoURLs = route.getPhotos();
            photoURLs.add(newPhoto);
            route.setPhotos(photoURLs);

            routeRepository.save(route);
        }
        return fileNameAndPath.toString();
    }


}
