package group4.cuisineCanvas.repositories;

import group4.cuisineCanvas.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {

    Optional<Reaction> findByUserIdAndRecipeId(UUID userId, UUID postId);

    Optional<Reaction> findByUserIdAndCommentId(UUID userId, UUID commentId);
}
