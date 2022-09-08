package com.recipepile.repositories;

import com.recipepile.domain.Category;
import com.recipepile.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;

public interface RecipeRepository extends MongoRepository<Recipe, Long> {
    @Query("'categories : ?0'")
    Collection<Recipe> findAllByCategory(Category category);

    @Query("'aggregate([{$sample: {size: 7}}])'")
    Collection<Recipe> getTopRecipes();
}
