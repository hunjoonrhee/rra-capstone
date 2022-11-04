package de.neuefische.backend.controller;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.service.IdService;
import de.neuefische.backend.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhotoRepository photoRepository;

    @MockBean
    private IdService idService;

    @MockBean
    private PhotoService photoService;

    @Test
    void uploadNewPhoto_ShouldReturn_UploadedPhoto() throws Exception {
        // GIVEN
        when(idService.generateId()).thenReturn("1");
        String routeId = "11";
        when(photoService.uploadNewPhoto(any(), any())).thenReturn(new Photo("1", routeId, "url"));


        String requestBody = """
                        {
                            "photoURL":"url"
                        }
                """;

        String expectedJSON = """
                        {
                            "id":"1",
                            "routeId":"11",
                            "photoURL":"url"
                        }
                """;

        // WHEN, THEN
        mockMvc.perform(
                        post("/api/photo/"+routeId)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }

    @Test
    void getAllPhotosByRouteId_ShouldReturn_AllPhotosByRouteId() throws Exception {
        Photo dummyPhoto = new Photo("1", "11", "url");
        Photo dummyPhoto2 = new Photo("2", "11", "url");
        String routeId = "11";
        photoRepository.save(dummyPhoto2);
        photoRepository.save(dummyPhoto);

        when(photoService.getAllPhotosByRouteId(any())).thenReturn(List.of(dummyPhoto2, dummyPhoto));

        String expectedJSON = """
                    [
                        {
                            "id":"1",
                            "routeId":"11",
                            "photoURL":"url"
                        },
                        {
                            "id":"2",
                            "routeId":"11",
                            "photoURL":"url"
                        }
                    ]
                """;

        mockMvc.perform(
                        get("/api/photo/"+routeId))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }
}