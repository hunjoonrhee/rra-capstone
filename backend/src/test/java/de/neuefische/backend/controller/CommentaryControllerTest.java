package de.neuefische.backend.controller;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.AppUserRepository;
import de.neuefische.backend.repository.RouteRepository;
import de.neuefische.backend.service.CommentaryService;
import de.neuefische.backend.service.IdService;
import de.neuefische.backend.service.TimeStampService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class CommentaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentaryService commentaryService;
    @MockBean
    private IdService idService;

    @MockBean
    private TimeStampService timeStampService;
    @MockBean
    private RouteRepository routeRepository;
    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void addNewComment_ShouldReturn_AddedCommentary() throws Exception {
        // GIVEN
        when(idService.generateId()).thenReturn("c1");
        when(timeStampService.getCurrentTime()).thenReturn("current-Time");

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser user = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById("user1")).thenReturn(Optional.of(user));

        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        List<Photo> photos = new ArrayList<>();
        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));

        CommentaryDTO commentaryDTO = new CommentaryDTO("Test comment", "1");
        Commentary commentary = Commentary.builder()
                .id(idService.generateId())
                .routeId(commentaryDTO.getRouteId())
                .message(commentaryDTO.getMessage())
                .postedBy(user)
                .timeStamp(timeStampService.getCurrentTime())
                .build();

        when(commentaryService.addANewComment("1", "user1", commentaryDTO)).thenReturn(commentary);

        String requestBody = """
                    {
                        "message":"Test comment",
                        "routeId":"1"
                    }
                """;

        String expectedJSON = """
                    
                       {
                            "id":"c1",
                            "message":"Test comment",
                            "routeId": "1",
                            "postedBy":
                                {
                                "username":"user1",
                                "passwordHash":  "xxx",
                                "roles": ["USER"]
                                },
                            "timeStamp": "current-Time"
                        }
                         
                    
                """;

        //WHEN THEN
        mockMvc.perform(
                        post("/api/comments/1?user=user1")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                                .andExpect(status().isOk())
                                .andExpect(content().json(expectedJSON));

    }

    @Test
    void getAllCommentaries_ShouldReturn_AllCommentariesInRepo() throws Exception {
        // GIVEN
        when(idService.generateId()).thenReturn("c1");
        when(timeStampService.getCurrentTime()).thenReturn("current-Time");

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser user = new AppUser("user1", "xxx", roles);
        when(appUserRepository.findById("user1")).thenReturn(Optional.of(user));

        CommentaryDTO commentaryDTO = new CommentaryDTO("Test comment", "1");
        Commentary commentary = Commentary.builder()
                .id(idService.generateId())
                .routeId(commentaryDTO.getRouteId())
                .message(commentaryDTO.getMessage())
                .postedBy(user)
                .timeStamp(timeStampService.getCurrentTime())
                .build();
        when(commentaryService.getAllCommentaries()).thenReturn(List.of(commentary));

        String expectedJSON = """
                    [
                       {
                            "id":"c1",
                            "message":"Test comment",
                            "routeId": "1",
                            "postedBy":
                                {
                                "username":"user1",
                                "passwordHash":  "xxx",
                                "roles": ["USER"]
                                },
                            "timeStamp": "current-Time"
                        }
                    ]
                    
                """;


        //WHEN THEN
        mockMvc.perform(
                        get("/api/comments/"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }



}