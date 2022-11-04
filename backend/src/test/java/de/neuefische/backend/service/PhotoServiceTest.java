package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PhotoServiceTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final IdService idService = mock(IdService.class);
    private final RouteRepository routeRepository = mock(RouteRepository.class);

    private final PhotoService photoService = new PhotoService(photoRepository, routeRepository, idService);

    @Test
    void uploadNewPhoto_ShouldReturn_UploadedPhoto(){
        // GIVEN
        PhotoDTO photoDTO = new PhotoDTO("url");
        String routeId = "11";
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);


        when(idService.generateId()).thenReturn("1");
        when(photoRepository.save(any())).thenReturn(
                Photo.builder().id("1").routeId(routeId).photoURL(photoDTO.getPhotoURL()).build()
        );
        when(routeRepository.findById(any())).thenReturn(
                Optional.of(new Route("11", "routeName", hashtags, "imageThumbnail", startPosition,
                endPosition, null, new ArrayList<>(), new GeoJsonPoint(2.2, 1.1))));

        // WHEN
        Photo actual = photoService.uploadNewPhoto(routeId, photoDTO);

        // THEN
        Photo expected = new Photo("1", "11", photoDTO.getPhotoURL());
        assertEquals(expected, actual);
    }

    @Test
    void getAllPhotosByRouteId_ShouldReturn_ListOfPhotosOfRoute(){
        //GIVEN
        Photo dummyPhoto = new Photo("1", "3", "url");
        photoRepository.save(dummyPhoto);
        when(photoRepository.findAll()).thenReturn(List.of(dummyPhoto));
        String routeId = "3";

        //WHEN
        List<Photo> actual = photoService.getAllPhotosByRouteId(routeId);

        // THEN
        List<Photo> expected = List.of(dummyPhoto);
        assertEquals(expected, actual);

    }

}