package de.neuefische.backend.controller;

import de.neuefische.backend.model.Commentary;
import de.neuefische.backend.model.CommentaryDTO;
import de.neuefische.backend.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentaryController {

    private final CommentaryService commentaryService;

    @Autowired
    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @PostMapping("/{routeId}")
    public Commentary addANewComment(@PathVariable String routeId, @RequestParam String user, @RequestBody CommentaryDTO commentaryDTO){
        return commentaryService.addANewComment(routeId, user, commentaryDTO);

    }

    @GetMapping
    public List<Commentary> getAllCommentaries(){
        return commentaryService.getAllCommentaries();
    }

}
