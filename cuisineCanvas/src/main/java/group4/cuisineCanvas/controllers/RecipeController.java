package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/recipe/add")
    public ResponseEntity<String> addARecipe(@RequestHeader("Authorization") String token, @RequestBody PostARecipeDto postARecipeDto){

        boolean result = recipeService.addANewRecipe(token, postARecipeDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Recipe successfully created!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body("Something went wrong! ");
        }
    }

    @GetMapping("/all-recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        List<Recipe> result = recipeService.getAllRecipes();
        return ResponseEntity.ok(result);
    }

}
