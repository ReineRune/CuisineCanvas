package group4.cuisineCanvas.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostARecipeDto {

    private String title;
    private String description;
    private String ingredients;
    private UUID userId;

}
