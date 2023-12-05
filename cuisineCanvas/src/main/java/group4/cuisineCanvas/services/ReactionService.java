package group4.cuisineCanvas.services;

import group4.cuisineCanvas.entities.Reaction;
import group4.cuisineCanvas.entities.ReactionType;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.repositories.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;

    // To handle the case when te entityId is a recipeID(reacting to a recipe)
    public void reactToRecipe(User user, UUID recipeId, ReactionType reactionType) {

        validateInput(recipeId, reactionType);

        try {

            Optional<Reaction> existingReaction =
                    reactionRepository.findByUserIdAndRecipeId(user.getId(), recipeId);

            if (existingReaction.isEmpty()) {

                saveNewReaction(user.getId(), recipeId, "recipe", reactionType);

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

    // To check if the recipeId or commentId and reactionType is not null.
    private void validateInput(UUID entityId, ReactionType reactionType) {
        if (entityId == null || reactionType == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "EntityID, and ReactionType cannot be null");
        }
    }

    // To add a new reaction, like or dislike on a recipe or a comment
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

    // To handle case when there is already a reaction existing and user is again adding the same reaction, it will remove the reaction.
    // If there is a reaction (LIKE) already and the user adds another reaction (DISLIKE), it throws an error.
    private void handleExistingReaction(Reaction existingReaction, ReactionType newReactionType) {
        ReactionType existingReactionType = existingReaction.getReactionType();

        if (existingReactionType == newReactionType) {
            reactionRepository.delete(existingReaction);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "It's not allowed to switch between reactions");
        }
    }

    // To handle the case when te entityId is a commentID(reacting to a comment)
    public void reactToComment(User user, UUID commentId, ReactionType reactionType) {


        validateInput(commentId, reactionType);

        try {

            Optional<Reaction> existingReaction =
                    reactionRepository.findByUserIdAndCommentId(user.getId(), commentId);

            if (existingReaction.isEmpty()) {

                saveNewReaction(user.getId(), commentId, "comment", reactionType);

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
