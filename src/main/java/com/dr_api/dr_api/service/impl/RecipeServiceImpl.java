package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Recipe;
import com.dr_api.dr_api.repository.RecipeRepository;
import com.dr_api.dr_api.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repo;

    public RecipeServiceImpl(RecipeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Recipe> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}