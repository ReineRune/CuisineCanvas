package group4.cuisineCanvas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private UUID userId;

    private UUID recipeId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StarRating starRating;


}

