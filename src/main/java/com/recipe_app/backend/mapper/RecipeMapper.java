package com.recipe_app.backend.mapper;

import com.recipe_app.backend.dto.CategoryDTO;
import com.recipe_app.backend.dto.RecipeDTO;
import com.recipe_app.backend.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

        public static RecipeDTO recipeEntityToRecipeDTO(Recipe recipe) {
                RecipeDTO recipeDTO = new RecipeDTO();

                recipeDTO.setId(recipe.getId());
                recipeDTO.setDescription(recipe.getDescription());
                recipeDTO.setPersonNumber(recipe.getPersonNumber());
                recipeDTO.setCookingTime(recipe.getCookingTime());
                recipeDTO.setUserId(recipe.getUser().getId());
                recipeDTO.setName(recipe.getName());
                recipeDTO.setScore(recipe.getScore() == null ? null : Math.round(recipe.getScore() * 10.0) / 10.0);
                recipeDTO.setDescription(recipe.getDescription());

                recipeDTO.setImageUrl(recipe.getImageUrl());
                recipeDTO.setIngredients(recipe.getIngredients());
                List<Long> categoryIds = recipe.getCategories()
                                .stream()
                                .map(Category::getId)
                                .collect(Collectors.toList());
                recipeDTO.setCategoryIds(categoryIds);

                List<Long> reviewIds = recipe.getReviews()
                                .stream()
                                .map(Review::getId)
                                .collect(Collectors.toList());
                recipeDTO.setReviewIds(reviewIds);

                List<Long> likedByUsers = recipe.getLikedByUsers()
                                .stream()
                                .map(User::getId)
                                .collect(Collectors.toList());
                recipeDTO.setLikedByUsers(likedByUsers);

                return recipeDTO;
        }

        public List<RecipeDTO> recipeEntityListToRecipeDTOList(List<Recipe> recipes) {
                List<RecipeDTO> recipeDTOList = new ArrayList<>();
                for (Recipe recipe : recipes) {
                        recipeDTOList.add(recipeEntityToRecipeDTO(recipe));
                }
                return recipeDTOList;
        }
}
