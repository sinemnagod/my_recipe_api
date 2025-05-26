package com.recipe_app.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

        @Id
        @GeneratedValue
        private Long id;

        private String name;

        @Column(length = 10000)
        private String description;

        @Column(length = 1000)
        private String ingredients;

        public String getIngredients() {
                return ingredients;
        }

        public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
        }

        @ManyToMany
        @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
        private List<Category> categories = new ArrayList<>();

        private Integer personNumber;
        private Long cookingTime;

        @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Review> reviews = new ArrayList<>();

        @ManyToMany(mappedBy = "favoriteRecipes")
        private List<User> likedByUsers = new ArrayList<>();

        private String recipe;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "score")
        private Double score;

        @Column(name = "image_url")
        private String imageUrl;

}
