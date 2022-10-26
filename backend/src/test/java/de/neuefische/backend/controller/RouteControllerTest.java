package de.neuefische.backend.controller;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.RouteRepository;
import de.neuefische.backend.service.GeoJsonPointService;
import de.neuefische.backend.service.IdService;
import de.neuefische.backend.service.LocationService;
import de.neuefische.backend.service.RouteService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RouteRepository routeRepository;
    @MockBean
    private IdService idService;
    @MockBean
    private GeoJsonPointService geoJsonPointService;
    @Autowired
    private MongoTemplate template;
    @MockBean
    private LocationService locationService;



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
    void addNewRoute_ShouldReturn_AddedRouteInJsonBody() throws Exception {
        // GIVEN
        when(idService.generateId()).thenReturn("1");
        when(geoJsonPointService.createGeoJsonPoint(anyDouble(), anyDouble())).thenReturn(new GeoJsonPoint(49.4543507, 11.0873597));
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
                          }
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
                            "position": {
                                "x": 49.4543507,
                                "y": 11.0873597,
                                "type": "Point",
                                "coordinates": [
                                    49.4543507,
                                    11.0873597
                                ]
                            }
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
}