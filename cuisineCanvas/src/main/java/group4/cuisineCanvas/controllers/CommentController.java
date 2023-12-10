package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.services.CommentService;
import group4.cuisineCanvas.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ReactionService reactionService;

    // To comment on a recipe, user from AuthenticationPrincipal provides UserObject for comment author.
    @PostMapping("/{recipeId}/comment/add")
    public ResponseEntity<Comment> createComment(@AuthenticationPrincipal User user, @RequestBody String comment, @PathVariable String recipeId) {
        var result = commentService.addComment(user, comment, UUID.fromString(recipeId));
        System.out.println(result);
        return ResponseEntity.ok(result);

    }

    // To like or dislike a comment
    @PostMapping("/{commentId}/reaction")
    public ResponseEntity<String> reactToComment(
            @AuthenticationPrincipal User user,
            @PathVariable UUID commentId,
            @RequestParam ReactionType reactionType) {


        if (commentId == null || reactionType == null) {
            return ResponseEntity.badRequest().body("CommentId, and ReactionType cannot be null");
        }

        reactionService.reactToComment(user, commentId, reactionType);

        return ResponseEntity.ok("Reaction processed successfully");
    }

    // To delete a comment by its Id, user is used to check if the creator is deleting the comment
    @DeleteMapping("/{recipeId}/{commentId}/delete")
    public ResponseEntity<String> deleteARecipe(
            @AuthenticationPrincipal User user,@PathVariable UUID recipeId, @PathVariable UUID commentId) throws AccessDeniedException {

        if (commentId == null || recipeId == null) {
            return ResponseEntity.badRequest().body("CommentId and RecipeId cannot be null");
        }

        commentService.deleteAComment(user, recipeId, commentId);
        return ResponseEntity.ok("Comment deleted");
    }

    // To update a comment only by the author of the comment.
    @PutMapping("/{recipeId}/{commentId}/update")
    public ResponseEntity<String> commentRecipe(
            @RequestBody String editedComment,
            @PathVariable UUID commentId,
            @AuthenticationPrincipal User user) throws AccessDeniedException {

        if (commentId == null) {
            return ResponseEntity.badRequest().body("CommentID cannot be null");
        }

        commentService.updateComment(editedComment, commentId, user);

        return ResponseEntity.ok("Comment updated");

    }


}
