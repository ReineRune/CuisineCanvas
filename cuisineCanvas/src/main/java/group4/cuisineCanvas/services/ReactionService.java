package group4.cuisineCanvas.services;

import static group4.cuisineCanvas.utilities.Utility.getUserId;

import group4.cuisineCanvas.entities.Reaction;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.repositories.ReactionRepository;
import group4.cuisineCanvas.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReactionService {

  private final ReactionRepository reactionRepository;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  public void reactToRecipe(String token, UUID recipeId, ReactionType reactionType) {

    UUID userid = getUserId(token, jwtService, userRepository);

    validateInput(recipeId, reactionType);

    try {

      Optional<Reaction> existingReaction =
          reactionRepository.findByUserIdAndRecipeId(userid, recipeId);

      if (existingReaction.isEmpty()) {

        saveNewReaction(userid, recipeId, "recipe", reactionType);

      } else {

        handleExistingReaction(existingReaction.get(), reactionType);
      }
    } catch (ResponseStatusException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something went Wrong!");
    }
  }

  private void validateInput(UUID entityId, ReactionType reactionType) {
    if (entityId == null || reactionType == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "EntityID, and ReactionType cannot be null");
    }
  }

  private void saveNewReaction(
      UUID userId, UUID entityId, String entityType, ReactionType reactionType) {
    Reaction reaction = new Reaction();
    reaction.setReactionType(reactionType);
    reaction.setUserId(userId);

    if (entityType.equalsIgnoreCase("recipe")) {
      reaction.setRecipeId(entityId);
    } else {
      reaction.setCommentId(entityId);
    }

    reactionRepository.save(reaction);
  }

  private void handleExistingReaction(Reaction existingReaction, ReactionType newReactionType) {
    ReactionType existingReactionType = existingReaction.getReactionType();

    if (existingReactionType == newReactionType) {
      reactionRepository.delete(existingReaction);
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "It's not allowed to switch between reactions");
    }
  }

  public void reactToComment(String token, UUID commentId, ReactionType reactionType) {

    UUID userid = getUserId(token, jwtService, userRepository);

    validateInput(commentId, reactionType);

    try {

      Optional<Reaction> existingReaction =
          reactionRepository.findByUserIdAndCommentId(userid, commentId);

      if (existingReaction.isEmpty()) {

        saveNewReaction(userid, commentId, "comment", reactionType);

      } else {

        handleExistingReaction(existingReaction.get(), reactionType);
      }
    } catch (ResponseStatusException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Oops! Something went Wrong!");
    }
  }
}
