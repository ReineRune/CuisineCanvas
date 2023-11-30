package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

}
