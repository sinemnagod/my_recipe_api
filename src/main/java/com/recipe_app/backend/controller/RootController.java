package com.recipe_app.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "API is running!";
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();

        try {
            // Test database connection
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            status.put("status", "UP");
            status.put("database", "Connected");
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            status.put("status", "DOWN");
            status.put("database", "Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(status);
        }
    }
}