package com.recipe_app.backend.service;

import com.recipe_app.backend.dto.CategoryDTO;
import com.recipe_app.backend.entity.Category;
import com.recipe_app.backend.mapper.CategoryMapper;
import com.recipe_app.backend.repository.CategoryRepository;
import com.recipe_app.backend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final RecipeRepository recipeRepository;

    public CategoryDTO saveCategory(CategoryDTO dto) {
        Category category = categoryMapper.categoryDTOToCategory(dto, recipeRepository);
        return CategoryMapper.categoryEntityToCategoryDTO(categoryRepository.save(category));
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::categoryEntityToCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::categoryEntityToCategoryDTO)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı."));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
