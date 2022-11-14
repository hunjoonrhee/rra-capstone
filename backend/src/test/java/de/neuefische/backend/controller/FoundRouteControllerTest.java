package de.neuefische.backend.controller;

import de.neuefische.backend.model.EndPosition;
import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.model.StartPosition;
import de.neuefische.backend.service.FoundRouteService;
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

import java.util.ArrayList;
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
    @MockBean
    private RouteService routeService;
    @MockBean
    private FoundRouteService foundRouteService;


    @Test
    @DirtiesContext
    @WithMockUser(username = "hrhee", password = "ABC123")
    void saveFoundRoutes_ShouldSave_FoundRoutesFromRouteRepo() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        List<Route> fakeFoundRoutes = List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("test1")
                        .startPosition(startPosition)
                        .betweenPositions(new ArrayList<>())
                        .endPosition(endPosition)
                        .routes(null)
                        .photos(new ArrayList<>())
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .createdBy("user1")
                        .build()
        );

        when(routeService.findByRoutesNear(any())).thenReturn(
                fakeFoundRoutes
        );
        FoundRoutes foundRoutes = new FoundRoutes("test", fakeFoundRoutes);
        when(foundRouteService.saveFoundRoutes("test")).thenReturn(foundRoutes);

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
                                        "imageThumbnail": "test1",
                                        "startPosition": {
                                            "lat": 2.2,
                                            "lon": 1.1
                                        },
                                        "betweenPositions":[
                                        ],
                                        "endPosition": {
                                            "lat": 2.3,
                                            "lon": 1.12
                                        },
                                        "routes": null,
                                        "photos":[
                                        ],
                                        "position": {
                                            "x": 2.2,
                                            "y": 1.1,
                                            "type": "Point",
                                            "coordinates": [
                                                2.2,
                                                1.1
                                            ]
                                        },
                                        "createdBy":"user1"
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
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        FoundRoutes foundRoutes = new FoundRoutes("test", List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("test1")
                        .startPosition(startPosition)
                        .betweenPositions(new ArrayList<>())
                        .endPosition(endPosition)
                        .routes(null)
                        .photos(new ArrayList<>())
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .createdBy("user1")
                        .build()
        ));
        when(foundRouteService.getAllFoundRoutes()).thenReturn(List.of(foundRoutes));

        String expectedJSON = """
                    [
                        {
                         "id": "test",
                         "routes": [
                                    {
                                        "id": "1",
                                        "routeName": "test",
                                        "hashtags": [
                                            "tree"
                                        ],
                                        "imageThumbnail": "test1",
                                        "startPosition": {
                                            "lat": 2.2,
                                            "lon": 1.1
                                        },
                                        "betweenPositions":[
                                        ],
                                        "endPosition": {
                                            "lat": 2.3,
                                            "lon": 1.12
                                        },
                                        "routes": null,
                                        "photos":[
                                        ],
                                        "position": {
                                            "x": 2.2,
                                            "y": 1.1,
                                            "type": "Point",
                                            "coordinates": [
                                                2.2,
                                                1.1
                                            ]
                                        },
                                        "createdBy":"user1"
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
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        List<Route> fakeFoundRoutes = List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("test1")
                        .startPosition(startPosition)
                        .betweenPositions(new ArrayList<>())
                        .endPosition(endPosition)
                        .routes(null)
                        .photos(new ArrayList<>())
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .createdBy("user1")
                        .build()
        );
        when(foundRouteService.getFoundRoutesByAddress("test")).thenReturn(fakeFoundRoutes);

        String expectedJSON = """
                            [
                                    {
                                        "id": "1",
                                        "routeName": "test",
                                        "hashtags": [
                                            "tree"
                                        ],
                                        "imageThumbnail": "test1",
                                        "startPosition": {
                                            "lat": 2.2,
                                            "lon": 1.1
                                        },
                                        "betweenPositions":[
                                        ],
                                        "endPosition": {
                                            "lat": 2.3,
                                            "lon": 1.12
                                        },
                                        "routes": null,
                                        "photos":[
                                        ],
                                        "position": {
                                            "x": 2.2,
                                            "y": 1.1,
                                            "type": "Point",
                                            "coordinates": [
                                                2.2,
                                                1.1
                                            ]
                                        },
                                        "createdBy":"user1"
                                    }
                                
                                ]


                """;
        // WHEN & THEN
        mockMvc.perform(
                        get("/api/found-routes/routes?id=test"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }
}