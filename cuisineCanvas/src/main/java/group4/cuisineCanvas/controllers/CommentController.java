package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.CreateCommentDto;
import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<Comment> createComment(@RequestHeader("Authorization") String token, @RequestBody CreateCommentDto createCommentDto){
        var result= commentService.addComment(createCommentDto,token);
        return ResponseEntity.ok(result);

    }
}
