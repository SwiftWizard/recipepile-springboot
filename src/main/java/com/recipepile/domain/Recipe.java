package com.recipepile.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@ToString
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
    private String article;
    private Integer servings;

    @DBRef
    private User author;

    private List<String> images = new ArrayList<>();

    @DBRef
    private List<Category> categories = new ArrayList<>();

    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Review> reviews = new ArrayList<>();

    private SortedSet<ContentStateDecision> contentStateDecisions = new TreeSet<>();

    public void addContentStateDecision(ContentStateDecision decision){
        this.contentStateDecisions.add(decision);
    }
}
