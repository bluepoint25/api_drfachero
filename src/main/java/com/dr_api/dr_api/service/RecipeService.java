package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);
    Recipe save(Recipe recipe);
    void deleteById(Long id);
}