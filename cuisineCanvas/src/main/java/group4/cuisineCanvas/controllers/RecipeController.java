package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.entities.StarRating;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.services.RatingService;
import group4.cuisineCanvas.services.ReactionService;
import group4.cuisineCanvas.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final ReactionService reactionService;
    private final RatingService ratingService;

    // To add a recipe only when the User is logged in.
    @PostMapping("/add")
    public ResponseEntity<String> addARecipe(
            @AuthenticationPrincipal User user,
            @RequestBody PostARecipeDto postARecipeDto) {

        boolean result = recipeService.addANewRecipe(user, postARecipeDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Recipe successfully created!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal())
                    .body("Something went wrong! ");
        }
    }

    // To get all the recipes and no logged in User is required.
    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> result = recipeService.getAllRecipes();
        return ResponseEntity.ok(result);
    }

    // To like or dislike a recipe when a user is logged in.
    @PostMapping("/{recipeId}/reaction")
    public ResponseEntity<String> reactToRecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID recipeId,
            @RequestParam ReactionType reactionType) {

        if (recipeId == null || reactionType == null) {
            return ResponseEntity.badRequest().body("RecipeID, and ReactionType cannot be null");
        }

        reactionService.reactToRecipe(user, recipeId, reactionType);

        return ResponseEntity.ok("Reaction processed successfully");
    }

    // To delete a recipe only by its author.
    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<String> deleteARecipe(
            @AuthenticationPrincipal User user, @PathVariable UUID recipeId) throws AccessDeniedException {

        if (recipeId == null) {
            return ResponseEntity.badRequest().body("RecipeID cannot be null");
        }

        recipeService.deleteARecipe(user, recipeId);
        return ResponseEntity.ok("Recipe deleted");
    }

    // To update a recipe only by its author.
    @PutMapping("/{recipeId}/update")
    public ResponseEntity<String> updateRecipe(
            @RequestBody PostARecipeDto editRecipe,
            @PathVariable UUID recipeId,
            @AuthenticationPrincipal User user) throws AccessDeniedException {

        if (recipeId == null) {
            return ResponseEntity.badRequest().body("RecipeID cannot be null");
        }

        recipeService.updateRecipe(editRecipe, recipeId, user);

        return ResponseEntity.ok("Recipe updated");

    }

    // To give star rating to a recipe by a logged in User only.
    @PostMapping("/{recipeId}/rating")
    public ResponseEntity<String> rateARecipe(
            @AuthenticationPrincipal User user,
            @PathVariable UUID recipeId,
            @RequestParam StarRating starRating) {

        if (recipeId == null || starRating == null) {
            return ResponseEntity.badRequest().body("RecipeID, and Star rating cannot be null");
        }

        ratingService.rateARecipe(user, recipeId, starRating);
        return ResponseEntity.ok("Recipe rated successfully! ");

    }

    // To get an average rating on a particular recipe by its id.
    @GetMapping("/{recipeId}/get-avg-rating")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable UUID recipeId
    ) {
        double result = ratingService.getAverageRating(recipeId);
        return ResponseEntity.ok(result);
    }

    // To search recipes by its title(title containing search words)
    @GetMapping("/searchARecipe/{title}")
    public ResponseEntity<List<Recipe>> searchARecipe(
            @PathVariable String title) {
        return ResponseEntity.ok(this.recipeService.searchRecipeByTitle(title));
    }

}















