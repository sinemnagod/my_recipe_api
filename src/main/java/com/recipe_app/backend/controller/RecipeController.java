package com.recipe_app.backend.controller;

import com.recipe_app.backend.dto.RecipeDTO;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.repository.CategoryRepository;
import com.recipe_app.backend.repository.UserRepository;
import com.recipe_app.backend.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recipe_app.backend.entity.Category;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    /*
     * @PostMapping("/create")
     * public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO
     * dto,
     * 
     * @CookieValue("userId") Long userId) {
     * Recipe recipe = new Recipe();
     * recipe.setName(dto.getName());
     * recipe.setDescription(dto.getDescription());
     * recipe.setIngredients(dto.getIngredients());
     * recipe.setPersonNumber(dto.getPersonNumber());
     * recipe.setCookingTime(dto.getCookingTime());
     * recipe.setUser(userRepository.findById(userId).orElseThrow());
     * recipe.setImageUrl(dto.getImageUrl());
     * recipe.setScore(dto.getScore());
     * 
     * List<Category> cats = dto.getCategoryIds().stream()
     * .map(catId -> categoryRepository
     * .findById(catId)
     * .orElseThrow(() -> new RuntimeException("Category not found: " + catId)))
     * .collect(Collectors.toList());
     * recipe.setCategories(cats);
     * 
     * return ResponseEntity.ok(recipeService.saveRecipe(recipe));
     * }
     */

    @PostMapping("/create")
    public ResponseEntity<RecipeDTO> createRecipe(
            @Valid @RequestBody RecipeDTO dto,
            @CookieValue("userId") Long userId) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setIngredients(dto.getIngredients());
        recipe.setPersonNumber(dto.getPersonNumber());
        recipe.setCookingTime(dto.getCookingTime());
        recipe.setUser(userRepository.findById(userId).orElseThrow());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setScore(dto.getScore());

        
        List<Category> cats = dto.getCategoryIds().stream()
                .map(catId -> categoryRepository
                        .findById(catId)
                        .orElseThrow(() -> new RuntimeException("Category not found: " + catId)))
                .collect(Collectors.toList());
        recipe.setCategories(cats);

       
        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<RecipeDTO>> getRecipesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(recipeService.getRecipesByCategory(categoryId));
    }

    @GetMapping("/name")
    public ResponseEntity<RecipeDTO> getRecipeByName(@RequestParam String name) {
        return ResponseEntity.ok(recipeService.getRecipesByName(name));
    }

    @GetMapping("/my-recipes")
    public ResponseEntity<List<RecipeDTO>> getMyRecipes(@CookieValue("userId") Long userId) {
        List<RecipeDTO> recipes = recipeService.getRecipesByUserId(userId);
        return ResponseEntity.ok(recipes);
    }

}
