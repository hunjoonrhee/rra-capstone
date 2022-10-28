package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RouteServiceTest {

    private final RouteRepository routeRepository = mock(RouteRepository.class);
    private final IdService idService = mock(IdService.class);
    private final LocationService locationService = mock(LocationService.class);

    private final RouteService routeService = new RouteService(routeRepository,
            idService, locationService);

    @Test
    void addNewRoute_ShouldReturn_AddedRoute(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        RouteDTO routeDTO = new RouteDTO("routeName", hashtags, "imageThumbnail", startPosition);
        when(idService.generateId()).thenReturn("1");
        when(routeRepository.save(any())).thenReturn(
                Route.builder()
                        .id("1")
                        .routeName(routeDTO.getRouteName())
                        .hashtags(routeDTO.getHashtags())
                        .imageThumbnail(routeDTO.getImageThumbnail())
                        .startPosition(routeDTO.getStartPosition())
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .build()
        );
        // WHEN
        Route actual = routeService.addNewRoute(routeDTO);

        // THEN
        Route expected = Route.builder()
                .id("1")
                .routeName(routeDTO.getRouteName())
                .hashtags(routeDTO.getHashtags())
                .imageThumbnail(routeDTO.getImageThumbnail())
                .startPosition(routeDTO.getStartPosition())
                .position(new GeoJsonPoint(2.2, 1.1))
                .build();
        assertEquals(expected, actual);
    }

}