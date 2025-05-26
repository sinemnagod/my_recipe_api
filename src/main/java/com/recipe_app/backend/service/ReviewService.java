package com.recipe_app.backend.service;

import com.recipe_app.backend.dto.ReviewDTO;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.entity.Review;
import com.recipe_app.backend.exception.custom.ResourceNotFoundException;
import com.recipe_app.backend.mapper.ReviewMapper;
import com.recipe_app.backend.repository.RecipeRepository;
import com.recipe_app.backend.repository.ReviewRepository;
import com.recipe_app.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public ReviewDTO saveReview(ReviewDTO dto) {
        Review review = ReviewMapper.toEntity(dto);
        review.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId())));
        Recipe recipe = recipeRepository.findById(dto.getRecipeId()).orElseThrow();
        review.setRecipe(recipe);

        Review saved = reviewRepository.save(review);

        double avgScore = recipe.getReviews()
                .stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);

        recipe.setScore(avgScore);
        recipeRepository.save(recipe);

        return ReviewMapper.toDTO(saved);
    }


    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByRecipe(Long recipeId) {
        return reviewRepository.findByRecipe_Id(recipeId)
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByUser(Long userId) {
        return reviewRepository.findByUser_Id(userId)
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}
