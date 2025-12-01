package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // CRUD básico de Recetas
}