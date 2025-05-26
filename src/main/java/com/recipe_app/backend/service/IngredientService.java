package com.recipe_app.backend.service;

import com.recipe_app.backend.dto.IngredientDTO;
import com.recipe_app.backend.entity.Ingredient;
import com.recipe_app.backend.mapper.IngredientMapper;
import com.recipe_app.backend.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientDTO saveIngredient(IngredientDTO dto) {
        Ingredient ingredient = IngredientMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        return IngredientMapper.toDTO(saved);
    }

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .map(IngredientMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
