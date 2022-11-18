package de.neuefische.backend.controller;

import de.neuefische.backend.model.Photo;
import de.neuefische.backend.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhotoService photoService;

    @Test
    void getAllPhotos_ShouldReturn_AllPhotosInRepo() throws Exception {
        // GIVEN
        Photo dummyPhoto = new Photo("photo1", "name1", "user1", "1");

        when(photoService.getAllPhotos()).thenReturn(List.of(dummyPhoto));

        String expectedJSON = """
                    [
                       {
                            "id":"photo1",
                            "name":"name1",
                            "uploadedBy":"user1",
                            "routeId":"1"
                        }
                            
                    ]
                """;

        //WHEN THEN
        mockMvc.perform(
                        get("/api/photo/"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }


}