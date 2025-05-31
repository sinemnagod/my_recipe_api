package com.recipe_app.backend.controller;

import com.recipe_app.backend.dto.RecipeDTO;
import com.recipe_app.backend.dto.UserDTO;
import com.recipe_app.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "https://my-recipe-frontend.vercel.app", "http://localhost:3001" }, allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) {

        UserDTO user = userService.loginUser(email, password);

        Cookie cookie = new Cookie("userId", user.getId().toString());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24 * 60 * 60);

        String domain = System.getenv("COOKIE_DOMAIN");
        if (domain != null && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }

        response.addCookie(cookie);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto,
            @RequestParam String password) {
        return ResponseEntity.ok(userService.saveUser(dto, password));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/favorites/add/{recipeId}")
    public ResponseEntity<UserDTO> addToFavorites(@CookieValue("userId") Long userId,
            @PathVariable Long recipeId) {
        return ResponseEntity.ok(userService.addRecipeToFavorites(userId, recipeId));
    }

    @PutMapping("/favorites/remove/{recipeId}")
    public ResponseEntity<UserDTO> removeFromFavorites(@CookieValue("userId") Long userId,
            @PathVariable Long recipeId) {
        return ResponseEntity.ok(userService.removeRecipeFromFavorites(userId, recipeId));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<RecipeDTO>> getFavoriteRecipes(@CookieValue("userId") Long userId) {
        return ResponseEntity.ok(userService.getFavoriteRecipes(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@CookieValue("userId") Long userId) {
        System.out.println(">>> /me called with userId = " + userId);
        UserDTO user = userService.getUserById(userId);
        System.out.println(">>> user fetched = " + user);
        return ResponseEntity.ok(user);
    }

}
