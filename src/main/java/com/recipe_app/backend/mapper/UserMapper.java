package com.recipe_app.backend.mapper;

import com.recipe_app.backend.dto.UserDTO;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.entity.Review;
import com.recipe_app.backend.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getReviews()
                        .stream()
                        .map(Review::getId)
                        .collect(Collectors.toList()),
                user.getRegisterOn(),
                user.getFavoriteRecipes()
                        .stream()
                        .map(Recipe::getId)
                        .collect(Collectors.toList())
        );
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
}

