package group4.cuisineCanvas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotNull(message = "Name can not be null! ")
    @Size(min = 2, max = 8, message = "Invalid size! ")
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

}
