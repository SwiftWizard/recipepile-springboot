package com.recipepile.domain.dtos.post;

import com.recipepile.domain.*;
import com.recipepile.domain.dtos.CategorySlimDTO;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class RecipePost {
    private String title;
    private String description;
    private Integer timeNeeded;
    private String source;
    private String url;
    private String article;
    private Integer servings;

    private List<String> images = new ArrayList<>();

    private List<CategorySlimDTO> categories = new ArrayList<>();

    private List<Ingredient> ingredients = new ArrayList<>();
}
