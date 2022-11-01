package de.neuefische.backend.controller;

import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.model.StartPosition;
import de.neuefische.backend.repository.FoundRouteRepository;
import de.neuefische.backend.service.IdService;
import de.neuefische.backend.service.RouteService;
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
class FoundRouteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FoundRouteRepository foundRouteRepository;
    @MockBean
    private RouteService routeService;


    @Test
    @DirtiesContext
    @WithMockUser(username = "hrhee", password = "ABC123")
    void saveFoundRoutes_ShouldSave_FoundRoutesFromRouteRepo() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        List<Route> fakeFoundRoutes = List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("imageThumbnail")
                        .startPosition(startPosition)
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .build()
        );

        when(routeService.findByRoutesNear(any())).thenReturn(
                fakeFoundRoutes
        );

        String expectedJSON = """
                    {
                         "id": "test",
                         "routes": [
                             {
                                 "id": "1",
                                 "routeName": "test",
                                 "hashtags": [
                                     "tree"
                                 ],
                                 "imageThumbnail": "imageThumbnail",
                                 "startPosition": {
                                     "lat": 2.2,
                                     "lon": 1.1
                                 },
                                 "position": {
                                     "x": 2.2,
                                     "y": 1.1,
                                     "type": "Point",
                                     "coordinates": [
                                         2.2,
                                         1.1
                                     ]
                                 }
                             }
                         ]
                     }
                """;
        // WHEN & THEN

        mockMvc.perform(
                        post("/api/found-routes")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .queryParam("address", "test"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void getAllFoundRoutes_ShouldReturn_AllObjectsInRepo() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        FoundRoutes foundRoutes = new FoundRoutes("testaddress", List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("imageThumbnail")
                        .startPosition(startPosition)
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .build()
        ));
        foundRouteRepository.save(foundRoutes);

        String expectedJSON = """
                    [
                        {
                         "id": "testaddress",
                         "routes": [
                             {
                                 "id": "1",
                                 "routeName": "test",
                                 "hashtags": [
                                     "tree"
                                 ],
                                 "imageThumbnail": "imageThumbnail",
                                 "startPosition": {
                                     "lat": 2.2,
                                     "lon": 1.1
                                 },
                                 "position": {
                                     "x": 2.2,
                                     "y": 1.1,
                                     "type": "Point",
                                     "coordinates": [
                                         2.2,
                                         1.1
                                     ]
                                 }
                             }
                         ]
                     }
                    ]
                    
                """;
        // WHEN & THEN
        mockMvc.perform(
                        get("/api/found-routes"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void getFoundRoutesByAddress_ShouldReturn_FoundRoutesByAddress() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        FoundRoutes foundRoutes = new FoundRoutes("testaddress", List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("imageThumbnail")
                        .startPosition(startPosition)
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .build()
        ));
        foundRouteRepository.save(foundRoutes);

        String expectedJSON = """
                     [
                         {
                             "id": "1",
                             "routeName": "test",
                             "hashtags": [
                                 "tree"
                             ],
                             "imageThumbnail": "imageThumbnail",
                             "startPosition": {
                                 "lat": 2.2,
                                 "lon": 1.1
                             },
                             "position": {
                                 "x": 2.2,
                                 "y": 1.1,
                                 "type": "Point",
                                 "coordinates": [
                                     2.2,
                                     1.1
                                 ]
                             }
                         }
                     ]
                """;
        // WHEN & THEN
        mockMvc.perform(
                        get("/api/found-routes/testaddress"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }
}