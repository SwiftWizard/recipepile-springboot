package com.recipepile.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipepile.domain.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RecipeThickDTO {

    private Long recipeId;

    private String title;
    private String description;
    private Integer timeNeeded;
    private String source;
    private String url;
    private String article;
    private Integer servings;

    private UserSlimInfoDTO author;

    private Set<String> images = new HashSet<>();

    private Set<CategorySlimDTO> categories = new HashSet<>();

    private Set<Ingredient> ingredients = new HashSet<>();
}
