package com.recipe_app.backend.repository;

import com.recipe_app.backend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    Optional<Ingredient> findByName(String name);
}
