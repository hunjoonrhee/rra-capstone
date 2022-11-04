package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RouteServiceTest {

    private final RouteRepository routeRepository = mock(RouteRepository.class);
    private final IdService idService = mock(IdService.class);
    private final LocationService locationService = mock(LocationService.class);
    private final PhotoService photoService = mock(PhotoService.class);

    private final RoutesService routesService = mock(RoutesService.class);

    private final RouteService routeService = new RouteService(routeRepository,
            idService, locationService, photoService, routesService);

    @Test
    void addNewRoute_ShouldReturn_AddedRoute(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        RouteDTO routeDTO = new RouteDTO("routeName", hashtags, "imageThumbnail", startPosition, endPosition);
        when(idService.generateId()).thenReturn("1");
        when(routesService.getRoutes(any(), any())).thenReturn(null);
        when(photoService.getAllPhotosByRouteId(any())).thenReturn(null);
        when(routeRepository.save(any())).thenReturn(
                Route.builder()
                        .id("1")
                        .routeName(routeDTO.getRouteName())
                        .hashtags(routeDTO.getHashtags())
                        .imageThumbnail(routeDTO.getImageThumbnail())
                        .startPosition(routeDTO.getStartPosition())
                        .endPosition(routeDTO.getEndPosition())
                        .routes(null)
                        .photos(null)
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
                .endPosition(routeDTO.getEndPosition())
                .routes(null)
                .photos(null)
                .position(new GeoJsonPoint(2.2, 1.1))
                .build();
        assertEquals(expected, actual);
    }

    @Test
    void getAllRoutes_ShouldReturn_AllRoutes(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                endPosition, null, null, new GeoJsonPoint(2.2, 1.1));

        when(routeRepository.findAll()).thenReturn(List.of(dummyRoute));

        // WHEN
        List<Route> actual = routeService.getAllRoutesInRepo();

        // THEN
        List<Route> expected = List.of(dummyRoute);
        assertEquals(expected, actual);

    }

}