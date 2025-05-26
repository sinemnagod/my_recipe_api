package com.recipe_app.backend.repository;

import com.recipe_app.backend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategories_Id(Long categoryId);

    Recipe findByName(String name);

    List<Recipe> findByUser_Id(Long userId);
}
