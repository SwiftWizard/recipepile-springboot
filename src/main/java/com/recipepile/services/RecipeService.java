package com.recipepile.services;

import com.recipepile.domain.Category;
import com.recipepile.domain.Recipe;
import com.recipepile.domain.dtos.RecipeSlimDTO;
import com.recipepile.exceptions.CategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface RecipeService {

    Collection<Recipe> getRecipes();
    Collection<Recipe> getRecipes(Category category);
    Recipe addRecipe(Recipe recipe) throws CategoryNotFoundException;
    Collection<Recipe> getTopRecipes();
    Page<RecipeSlimDTO> getAll(Category category, Pageable pageable);

    Recipe findById(Long id);
}
