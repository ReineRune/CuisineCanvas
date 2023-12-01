package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.services.CommentService;
import group4.cuisineCanvas.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ReactionService reactionService;

    @PostMapping("/{recipeId}/comment/add")
    public ResponseEntity<Comment> createComment(@RequestHeader("Authorization") String token, @RequestBody String comment, @PathVariable String recipeId ){
        var result= commentService.addComment(comment,token, UUID.fromString(recipeId));
    System.out.println(result);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/{commentId}/reaction")
    public ResponseEntity<String> reactToComment(
            @RequestHeader("Authorization") String token,
            @PathVariable UUID commentId,
            @RequestParam ReactionType reactionType) {

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied!");
        }

        if (commentId == null || reactionType == null) {
            return ResponseEntity.badRequest().body("CommentId, and ReactionType cannot be null");
        }

        reactionService.reactToComment(token, commentId, reactionType);

        return ResponseEntity.ok("Reaction processed successfully");
    }

 //   @GetMapping("/{recipeId}/all-comments")

}
