package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.CommentaryRepository;
import de.neuefische.backend.repository.FoundRouteRepository;
import de.neuefische.backend.repository.PhotoRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
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

    private final RoutesService routesService = mock(RoutesService.class);

    private final FoundRouteRepository foundRouteRepository = mock(FoundRouteRepository.class);

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);

    private final CommentaryRepository commentaryRepository = mock(CommentaryRepository.class);

    private final RouteService routeService = new RouteService(routeRepository,
            idService, locationService, routesService, foundRouteRepository, photoRepository, commentaryRepository);

    @Test
    void addNewRoute_ShouldReturn_AddedRoute(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);
        RouteDTO routeDTO = new RouteDTO("routeName", hashtags, "imageThumbnail", startPosition, new ArrayList<>(), endPosition, "user1");
        when(idService.generateId()).thenReturn("1");
        when(routesService.getRoutes(any(),any(), any())).thenReturn(null);
        when(routeRepository.save(any())).thenReturn(
                Route.builder()
                        .id("1")
                        .routeName(routeDTO.getRouteName())
                        .hashtags(routeDTO.getHashtags())
                        .imageThumbnail(routeDTO.getImageThumbnail())
                        .startPosition(routeDTO.getStartPosition())
                        .betweenPositions(routeDTO.getBetweenPositions())
                        .endPosition(routeDTO.getEndPosition())
                        .routes(null)
                        .photos(new ArrayList<>())
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .createdBy(routeDTO.getCreatedBy())
                        .commentaries(new ArrayList<>())
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
                .betweenPositions(routeDTO.getBetweenPositions())
                .endPosition(routeDTO.getEndPosition())
                .routes(null)
                .photos(new ArrayList<>())
                .position(new GeoJsonPoint(2.2, 1.1))
                .createdBy(routeDTO.getCreatedBy())
                .commentaries(new ArrayList<>())
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
                new ArrayList<>(),endPosition, null, null, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findAll()).thenReturn(List.of(dummyRoute));

        // WHEN
        List<Route> actual = routeService.getAllRoutesInRepo();

        // THEN
        List<Route> expected = List.of(dummyRoute);
        assertEquals(expected, actual);

    }

}