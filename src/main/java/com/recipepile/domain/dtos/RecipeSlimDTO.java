package com.recipepile.domain.dtos;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RecipeSlimDTO {
    private Long recipeId;

    private String title;
    private String description;
    private Integer timeNeeded;
    private String source;
    private String url;
    private Integer servings;

    private UserSlimInfoDTO author;

    private Set<String> images = new HashSet<>();

    private Set<CategorySlimDTO> categories = new HashSet<>();
}
