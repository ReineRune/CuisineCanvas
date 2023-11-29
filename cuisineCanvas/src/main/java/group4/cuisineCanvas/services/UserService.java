package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.CreateUserDto;
import group4.cuisineCanvas.entities.Role;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Create a new User
    public boolean createUser(CreateUserDto createUserDto) {
        if (createUserDto.getEmail() == null || createUserDto.getEmail().isEmpty() || createUserDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email can not be null! ");
        }
        var existing = userRepository.findByEmail(createUserDto.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Email already exists! ");

        }
        User newUser = User.builder().email(createUserDto.getEmail()).name(createUserDto.getName()).password(passwordEncoder.encode(createUserDto.getPassword())).role(Role.ROLE_USER).build();
        userRepository.save(newUser);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
