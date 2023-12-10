package group4.cuisineCanvas.services;

import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.exceptionsHandler.NoAccessToThisFeatureException;
import group4.cuisineCanvas.exceptionsHandler.ValueCanNotBeNullException;
import group4.cuisineCanvas.repositories.CommentRepository;
import group4.cuisineCanvas.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;

    // To add a new comment on a recipe by a logged-in User.
    public Comment addComment(User user, String comment, UUID recipeId) throws ValueCanNotBeNullException {

        Comment newComment = Comment.builder().user(user).content(comment).recipe_id(recipeId).build();
        commentRepository.save(newComment);

        return newComment;
    }

    // Only the author of the comment can delete a comment
    public void deleteAComment(User user, UUID recipeId, UUID commentId) throws NoAccessToThisFeatureException {
        Recipe recipe =
                recipeRepository
                        .findById(recipeId)
                        .orElseThrow(() -> new IllegalArgumentException("Could not find a recipe with that id"));
        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Could not find a comment with that id"));

        if (!comment.getUser().getId().equals(user.getId()) && !recipe.getUser().getId().equals(user.getId())) {
      throw new NoAccessToThisFeatureException("You can only delete your own comments");
        }

        commentRepository.deleteById(commentId);
    }

    // Only the author of the comment can update the comment.
    public void updateComment(String editComment, UUID commentId, User user) throws AccessDeniedException {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find a comment with that id"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the comment author is allowed to edit the recipe");
        }
        if (editComment == null){
            throw new ValueCanNotBeNullException("Comment can't be null. You can rather delete the comment.");
        }
        comment.setContent(editComment);

        commentRepository.save(comment);
    }
}
