package group4.cuisineCanvas.services;

import group4.cuisineCanvas.entities.Rating;
import group4.cuisineCanvas.entities.StarRating;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.exceptionsHandler.ValueAlreadyExistsException;
import group4.cuisineCanvas.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    // A logged in User can only give one rating to a particular recipe.
    public void rateARecipe(User user, UUID recipeId, StarRating starRating)
            throws ValueAlreadyExistsException {

        validateInput(recipeId, starRating);
        try {
            Optional<Rating> existingRating = ratingRepository.findByUserIdAndRecipeId(user.getId(), recipeId);

            if (existingRating.isEmpty()) {

                saveNewRating(user.getId(), recipeId, starRating);

            } else {
                handleExistingRating(existingRating.get(), starRating);
            }
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something went Wrong!");
        }

    }

    //  To check if the rating or the recipeId is not null.
    private void validateInput(UUID entityId, StarRating starRating) {
        if (entityId == null || starRating == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "EntityID, and Star Rating cannot be null");
        }
    }

    // Handles case when no previous rating exists on a recipe.
    private void saveNewRating(
            UUID userId, UUID recipeId, StarRating starRating) {
        Rating rating = new Rating();
        rating.setStarRating(starRating);
        rating.setUserId(userId);
        rating.setRecipeId(recipeId);
        ratingRepository.save(rating);

    }

    // Changes an existing rating to a new rating and throws error if the new rating is the same as the existing rating.
    private void handleExistingRating(Rating existingRating, StarRating newStarRating) {
        StarRating existingStarRating = existingRating.getStarRating();

        if (newStarRating != existingStarRating) {
            existingRating.setStarRating(newStarRating);
            ratingRepository.save(existingRating);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have already set '" + existingRating.getStarRating() + "' rating on this recipe.");

        }
    }

    // To get an average rating of a particular recipe
    public double getAverageRating(UUID recipeId) {
        List<Rating> ratingList = ratingRepository.findByRecipeId(recipeId);

        if (ratingList.isEmpty()) {
            return 0.0;
        }

        double averageRating = ratingList.stream()
                .mapToDouble(rating -> rating.getStarRating().ordinal() + 1)
                .average()
                .orElse(0.0); // Handle the case where the average cannot be calculated (return a default value)

        return averageRating;
    }
}
