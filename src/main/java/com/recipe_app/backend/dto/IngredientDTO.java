package com.recipe_app.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id;

    @NotBlank(message = "Ingredient name is required")
    private String name;

    @NotBlank(message = "Measurement is required")
    private String measurement;

    private String description;
}
