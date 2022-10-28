package de.neuefische.backend.service;

import de.neuefische.backend.model.FoundRoutes;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.model.StartPosition;
import de.neuefische.backend.repository.FoundRouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
class FoundRouteServiceTest {

    private final FoundRouteRepository foundRouteRepository = mock(FoundRouteRepository.class);
    private final RouteService routeService = mock(RouteService.class);
    private final IdService idService = mock(IdService.class);

    private final FoundRouteService foundRouteService = new FoundRouteService(foundRouteRepository,
                                                                        routeService, idService);

    @Test
    void getAllFoundRoutes_ShouldReturn_AllObjectsInRepo(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        List<Route> dummyRoutes = List.of(
                Route.builder()
                        .id("1")
                        .routeName("test")
                        .hashtags(hashtags)
                        .imageThumbnail("imageThumbnail")
                        .startPosition(startPosition)
                        .position(new GeoJsonPoint(2.2, 1.1))
                        .build()
        );
        FoundRoutes foundRoutes = FoundRoutes.builder()
                .id("1")
                .foundRoutes(dummyRoutes)
                .build();
        when(foundRouteRepository.findAll()).thenReturn(List.of(foundRoutes));

        // WHEN
        List<FoundRoutes> actual = foundRouteService.getAllFoundRoutes();

        // THEN
        List<FoundRoutes> expected = List.of(foundRoutes);

        assertEquals(expected, actual);
    }

}