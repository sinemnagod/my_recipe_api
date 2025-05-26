package com.recipe_app.backend.mapper;

import com.recipe_app.backend.dto.IngredientDTO;
import com.recipe_app.backend.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public static IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getMeasurement(),
                ingredient.getDescription()
        );
    }

    public static Ingredient toEntity(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.getId());
        ingredient.setName(dto.getName());
        ingredient.setMeasurement(dto.getMeasurement());
        ingredient.setDescription(dto.getDescription());
        return ingredient;
    }
}
