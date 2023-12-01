package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.services.ReactionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReactionController {

  private final ReactionService reactionService;

  @PostMapping("/{recipeId}/reaction")
  public ResponseEntity<String> reactToRecipe(
      @RequestHeader("Authorization") String token,
      @PathVariable UUID recipeId,
      @RequestParam ReactionType reactionType) {

    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied!");
    }

    if (recipeId == null || reactionType == null) {
      return ResponseEntity.badRequest().body("RecipeID, and ReactionType cannot be null");
    }

    reactionService.reactToRecipe(token, recipeId, reactionType);

    return ResponseEntity.ok("Reaction processed successfully");
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
}
