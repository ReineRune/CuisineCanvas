package group4.cuisineCanvas.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PostARecipeDto {

    private String title;
    private String description;
    private Map<String, Double> ingredients;

}
