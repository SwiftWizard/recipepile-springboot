package com.recipepile.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document
public class Recipe {

    @Transient
    public static final String SEQUENCE_NAME = "recipes_sequence";

    @Id
    private Long recipeId;

    private String title;
    private String description;
    private Integer timeNeeded;
    private String source;
    private String url;
    private Note note;
    private Integer servings;

    @DBRef
    private User author;

    private List<String> images = new ArrayList<>();

    private List<Step> steps = new ArrayList<>();

    @DBRef
    private List<Category> categories = new ArrayList<>();

    private List<Ingredient> allStuffNeeded = new ArrayList<>();
}
