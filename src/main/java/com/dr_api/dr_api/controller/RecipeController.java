package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.model.Recipe;
import com.dr_api.dr_api.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 1. GET
    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    // 2. POST
    @PostMapping
    public ResponseEntity<Recipe> create(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.save(recipe);
        return ResponseEntity.ok(newRecipe);
    }

    // 3. PUT: Actualizar (NUEVO)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        if (recipeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        recipe.setId(id);
        Recipe updatedRecipe = recipeService.save(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    // 4. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (recipeService.findById(id).isPresent()) {
            recipeService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}