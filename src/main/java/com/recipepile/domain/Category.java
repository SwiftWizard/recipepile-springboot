package com.recipepile.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@Document
@EqualsAndHashCode
public class Category {

    @Transient
    public static final String SEQUENCE_NAME = "categories_sequence";

    @Id
    private Long categoryId;

    private String categoryName;

    private Category parentCategory;

    @DBRef
    private List<Recipe> recipes = new ArrayList<>();
}
