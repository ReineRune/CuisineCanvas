package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.CreateCommentDto;
import group4.cuisineCanvas.entities.Comment;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.exceptionsHandler.ValueCanNotBeNullException;
import group4.cuisineCanvas.repositories.CommentRepository;
import group4.cuisineCanvas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public Comment addComment(CreateCommentDto createCommentDto, String token) throws ValueCanNotBeNullException {
        String username=jwtService.extractUserNameFromToken(token);
        System.out.println("username is "+ username);
        User user= userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("user with this email id is not available"));
        System.out.println("user is "+ user);
        UUID userId= user.getId();
        if(createCommentDto.getContent()==null || createCommentDto.getContent().isBlank() || createCommentDto.getContent().isEmpty()){
            throw new ValueCanNotBeNullException("Comment can not be null");
        }
        Comment newComment= Comment.builder().user(user).content(createCommentDto.getContent()).build();
        commentRepository.save(newComment);
        return newComment;
    }
}
