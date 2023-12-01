package group4.cuisineCanvas.services;

import static group4.cuisineCanvas.utilities.Utility.getUserId;

import group4.cuisineCanvas.entities.Rating;
import group4.cuisineCanvas.entities.Reaction;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.entities.StarRating;
import group4.cuisineCanvas.exceptionsHandler.ValueAlreadyExistsException;
import group4.cuisineCanvas.repositories.RatingRepository;
import group4.cuisineCanvas.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  public void rateARecipe(String token, UUID recipeId, StarRating starRating)
      throws ValueAlreadyExistsException {

    UUID userId = getUserId(token, jwtService, userRepository);

    validateInput(recipeId, starRating);
    try {
        Optional<Rating> existingRating = ratingRepository.findByUserIdAndRecipeId(userId, recipeId);

        if (existingRating.isEmpty()) {

            saveNewRating(userId, recipeId, starRating);

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

  private void validateInput(UUID entityId, StarRating starRating) {
    if (entityId == null || starRating == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "EntityID, and Star Rating cannot be null");
    }
  }

    private void saveNewRating(
            UUID userId, UUID recipeId, StarRating starRating) {
        Rating rating = new Rating();
        rating.setStartRating(starRating);
        rating.setUserId(userId);
        rating.setRecipeId(recipeId);
        ratingRepository.save(rating);

    }

    private void handleExistingRating(Rating existingRating, StarRating newStarRating) {
        StarRating existingStarRating = existingRating.getStartRating();

        if (newStarRating != existingStarRating) {
            existingRating.setStartRating(newStarRating);
            ratingRepository.save(existingRating);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You have already set '" + existingRating.getStartRating() + "' rating on this recipe.");

        }
    }

}
