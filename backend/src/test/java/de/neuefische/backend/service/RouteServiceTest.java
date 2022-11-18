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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void deleteRouteById_ShouldDelete_RouteByGivenId(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, null, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());
        List<Route> dummyRoutes = new ArrayList<>();
        dummyRoutes.add(dummyRoute);

        FoundRoutes foundRoutes = new FoundRoutes("address", dummyRoutes);

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        when(foundRouteRepository.findById("address")).thenReturn(Optional.of(foundRoutes));
        // When
        routeService.deleteRouteById("1", "address");

        // Then
        verify(routeRepository).deleteById("1");

    }

    @Test
    void getPhotosOfRoute_ShouldReturn_AllPhotosOfRoute_ByGivenRouteId(){
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

        // WHEN
        List<Photo> actual = routeService.getPhotosOfRoute("1");

        // THEN
        assertEquals(photos, actual);
    }

    @Test
    void addANewPhotoForRoute_ShouldReturn_AddedPhoto(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        PhotoDTO photoDTO = new PhotoDTO("photo", "user1");
        List<Photo> photos = new ArrayList<>();

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        when(idService.generateId()).thenReturn("photo1");

        // WHEN
        Photo actual = routeService.addANewPhotoForRoute("1", photoDTO);

        // THEN
        Photo expected = Photo.builder()
                .id(idService.generateId())
                .name(photoDTO.getName())
                .uploadedBy(photoDTO.getUploadedBy())
                .routeId(dummyRoute.getId())
                .build();
        assertEquals(expected, actual);

    }

    @Test
    void deletePhotoOfRoute_ShouldDelete_PhotoByGivenRouteId(){
        // GIVEN
        String[] hashtags = new String[1];
        hashtags[0] = "tree";
        StartPosition startPosition = new StartPosition(2.2, 1.1);
        EndPosition endPosition = new EndPosition(2.3, 1.12);

        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo("photo1", "photo", "user1", "1"));
        photos.add(new Photo("photo2", "photo2", "user1", "1"));
        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, photos, new GeoJsonPoint(2.2, 1.1), "user1",  new ArrayList<>());


        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        when(photoRepository.findById("photo1"))
                .thenReturn(Optional.of(new Photo("photo1", "photo", "user1", "1")));
        // When
        routeService.deletePhotoOfRoute("1", "photo1");
        List<Photo> actual = dummyRoute.getPhotos();
        // Then
        List<Photo> expected = List.of(new Photo("photo2", "photo2", "user1", "1"));
        assertEquals(expected, actual);

    }
    @Test
    void getAllCommentariesOfRoute_ShouldReturn_AllCommentariesOfRoute(){
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
        commentaries.add(new Commentary("c2", "comment2", "1", user, "xx"));

        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, new ArrayList<>(), new GeoJsonPoint(2.2, 1.1), "user1", commentaries);

        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));

        // WHEN

        List<Commentary> actual = routeService.getAllCommentariesOfRoute("1");

        // THEN
        assertEquals(commentaries, actual);

    }

    @Test
    void deleteCommentaryOfRoute_ShouldDelete_CommentaryByGivenRouteId(){
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
        commentaries.add(new Commentary("c2", "comment2", "1", user, "xx"));
        Route dummyRoute = new Route("1", "routeName", hashtags, "imageThumbnail", startPosition,
                new ArrayList<>(),endPosition, null, new ArrayList<>(), new GeoJsonPoint(2.2, 1.1),
                "user1",  commentaries);


        when(routeRepository.findById("1")).thenReturn(Optional.of(dummyRoute));
        when(commentaryRepository.findById("c"))
                .thenReturn(Optional.of(new Commentary("c1", "comment1", "1", user, "xx")));
        // When
        routeService.deleteCommentaryOfRoute("1", "c1");
        List<Commentary> actual = dummyRoute.getCommentaries();
        // Then
        List<Commentary> expected = List.of(new Commentary("c2", "comment2", "1", user, "xx"));
        assertEquals(expected, actual);

    }
    @Test
    void deleteCommentaryById_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.deleteCommentaryOfRoute("1", "c1"));
        verify(routeRepository).findById("1");

    }

    @Test
    void deletePhotoById_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.deletePhotoOfRoute("1", "p1"));
        verify(routeRepository).findById("1");

    }
    @Test
    void deleteRouteById_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.deleteRouteById("1", "address"));
        verify(routeRepository).findById("1");

    }
    @Test
    void deleteFoundRouteById_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(foundRouteRepository.findById("address")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.deleteRouteById("1", "address"));

    }
    @Test
    void getRouteById_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.getAllCommentariesOfRoute("1"));
        verify(routeRepository).findById("1");

    }
    @Test
    void getPhotoByRouteId_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.getPhotosOfRoute("1"));
        verify(routeRepository).findById("1");

    }
    @Test
    void addANewPhotoForRoute_whenRouteDoesNotExists_throwException(){
        // GIVEN
        PhotoDTO photoDTO = new PhotoDTO("photo", "user1");
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.addANewPhotoForRoute("1",photoDTO));
        verify(routeRepository).findById("1");

    }
    @Test
    void getPhotosOfRoute_whenRouteDoesNotExists_throwException(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> routeService.getPhotosOfRoute("1"));
        verify(routeRepository).findById("1");

    }

}