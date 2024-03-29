package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    // To add a new recipe by a logged-in User.
    public boolean addANewRecipe(User user, PostARecipeDto postARecipeDto) {

        try {

            if (postARecipeDto.getTitle().isBlank()
                    || postARecipeDto.getTitle().isEmpty()
                    || postARecipeDto.getTitle() == null) {
                throw new IllegalArgumentException("Title cannot be blank");
            }
            if (postARecipeDto.getDescription().isBlank()
                    || postARecipeDto.getDescription().isEmpty()
                    || postARecipeDto.getDescription() == null) {
                throw new IllegalArgumentException("Description cannot be blank");
            }
            if (postARecipeDto.getIngredients().isEmpty() || postARecipeDto.getIngredients() == null) {
                throw new IllegalArgumentException("Ingredients cannot be blank");
            }

            Recipe newRecipe =
                    Recipe.builder()
                            .title(postARecipeDto.getTitle())
                            .description(postARecipeDto.getDescription())
                            .ingredients(postARecipeDto.getIngredients())
                            .user(user)
                            .build();
            recipeRepository.save(newRecipe);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // To get all recipes without requiring a login
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // To delete a recipe only by its author.
    public void deleteARecipe(User user, UUID recipeId) throws AccessDeniedException {

        Recipe recipe =
                recipeRepository
                        .findById(recipeId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Could not find a recipe with that id"));

        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only delete your own recipes");
        }

        recipeRepository.deleteById(recipeId);
    }

    // To update a recipe only by its author.
    public void updateRecipe(PostARecipeDto editRecipe, UUID recipeId, User user) throws AccessDeniedException {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find a recipe with that id"));

        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the recipe author is allowed to edit the recipe");
        }

        Optional.ofNullable(editRecipe.getTitle()).ifPresent(recipe::setTitle);
        Optional.ofNullable(editRecipe.getDescription()).ifPresent(recipe::setDescription);
        Optional.ofNullable(editRecipe.getIngredients()).ifPresent(recipe::setIngredients);

        recipeRepository.save(recipe);
    }

    // To search a recipe containing search words in the title.
    public List<Recipe> searchRecipeByTitle(String title) {
        List<Recipe> recipeList = recipeRepository.findRecipeByTitleContaining(title);

        return recipeList;
    }
}






