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
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private UUID userId;

    private UUID recipeId;

    private UUID commentId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

}
