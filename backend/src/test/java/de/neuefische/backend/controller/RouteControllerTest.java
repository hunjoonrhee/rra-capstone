package de.neuefische.backend.controller;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.FoundRouteRepository;
import de.neuefische.backend.repository.RouteRepository;
import de.neuefische.backend.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RouteRepository routeRepository;
    @MockBean
    private FoundRouteRepository foundRouteRepository;
    @MockBean
    private IdService idService;
    @MockBean
    private GeoJsonPointService geoJsonPointService;
    @Autowired
    private MongoTemplate template;
    @MockBean
    private LocationService locationService;
    @MockBean
    private RoutesService routesService;



    @Test
    void findByRoutesNear_ShouldReturn_RouteItSelf() throws Exception {
        //SETUP
        template
                .indexOps(Route.class)
                .ensureIndex(new GeospatialIndex("position").typed(GeoSpatialIndexType.GEO_2DSPHERE));
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        when(geoJsonPointService.createGeoJsonPoint(anyDouble(), anyDouble())).thenReturn(new GeoJsonPoint(2.2, 1.1));
        Route fakeRoute = Route.builder()
                .id("1")
                .routeName("test")
                .imageThumbnail("test")
                .hashtags(hashtags)
                .startPosition(new StartPosition(2.2, 1.1))
                .position(new GeoJsonPoint(2.2, 1.1))
                .build();

        LocationReturn fakeLocation = LocationReturn.builder()
                .lat(2.2)
                .lon(1.1)
                .display_name("test")
                .osm_id("test")
                .address(new AddressDTO("xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx"))
                .build();

        when(idService.generateId()).thenReturn("1");
        when(locationService.getLocations(any())).thenReturn(
                List.of(fakeLocation)
        );
        when(routeRepository.findByPositionNear(any(Point.class), any(Distance.class))).thenReturn(
                List.of(fakeRoute)
        );

        routeRepository.save(fakeRoute);

        String expectedJSON = """
                    [
                        {
                            "id": "1",
                            "routeName": "test",
                            "hashtags": ["tree"],
                            "imageThumbnail": "test",
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


        //WHEN THEN

        mockMvc.perform(
                        get("/api/route/routes").queryParam("address", "test"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void addNewRoute_ShouldReturn_AddedRouteInJsonBody() throws Exception {
        // GIVEN
        when(idService.generateId()).thenReturn("1");
        when(geoJsonPointService.createGeoJsonPoint(anyDouble(), anyDouble())).thenReturn(new GeoJsonPoint(49.4543507, 11.0873597));
        when(routesService.getRoutes(any(), any(), any())).thenReturn(null);
        String requestBody = """
                        {
                          "routeName": "Jogging by Wöhrder See",
                          "hashtags": [
                            "tree",
                            "river"
                          ],
                          "imageThumbnail": "https://mapio.net/images-p/10982408.jpg",
                          "startPosition": {
                            "lat": 49.4543507,
                            "lon": 11.0873597
                          },
                          "betweenPositions":[
                            {
                            "lat": 49.4543507,
                            "lon": 11.0873597
                          },
                          {
                            "lat": 49.4543507,
                            "lon": 11.0873597
                          }
                          ],
                          "endPosition": {
                            "lat": 49.4543507,
                            "lon": 11.0873597
                          },
                          "createdBy":"user1"
                        }
                """;

        String expectedJSON = """
                        {
                            "id": "1",
                            "routeName": "Jogging by Wöhrder See",
                            "hashtags": [
                                "tree",
                                "river"
                            ],
                            "imageThumbnail": "https://mapio.net/images-p/10982408.jpg",
                            "startPosition": {
                                "lat": 49.4543507,
                                "lon": 11.0873597
                            },
                            "betweenPositions":[
                                {
                                "lat": 49.4543507,
                                "lon": 11.0873597
                              },
                              {
                                "lat": 49.4543507,
                                "lon": 11.0873597
                              }
                            ],
                            "endPosition": {
                                "lat": 49.4543507,
                                "lon": 11.0873597
                            },
                            "routes": null,
                            "position": {
                                "x": 49.4543507,
                                "y": 11.0873597,
                                "type": "Point",
                                "coordinates": [
                                    49.4543507,
                                    11.0873597
                                ]
                            },
                            "createdBy":"user1"
                        }
                
                """;
        //WHEN THEN

        mockMvc.perform(
                        post("/api/route")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }

    @Test
    void getAllRoutes_ShouldReturn_AllRoutes() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        Route dummyRoute = new Route("1", "Jogging by Wöhrder See", hashtags, "https://mapio.net/images-p/10982408.jpg", startPosition,
                new ArrayList<>(), endPosition, null, null, new GeoJsonPoint(2.2, 1.1), "user1", new ArrayList<>());
        when(routeRepository.findAll()).thenReturn(List.of(dummyRoute));

        String expectedJSON = """
                    [
                        {
                            "id": "1",
                            "routeName": "Jogging by Wöhrder See",
                            "hashtags": [
                                "tree"
                            ],
                            "imageThumbnail": "https://mapio.net/images-p/10982408.jpg",
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
                            "photos": null,
                            "position": {
                                "x": 2.2,
                                "y": 1.1,
                                "type": "Point",
                                "coordinates": [
                                    2.2,
                                    1.1
                                ]
                            },
                            "createdBy": "user1",
                            "commentaries": []
                        }
                    ]
                """;

        //WHEN THEN

        mockMvc.perform(
                        get("/api/route/"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }
    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void deleteRouteById_ShouldDelete_RouteByGivenId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        Route dummyRoute = new Route("1", "Jogging by Wöhrder See", hashtags, "https://mapio.net/images-p/10982408.jpg", startPosition,
                new ArrayList<>(), endPosition, null, null, new GeoJsonPoint(2.2, 1.1), "user1", new ArrayList<>());

        List<Route> dummyRoutes = new ArrayList<>();
        dummyRoutes.add(dummyRoute);
        FoundRoutes foundRoutes = new FoundRoutes("address", dummyRoutes);

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        when(foundRouteRepository.findById("address")).thenReturn(Optional.of(foundRoutes));


        //WHEN THEN
        mockMvc.perform(
                        delete("/api/route/1").queryParam("address", "address"))
                .andExpect(status().isOk());
    }

    @Test
    void getPhotosOfRoute_ShouldReturn_PhotosByGivenRouteId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("photo1", "photo", "user1", "1"));

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));

        String expectedJSON = """
                    [
                       {
                            "id":"photo1",
                            "name":"photo",
                            "uploadedBy":"user1",
                            "routeId":"1"
                        }
                            
                    ]
                """;

        //WHEN THEN
        mockMvc.perform(
                        get("/api/route/photos/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }
    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void addANewPhotoOfRoute_ShouldReturn_AddedPhotoByGivenRouteId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        when(idService.generateId()).thenReturn("photo1");
        List<Photo> photos = new ArrayList<>();

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        String requestBody = """
                        {
                            "name":"photo",
                            "uploadedBy":"user1",
                            "routeId":"1"
                        }
                """;

        String expectedJSON = """
                    
                       {
                            "id":"photo1",
                            "name":"photo",
                            "uploadedBy":"user1",
                            "routeId":"1"
                        }
                         
                    
                """;

        //WHEN THEN
        mockMvc.perform(
                        post("/api/route/photos/1")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }

    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void deletePhotoById_ShouldDelete_PhotoByGivenPhotoId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("photo1", "photo", "user1", "1"));

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));


        //WHEN THEN
        mockMvc.perform(
                        delete("/api/route/photos/1").queryParam("photoId", "photo1"))
                .andExpect(status().isOk());
    }
    @Test
    void getCommentaryOfRoute_ShouldReturn_CommentariesByGivenRouteId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser user = new AppUser("user1", "xxx", roles);

        List<Commentary> commentaries = new ArrayList<>();
        commentaries.add(new Commentary("c1", "comment1", "1", user, "xx"));

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, new ArrayList<>(), new GeoJsonPoint(2.2, 1.1), "user1",  commentaries);

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));

        String expectedJSON = """
                    [
                       {
                            "id":"c1",
                            "message":"comment1",
                            "postedBy":{
                                "username": "user1",
                                "passwordHash": "xxx",
                                "roles": ["USER"]
                            },
                            "routeId":"1"
                        }
                            
                    ]
                """;

        //WHEN THEN
        mockMvc.perform(
                        get("/api/route/comments/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }
    @Test
    @WithMockUser(username = "hrhee", password = "ABC123")
    void deleteCommentaryById_ShouldDelete_CommentaryByGivenCommentaryId() throws Exception {
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser user = new AppUser("user1", "xxx", roles);

        List<Commentary> commentaries = new ArrayList<>();
        commentaries.add(new Commentary("c1", "comment1", "1", user, "xx"));

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, new ArrayList<>(), new GeoJsonPoint(2.2, 1.1), "user1",  commentaries);

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));


        //WHEN THEN
        mockMvc.perform(
                        delete("/api/route/comments/1").queryParam("commentaryId", "c1"))
                .andExpect(status().isOk());
    }
}