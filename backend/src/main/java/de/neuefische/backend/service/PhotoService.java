package de.neuefische.backend.service;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.model.PhotoDTO;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.springframework.stereotype.Service;

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

    public Photo uploadNewPhoto(String routeId, PhotoDTO photoDTO) {
        Photo newPhoto = Photo.builder()
                .id(idService.generateId())
                .routeId(routeId)
                .photoURL(photoDTO.getPhotoURL())
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
        return newPhoto;
    }

    public List<Photo> getAllPhotosByRouteId(String routeId) {
        List<Photo> photosOfRoute = new ArrayList<>();
        List<Photo> allPhotos = photoRepository.findAll();

        for(Photo photo:allPhotos){
            if(photo.getRouteId().equals(routeId)){
                photosOfRoute.add(photo);
            }
        }
        return photosOfRoute;
    }

}
