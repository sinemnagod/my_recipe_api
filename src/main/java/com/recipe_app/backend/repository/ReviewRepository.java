package com.recipe_app.backend.repository;

import com.recipe_app.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRecipe_Id(Long recipeId);
    List<Review> findByUser_Id(Long userId);
}
