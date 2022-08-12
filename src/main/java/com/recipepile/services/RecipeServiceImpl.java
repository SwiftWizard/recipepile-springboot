package com.recipepile.services;

import com.recipepile.domain.Category;
import com.recipepile.domain.Recipe;
import com.recipepile.exceptions.CategoryNotFoundException;
import com.recipepile.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService{
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    public RecipeServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Collection<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Collection<Recipe> getRecipes(Category category) {
        return recipeRepository.findAllByCategory(category);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) throws CategoryNotFoundException {
        recipe.setRecipeId(sequenceGeneratorService.generateSequence("recipes_sequence"));

        User author = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setAuthor(userService.findByEmail(author.getUsername()));

        boolean categoryDoesNotExists = false;

        for(int i = 0; i < recipe.getCategories().size(); i++){
            if(!categoryService.categoryExists(recipe.getCategories().get(i))){
                categoryDoesNotExists = true;
            }
        }

        if(categoryDoesNotExists){
            throw new CategoryNotFoundException("At least one of the supplied categories does not exist in the application!");
        }

        return recipeRepository.save(recipe);
    }

    @Override
    public Collection<Recipe> getTopRecipes() {
        return recipeRepository.getTopRecipes();
    }

}
