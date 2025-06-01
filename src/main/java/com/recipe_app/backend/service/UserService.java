package com.recipe_app.backend.service;

import com.recipe_app.backend.dto.RecipeDTO;
import com.recipe_app.backend.dto.UserDTO;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.entity.User;
import com.recipe_app.backend.exception.custom.InvalidCredentialsException;
import com.recipe_app.backend.exception.custom.ResourceNotFoundException;
import com.recipe_app.backend.mapper.RecipeMapper;
import com.recipe_app.backend.mapper.UserMapper;
import com.recipe_app.backend.repository.RecipeRepository;
import com.recipe_app.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO saveUser(UserDTO dto, String rawPassword) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    
    @Transactional
    public UserDTO registerUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO addRecipeToFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        user.getFavoriteRecipes().add(recipe);
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserDTO removeRecipeFromFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        user.getFavoriteRecipes().remove(recipe);
        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public List<RecipeDTO> getFavoriteRecipes(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getFavoriteRecipes().stream()
                .map(RecipeMapper::recipeEntityToRecipeDTO)
                .collect(Collectors.toList());
    }


}

