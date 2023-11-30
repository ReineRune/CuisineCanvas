package group4.cuisineCanvas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String content;

   @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    //@OneToMany
    //@JoinColumn(name="recipeId)
    //private Recipe recipe;

}
