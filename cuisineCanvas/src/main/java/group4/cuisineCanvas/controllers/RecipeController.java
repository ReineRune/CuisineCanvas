package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.services.ReactionService;
import group4.cuisineCanvas.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final ReactionService reactionService;

    @PostMapping("/add")
    public ResponseEntity<String> addARecipe(@RequestHeader("Authorization") String token, @RequestBody PostARecipeDto postARecipeDto){

        boolean result = recipeService.addANewRecipe(token, postARecipeDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Recipe successfully created!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body("Something went wrong! ");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        List<Recipe> result = recipeService.getAllRecipes();
        return ResponseEntity.ok(result);
    }

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

}
