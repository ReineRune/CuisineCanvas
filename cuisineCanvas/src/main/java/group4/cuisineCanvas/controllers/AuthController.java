package group4.cuisineCanvas.controllers;

import group4.cuisineCanvas.dto.CreateUserDto;
import group4.cuisineCanvas.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    //An Endpoint to register a new User
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {

        boolean result = userService.createUser(createUserDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.ordinal()).body("Something went wrong! ");
        }
    }

}
