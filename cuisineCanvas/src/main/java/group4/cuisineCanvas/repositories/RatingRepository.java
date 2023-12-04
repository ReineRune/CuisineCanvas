package group4.cuisineCanvas.repositories;

import group4.cuisineCanvas.entities.Rating;
import group4.cuisineCanvas.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {

    Optional<Rating> findByUserIdAndRecipeId(UUID userId, UUID postId);

    List<Rating> findByRecipeId(UUID recipeId);
}
