package com.recipepile.controllers;

import com.recipepile.domain.Category;
import com.recipepile.domain.Recipe;
import com.recipepile.domain.dtos.CategorySlimDTO;
import com.recipepile.domain.dtos.generics.DataWithMessages;
import com.recipepile.domain.dtos.RecipeThickDTO;
import com.recipepile.domain.dtos.RecipeSlimDTO;
import com.recipepile.domain.dtos.post.RecipePost;
import com.recipepile.exceptions.CategoryNotFoundException;
import com.recipepile.mappers.RecipeMapper;
import com.recipepile.services.CategoryService;
import com.recipepile.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/")
public class RecipeController {

    @Autowired
    private CategoryService categoryService;
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
            System.out.println(newSavedRecipe.toString());
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


    @GetMapping(path = "public/recipes/all")
    ResponseEntity<DataWithMessages<Page<RecipeSlimDTO>, List<String>>> getAll(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        Pageable pageable = PageRequest.of((page != null)? page: 0, (size != null)? size : 10);

        Category category = null;

        if(categoryId != null){
            category = categoryService.findById(categoryId).get();
        }

        return new ResponseEntity<>(new DataWithMessages<>(recipeService.getAll(category, pageable), Arrays.asList("Found the following recipes for supplied category")), HttpStatus.OK);
    }

    @GetMapping(path = "public/recipes/recipe")
    ResponseEntity<?> findRecipe(Long id){
        Recipe recipe = recipeService.findById(id);
        String message;
        if(recipe != null){
            message = "Successfully found recipe";
            return new ResponseEntity<>(new DataWithMessages<>(recipeMapper.recipeToRecipeThickDTO(recipe), Arrays.asList(message)), HttpStatus.OK);
        }else{
            message = "No recipe exists for supplied parameters";
            return new ResponseEntity<>(new DataWithMessages<>(null, Arrays.asList(message)), HttpStatus.OK);
        }

    }
}
