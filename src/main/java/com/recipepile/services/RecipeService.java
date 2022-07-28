package com.recipepile.services;

import com.recipepile.domain.Category;
import com.recipepile.domain.Recipe;
import com.recipepile.exceptions.CategoryNotFoundException;

import java.util.Collection;

public interface RecipeService {

    Collection<Recipe> getRecipes();
    Collection<Recipe> getRecipes(Category category);

    Recipe addRecipe(Recipe recipe) throws CategoryNotFoundException;
}
