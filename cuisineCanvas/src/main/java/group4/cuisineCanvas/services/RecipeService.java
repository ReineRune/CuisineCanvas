package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.repositories.RecipeRepository;
import group4.cuisineCanvas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public boolean addANewRecipe(String token, PostARecipeDto postARecipeDto) {

        try{
        String username = jwtService.extractUserNameFromToken(token);
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No user found with this email"));

        if(postARecipeDto.getTitle().isBlank() || postARecipeDto.getTitle().isEmpty() || postARecipeDto.getTitle() == null){
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if(postARecipeDto.getDescription().isBlank() || postARecipeDto.getDescription().isEmpty() || postARecipeDto.getDescription() == null){
            throw new IllegalArgumentException("Description cannot be blank");
        }
        if(postARecipeDto.getIngredients().isEmpty() || postARecipeDto.getIngredients() == null){
            throw new IllegalArgumentException("Ingredients cannot be blank");
        }

        Recipe newRecipe = Recipe.builder().title(postARecipeDto.getTitle()).description(postARecipeDto.getDescription()).ingredients(postARecipeDto.getIngredients()).user(user).build();
        recipeRepository.save(newRecipe);
        return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
