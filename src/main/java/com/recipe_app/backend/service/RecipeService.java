package com.recipe_app.backend.service;

import com.recipe_app.backend.dto.RecipeDTO;
import com.recipe_app.backend.entity.*;
import com.recipe_app.backend.mapper.RecipeMapper;
import com.recipe_app.backend.repository.CategoryRepository;
import com.recipe_app.backend.repository.RecipeRepository;
import com.recipe_app.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final CategoryRepository categoryRepository;

    public RecipeDTO saveRecipe(Recipe recipe) {
        Recipe recipeToSave = recipeRepository.save(recipe);
        return recipeMapper.recipeEntityToRecipeDTO(recipeToSave);
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.recipeEntityListToRecipeDTOList(recipeRepository.findAll());
    }

    public List<RecipeDTO> getRecipesByCategory(Long categoryId) {
        return recipeMapper.recipeEntityListToRecipeDTOList(recipeRepository.findByCategories_Id(categoryId));
    }

    public RecipeDTO getRecipesByName(String name) {
        return recipeMapper.recipeEntityToRecipeDTO(recipeRepository.findByName(name));
    }

    public RecipeDTO addCategoryToRecipe(Long recipeId, Long categoryId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        recipe.getCategories().add(category);
        return recipeMapper.recipeEntityToRecipeDTO(recipeRepository.save(recipe));
    }

    public List<RecipeDTO> getRecipesByUserId(Long userId) {
        return recipeMapper.recipeEntityListToRecipeDTOList(recipeRepository.findByUser_Id(userId));
    }

    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + id));
        return recipeMapper.recipeEntityToRecipeDTO(recipe);
    }

    public RecipeDTO removeCategoryFromRecipe(Long recipeId, Long categoryId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        recipe.getCategories().remove(category);
        return RecipeMapper.recipeEntityToRecipeDTO(recipeRepository.save(recipe));
    }
}
