package com.recipe_app.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Score is required")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    private Double score;

    private Date commentDate;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;

    private String username;
}
