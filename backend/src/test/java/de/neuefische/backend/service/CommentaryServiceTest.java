package de.neuefische.backend.service;

import de.neuefische.backend.model.*;
import de.neuefische.backend.repository.AppUserRepository;
import de.neuefische.backend.repository.CommentaryRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class CommentaryServiceTest {

    private final RouteRepository routeRepository = mock(RouteRepository.class);
    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final IdService idService = mock(IdService.class);
    private final CommentaryRepository commentaryRepository = mock(CommentaryRepository.class);
    private final TimeStampService timeStampService = mock(TimeStampService.class);

    private final CommentaryService commentaryService = new CommentaryService(
            routeRepository, appUserRepository, idService, commentaryRepository, timeStampService
    );

    @Test
    void addANewComment_ShouldReturn_Commentary_ByGivenUserAndRouteId(){
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

        when(commentaryRepository.save(any())).thenReturn(commentary);

        // WHEN
        Commentary actual = commentaryService.addANewComment("1", "user1", commentaryDTO);

        // THEN
        assertEquals(commentary, actual);
    }
    @Test
    void addANewComment_ShouldThrowException_WhenRouteIdDoesntExist(){
        // GIVEN
        when(routeRepository.findById("1")).thenReturn(Optional.empty());

        CommentaryDTO commentaryDTO = new CommentaryDTO("Test comment", "1");

        // When / THEN
        assertThrows(NoSuchElementException.class, ()-> commentaryService.addANewComment("1", "user1", commentaryDTO));
        verify(routeRepository).findById("1");
    }

    @Test
    void getAllCommentaries_ShouldReturn_AllCommentariesInRepo(){
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
        when(commentaryRepository.findAll()).thenReturn(List.of(commentary));

        // WHEN
        List<Commentary> actual = commentaryService.getAllCommentaries();

        // THEN
        assertEquals(List.of(commentary), actual);
    }


}