package com.recipe_app.backend.config;

import com.recipe_app.backend.entity.Category;
import com.recipe_app.backend.entity.Recipe;
import com.recipe_app.backend.entity.User;
import com.recipe_app.backend.repository.CategoryRepository;
import com.recipe_app.backend.repository.RecipeRepository;
import com.recipe_app.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            RecipeRepository recipeRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                // Create admin user
                User admin = new User();
                admin.setName("Admin");
                admin.setSurname("User");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                userRepository.save(admin);

                // Create categories
                Category mainDish = new Category();
                mainDish.setName("Main Dishes");

                Category dessert = new Category();
                dessert.setName("Desserts");

                Category appetizer = new Category();
                appetizer.setName("Appetizers");

                List<Category> categories = categoryRepository.saveAll(Arrays.asList(mainDish, dessert, appetizer));

                // Create sample recipes
                Recipe spaghetti = new Recipe();
                spaghetti.setName("Spaghetti Bolognese");
                spaghetti.setDescription("Classic Italian pasta dish with meat sauce");
                spaghetti.setIngredients(
                        "400g spaghetti, 500g ground beef, 2 cans tomato sauce, 1 onion, 2 cloves garlic");
                spaghetti.setPersonNumber(4);
                spaghetti.setCookingTime(45L);
                spaghetti.setUser(admin);
                spaghetti.setScore(4.5);
                spaghetti.setCategories(Arrays.asList(mainDish));
                spaghetti.setImageUrl("https://example.com/spaghetti.jpg");

                Recipe tiramisu = new Recipe();
                tiramisu.setName("Tiramisu");
                tiramisu.setDescription("Classic Italian coffee-flavored dessert");
                tiramisu.setIngredients("Ladyfingers, Coffee, Mascarpone cheese, Eggs, Sugar");
                tiramisu.setPersonNumber(6);
                tiramisu.setCookingTime(30L);
                tiramisu.setUser(admin);
                tiramisu.setScore(4.8);
                tiramisu.setCategories(Arrays.asList(dessert));
                tiramisu.setImageUrl("https://example.com/tiramisu.jpg");

                recipeRepository.saveAll(Arrays.asList(spaghetti, tiramisu));
            }
        };
    }
}