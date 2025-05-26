package com.recipe_app.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;

    @NotBlank(message = "Recipe name is required")
    private String name;

    @NotEmpty(message = "At least one category is required")
    private List<Long> categoryIds;

    @NotBlank(message = "Ingredients are required")
    private String ingredients;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @NotNull(message = "Person number is required")
    @Min(value = 1, message = "Must serve at least 1 person")
    private Integer personNumber;

    @NotNull(message = "Cooking time is required")
    @Min(value = 1, message = "Cooking time must be at least 1 minute")
    private Long cookingTime;

    private List<Long> reviewIds;

    @NotBlank(message = "Recipe description is required")
    private String description;

    @NotNull(message = "User ID is required")
    private Long userId;

    private List<Long> likedByUsers;

    private Double score;

    private String imageUrl;

}
