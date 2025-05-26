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
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Recipe> recipes = new ArrayList<>();
}

