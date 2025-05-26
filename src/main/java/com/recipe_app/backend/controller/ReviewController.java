package com.recipe_app.backend.controller;

import com.recipe_app.backend.dto.ReviewDTO;
import com.recipe_app.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3001", allowCredentials = "true")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO dto,
            @CookieValue("userId") Long userId) {
        dto.setUserId(userId);
        return ResponseEntity.ok(reviewService.saveReview(dto));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByRecipe(@PathVariable Long recipeId) {
        return ResponseEntity.ok(reviewService.getReviewsByRecipe(recipeId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }
}
