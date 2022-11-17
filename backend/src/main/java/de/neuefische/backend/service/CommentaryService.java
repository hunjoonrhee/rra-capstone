package de.neuefische.backend.service;

import de.neuefische.backend.model.Commentary;
import de.neuefische.backend.model.CommentaryDTO;
import de.neuefische.backend.model.Route;
import de.neuefische.backend.repository.AppUserRepository;
import de.neuefische.backend.repository.CommentaryRepository;
import de.neuefische.backend.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentaryService {

    private final RouteRepository routeRepository;
    private final AppUserRepository appUserRepository;
    private final IdService idService;
    private final CommentaryRepository commentaryRepository;

    public CommentaryService(RouteRepository routeRepository, AppUserRepository appUserRepository, IdService idService, CommentaryRepository commentaryRepository) {
        this.routeRepository = routeRepository;
        this.appUserRepository = appUserRepository;
        this.idService = idService;
        this.commentaryRepository = commentaryRepository;
    }

    public Commentary addANewComment(String routeId, String user, CommentaryDTO commentaryDTO) {
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(optionalRoute.isPresent()){
            Route route = optionalRoute.get();
            Commentary newCommentary = Commentary.builder()
                    .id(idService.generateId())
                    .message(commentaryDTO.getMessage())
                    .routeId(commentaryDTO.getRouteId())
                    .postedBy(appUserRepository.findById(user).orElse(null))
                    .timeStamp(localDateFormat.format(new Date()))
                    .build();
            List<Commentary> commentaryList = route.getCommentaries();
            commentaryList.add(newCommentary);
            route.setCommentaries(commentaryList);
            routeRepository.save(route);
            commentaryRepository.save(newCommentary);
            return newCommentary;
        }else{
            throw new NoSuchElementException("No route with id: " + routeId + " was found!");
        }
    }

    public List<Commentary> getAllCommentaries() {
        return commentaryRepository.findAll();
    }
}
