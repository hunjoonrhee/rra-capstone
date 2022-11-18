package de.neuefische.backend.service;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PhotoServiceTest {

    private final PhotoRepository photoRepository= mock(PhotoRepository.class);

    private final PhotoService photoService = new PhotoService(photoRepository);

    @Test
    void getAllPhotos_ShouldReturn_AllPhotosInRepo(){
        // GIVEN

        Photo dummyPhoto = new Photo("photo1", "name1", "user1", "1");
        when(photoRepository.findAll()).thenReturn(List.of(dummyPhoto));

        // WHEN
        List<Photo> actual = photoService.getAllPhotos();

        // THEN
        assertEquals(List.of(dummyPhoto), actual);
    }


}