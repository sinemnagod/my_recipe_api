package com.recipe_app.backend.mapper;

import com.recipe_app.backend.dto.CategoryDTO;
import com.recipe_app.backend.entity.Category;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final RecipeRepository recipeRepository;

    public static CategoryDTO categoryEntityToCategoryDTO(Category category) {

        List<Long> recipeIds = category.getRecipes().stream()
                .map(Recipe::getId)
                .toList();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setRecipeId(recipeIds);

        return categoryDTO;
    }

    public static Category categoryDTOToCategory(CategoryDTO dto, RecipeRepository recipeRepository) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        if (dto.getRecipeId() != null && !dto.getRecipeId().isEmpty()) {
            List<Recipe> recipes = recipeRepository.findAllById(dto.getRecipeId());
            category.setRecipes(recipes);
        } else {
            category.setRecipes(new ArrayList<>());
        }

        return category;
    }
}
