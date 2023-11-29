package group4.cuisineCanvas.repositories;

import group4.cuisineCanvas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email);
}
