package group4.cuisineCanvas.utilities;

import group4.cuisineCanvas.repositories.UserRepository;
import group4.cuisineCanvas.services.JwtService;
import java.util.NoSuchElementException;
import java.util.UUID;

public final class Utility {

  public static UUID getUserId(String token, JwtService jwtService, UserRepository userRepository) {

    var userEmail = jwtService.extractUserNameFromToken(token);

    return userRepository
        .findByEmail(userEmail)
        .orElseThrow(() -> new NoSuchElementException("Oops! User not Found!"))
        .getId();
  }
}
