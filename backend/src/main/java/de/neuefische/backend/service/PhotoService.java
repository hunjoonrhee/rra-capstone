package de.neuefische.backend.service;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final RouteRepository routeRepository;
    private final IdService idService;




    public PhotoService(PhotoRepository photoRepository, RouteRepository routeRepository, IdService idService) {
        this.photoRepository = photoRepository;
        this.routeRepository = routeRepository;
        this.idService = idService;
    }

    public String uploadNewPhoto(String routeId, String name, MultipartFile multipartFile) throws IOException {

        Photo newPhoto = Photo.builder()
                .id(idService.generateId())
                .name(name)
                .image(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()))
                .build();
        photoRepository.save(newPhoto);


        Route route;
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        if(optionalRoute.isPresent()){
            route = optionalRoute.get();
            List<Photo> photos = route.getPhotos();
            photos.add(newPhoto);
            route.setPhotos(photos);

            routeRepository.save(route);
        }
        return newPhoto.getId();
    }


    public List<Photo> getPhotosByRouteId(String routeId) {
        Route route;
        List<Photo> photos = new ArrayList<>();
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        if(optionalRoute.isPresent()){
            route = optionalRoute.get();
            photos = route.getPhotos();
        }
        return photos;
    }

//    public String getPhotoForGivenRouteId(String routeId, String photoId) {
//    }
}
