package com.recipepile.controllers;

import com.recipepile.domain.Recipe;
import com.recipepile.domain.dtos.generics.DataWithMessages;
import com.recipepile.domain.dtos.RecipeThickDTO;
import com.recipepile.domain.dtos.RecipeSlimDTO;
import com.recipepile.domain.dtos.post.RecipePost;
import com.recipepile.exceptions.CategoryNotFoundException;
import com.recipepile.mappers.RecipeMapper;
import com.recipepile.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("api/")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeMapper recipeMapper;

    @PostMapping("manage/recipes/new")
    ResponseEntity<DataWithMessages<RecipeThickDTO, List<String>>> createRecipe(@RequestBody RecipePost recipePost){
        Recipe newRecipe = recipeMapper.recipePostToRecipe(recipePost);
        Recipe newSavedRecipe = null;

        try {
            newSavedRecipe = recipeService.addRecipe(newRecipe);
            RecipeThickDTO recipe = recipeMapper.recipeToRecipeThickDTO(newSavedRecipe);
            return new ResponseEntity<>(new DataWithMessages<>(recipe, Arrays.asList("Recipe successfully added")), HttpStatus.CREATED);
        } catch (CategoryNotFoundException e) {
            RecipeThickDTO recipe = recipeMapper.recipeToRecipeThickDTO(newRecipe);
            return new ResponseEntity<>(new DataWithMessages<>(recipe, Arrays.asList("Update needed!", "At least one of the supplied categories does not exist in the application!")), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("public/recipes/top")
    ResponseEntity<DataWithMessages<List<RecipeSlimDTO>, List<String>>> getTopRecipes(){
        List<Recipe> topRecipes = (List<Recipe>) recipeService.getTopRecipes();
        return new ResponseEntity<>(new DataWithMessages<>(recipeMapper.recipesToRecipeThinDTOs(topRecipes), Arrays.asList("Today's top recipes")), HttpStatus.OK);
    }
}
