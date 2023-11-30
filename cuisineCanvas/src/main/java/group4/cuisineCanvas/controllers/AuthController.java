package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.CreateUserDto;
import group4.cuisineCanvas.dto.SignInDto;
import group4.cuisineCanvas.services.AuthenticationService;
import group4.cuisineCanvas.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    //An Endpoint to register a new User
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {

        boolean result = authenticationService.createUser(createUserDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body("Something went wrong! ");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignInDto signInDto) {

        String signIn = authenticationService.signIn(signInDto);
        return ResponseEntity.ok(signIn);

    }

}
