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
// FIX: Cambiamos el origen para que coincida con el frontend
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 1. GET: Listar todas las recetas (Para llenar la tabla)
    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    // 2. POST: Crear nueva receta
    @PostMapping
    public ResponseEntity<Recipe> create(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.save(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    // 3. DELETE: Eliminar receta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (recipeService.findById(id).isPresent()) {
            recipeService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}