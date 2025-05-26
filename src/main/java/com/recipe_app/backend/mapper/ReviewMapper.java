package com.recipe_app.backend.mapper;

import com.recipe_app.backend.dto.ReviewDTO;
import com.recipe_app.backend.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public static ReviewDTO toDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getDescription(),
                review.getScore(),
                review.getCommentDate(),
                review.getUser().getId(),
                review.getRecipe().getId(),
                review.getUser().getUsername()
        );
    }

    public static Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setDescription(dto.getDescription());
        review.setScore(dto.getScore());
        review.setCommentDate(dto.getCommentDate()); // null olabilir, @PrePersist zaten set eder
        return review;
    }
}

