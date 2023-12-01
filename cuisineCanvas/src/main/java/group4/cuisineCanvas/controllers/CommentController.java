package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{recipeId}/comment/add")
    public ResponseEntity<Comment> createComment(@RequestHeader("Authorization") String token, @RequestBody String comment, @PathVariable String recipeId ){
        var result= commentService.addComment(comment,token, UUID.fromString(recipeId));
    System.out.println(result);
        return ResponseEntity.ok(result);

    }


 //   @GetMapping("/{recipeId}/all-comments")

}
