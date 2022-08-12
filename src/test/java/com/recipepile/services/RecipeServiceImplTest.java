package com.recipepile.services;

import com.recipepile.domain.Recipe;
import com.recipepile.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes(){
        Recipe rec1 = new Recipe();
        List<Recipe> recipeData = new ArrayList<>();
        recipeData.add(rec1);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipeData);

        List<Recipe> recipes = (List<Recipe>) recipeService.getRecipes();

        Assertions.assertEquals(recipes.size(), 1);

        Mockito.verify(recipeRepository, Mockito.times(1));

    }
}