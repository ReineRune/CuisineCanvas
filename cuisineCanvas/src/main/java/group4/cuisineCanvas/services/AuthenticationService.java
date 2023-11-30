package group4.cuisineCanvas.services;

import group4.cuisineCanvas.dto.CreateUserDto;
import group4.cuisineCanvas.dto.SignInDto;
import group4.cuisineCanvas.entities.Role;
import group4.cuisineCanvas.entities.User;
import group4.cuisineCanvas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public String signIn(SignInDto signInDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword()));
        User user = userRepository.findByEmail(signInDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password! "));

        return jwtService.generateToken(user);
    }

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


}
