package com.recipepile.mappers;

import com.recipepile.domain.Recipe;
import com.recipepile.domain.dtos.RecipeThickDTO;
import com.recipepile.domain.dtos.post.RecipePost;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface RecipeMapper {
    Recipe recipePostToRecipe(RecipePost recipePost);

    RecipeThickDTO recipeToRecipeThickDTO(Recipe recipe);
}
