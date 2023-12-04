package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.PostARecipeDto;
import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.entities.Recipe;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.exceptionsHandler.ValueCanNotBeNullException;
import group4.cuisineCanvas.repositories.CommentRepository;
import group4.cuisineCanvas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public Comment addComment(String comment, String token, UUID recipeId) throws ValueCanNotBeNullException {
        String username=jwtService.extractUserNameFromToken(token);
        User user= userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user with this email id is not available"));
        if(comment ==null ||  comment.isEmpty()){
            throw new ValueCanNotBeNullException("Comment can not be null");
        }
        Comment newComment= Comment.builder().user(user).content(comment).recipe_id(recipeId).build();
        commentRepository.save(newComment);

        return newComment;
    }


    public void deleteAComment(User user, UUID commentId) throws AccessDeniedException {

        Comment comment =
                commentRepository
                        .findById(commentId)
                        .orElseThrow(
                                () -> new IllegalArgumentException("Could not find a comment with that id"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only delete your own comments");
        }

        commentRepository.deleteById(commentId);
    }

    public void updateComment(Comment editComment, UUID commentId, User user) throws AccessDeniedException {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find a comment with that id"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Only the comment author is allowed to edit the recipe");
        }

        Optional.ofNullable(editComment.getContent()).ifPresent(comment::setContent);


        commentRepository.save(comment);
    }
}
